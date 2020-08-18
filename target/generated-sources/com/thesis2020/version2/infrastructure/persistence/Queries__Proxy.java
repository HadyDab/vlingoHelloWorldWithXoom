package com.thesis2020.version2.infrastructure.persistence;

import io.vlingo.actors.Actor;
import io.vlingo.actors.Definition.SerializationProxy;
import io.vlingo.actors.ActorProxyBase;
import io.vlingo.actors.DeadLetter;
import io.vlingo.actors.LocalMessage;
import io.vlingo.actors.Mailbox;
import io.vlingo.actors.Returns;
import io.vlingo.common.Completes;
import io.vlingo.common.SerializableConsumer;
import com.thesis2020.version2.infrastructure.persistence.Queries;
import java.util.Collection;
import io.vlingo.common.Completes;
import java.lang.String;
import com.thesis2020.version2.model.greeting.GreetingState;

public class Queries__Proxy extends ActorProxyBase<com.thesis2020.version2.infrastructure.persistence.Queries> implements com.thesis2020.version2.infrastructure.persistence.Queries {

  private static final String greetingWithIdRepresentation1 = "greetingWithId(java.lang.String)";
  private static final String greetingsRepresentation2 = "greetings()";

  private final Actor actor;
  private final Mailbox mailbox;

  public Queries__Proxy(final Actor actor, final Mailbox mailbox){
    super(com.thesis2020.version2.infrastructure.persistence.Queries.class, SerializationProxy.from(actor.definition()), actor.address());
    this.actor = actor;
    this.mailbox = mailbox;
  }

  public Queries__Proxy(){
    super();
    this.actor = null;
    this.mailbox = null;
  }

  public io.vlingo.common.Completes<com.thesis2020.version2.model.greeting.GreetingState> greetingWithId(java.lang.String id) {
    if (!actor.isStopped()) {
      ActorProxyBase<Queries> self = this;
      final SerializableConsumer<Queries> consumer = (actor) -> actor.greetingWithId(ActorProxyBase.thunk(self, (Actor)actor, id));
      final io.vlingo.common.Completes<com.thesis2020.version2.model.greeting.GreetingState> returnValue = Completes.using(actor.scheduler());
      if (mailbox.isPreallocated()) { mailbox.send(actor, Queries.class, consumer, Returns.value(returnValue), greetingWithIdRepresentation1); }
      else { mailbox.send(new LocalMessage<Queries>(actor, Queries.class, consumer, Returns.value(returnValue), greetingWithIdRepresentation1)); }
      return returnValue;
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, greetingWithIdRepresentation1));
    }
    return null;
  }
  public io.vlingo.common.Completes<java.util.Collection<com.thesis2020.version2.model.greeting.GreetingState>> greetings() {
    if (!actor.isStopped()) {
      ActorProxyBase<Queries> self = this;
      final SerializableConsumer<Queries> consumer = (actor) -> actor.greetings();
      final io.vlingo.common.Completes<java.util.Collection<com.thesis2020.version2.model.greeting.GreetingState>> returnValue = Completes.using(actor.scheduler());
      if (mailbox.isPreallocated()) { mailbox.send(actor, Queries.class, consumer, Returns.value(returnValue), greetingsRepresentation2); }
      else { mailbox.send(new LocalMessage<Queries>(actor, Queries.class, consumer, Returns.value(returnValue), greetingsRepresentation2)); }
      return returnValue;
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, greetingsRepresentation2));
    }
    return null;
  }
}
