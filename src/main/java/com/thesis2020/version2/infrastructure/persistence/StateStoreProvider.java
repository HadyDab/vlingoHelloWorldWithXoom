package com.thesis2020.version2.infrastructure.persistence;

import java.util.Arrays;
import java.util.List;

import com.thesis2020.version2.model.greeting.GreetingState;
import io.vlingo.symbio.store.common.jdbc.postgres.PostgresConfigurationProvider;
import io.vlingo.symbio.store.state.StateTypeStateStoreMap;
import io.vlingo.symbio.store.state.jdbc.JDBCStateStoreActor;
import io.vlingo.symbio.store.state.jdbc.postgres.PostgresStorageDelegate;

import io.vlingo.actors.Definition;
import io.vlingo.actors.Protocols;
import io.vlingo.actors.Stage;
import io.vlingo.lattice.model.stateful.StatefulTypeRegistry;
import io.vlingo.lattice.model.stateful.StatefulTypeRegistry.Info;
import io.vlingo.symbio.StateAdapterProvider;
import io.vlingo.symbio.EntryAdapterProvider;
import io.vlingo.symbio.store.dispatch.Dispatcher;
import io.vlingo.symbio.store.dispatch.DispatcherControl;
import io.vlingo.symbio.store.dispatch.Dispatchable;
import io.vlingo.symbio.store.state.StateStore;

import io.vlingo.actors.ActorInstantiator;
import io.vlingo.symbio.store.DataFormat;
import io.vlingo.symbio.store.StorageException;
import io.vlingo.symbio.store.state.StateStore.StorageDelegate;
import io.vlingo.symbio.store.state.jdbc.JDBCStateStoreActor.JDBCStateStoreInstantiator;
import io.vlingo.symbio.store.common.jdbc.Configuration;
import io.vlingo.xoom.storage.DatabaseConfiguration;
import io.vlingo.xoom.storage.Model;

public class StateStoreProvider {
  private static StateStoreProvider instance;

  public final DispatcherControl dispatcherControl;
  public final StateStore store;
  public final Queries queries;


  public static StateStoreProvider instance() {
    return instance;
  }

  public static StateStoreProvider using(final Stage stage, final StatefulTypeRegistry registry){
    final Dispatcher noop = new Dispatcher() {
      public void controlWith(final DispatcherControl control) { }
      public void dispatch(Dispatchable d) { }
    };

    return using(stage, registry, noop);
  }

  @SuppressWarnings("rawtypes")
  public static StateStoreProvider using(final Stage stage, final StatefulTypeRegistry registry, final Dispatcher dispatcher){
    if (instance != null) {
      return instance;
    }

    final StateAdapterProvider stateAdapterProvider = new StateAdapterProvider(stage.world());
    stateAdapterProvider.registerAdapter(GreetingState.class, new GreetingStateAdapter());
    StateTypeStateStoreMap.stateTypeToStoreName(GreetingState.class,GreetingState.class.getSimpleName());

    new EntryAdapterProvider(stage.world()); // future use

    final ActorInstantiator jdbcInstantiator = setupJDBCInstantiator(stage, dispatcher);
//    final List<Object> parameters = Definition.parameters(Arrays.asList(jdbcInstantiator));

    final Protocols storeProtocols =
            stage.actorFor(
                    new Class<?>[] { StateStore.class, DispatcherControl.class },
                    Definition.has(JDBCStateStoreActor.class, jdbcInstantiator));

    final Protocols.Two<StateStore, DispatcherControl> storeWithControl = Protocols.two(storeProtocols);

    registry.register(new Info(storeWithControl._1, GreetingState.class, GreetingState.class.getSimpleName()));

    final Queries queries = stage.actorFor(Queries.class,QueriesActor.class,storeWithControl._1);

    instance = new StateStoreProvider(storeWithControl._1, storeWithControl._2,queries);

    return instance;
  }

  private static ActorInstantiator setupJDBCInstantiator(final Stage stage, final Dispatcher dispatcher){
    final StorageDelegate storageDelegate = setupStorageDelegate(stage);
//    final StorageDelegate storageDelegate = manualStorageDelegateSetup(stage);
    final ActorInstantiator<?> instantiator = new JDBCStateStoreInstantiator();
    instantiator.set("dispatcher", dispatcher);
    instantiator.set("delegate", storageDelegate);
    return instantiator;
  }

  private static StorageDelegate setupStorageDelegate(final Stage stage) {
    return new PostgresStorageDelegate(DatabaseConfiguration.load(Model.COMMAND), stage.world().defaultLogger());
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private StateStoreProvider(final StateStore store, final DispatcherControl dispatcherControl,final Queries queries) {
    this.store = store;
    this.dispatcherControl = dispatcherControl;
    this.queries = queries;
  }
}
