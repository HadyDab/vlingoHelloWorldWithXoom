package com.thesis2020.version2.model.greeting;

import io.vlingo.actors.Actor;
import io.vlingo.actors.Definition.SerializationProxy;
import io.vlingo.actors.ActorProxyBase;
import io.vlingo.actors.DeadLetter;
import io.vlingo.actors.LocalMessage;
import io.vlingo.actors.Mailbox;
import io.vlingo.actors.Returns;
import io.vlingo.common.Completes;
import io.vlingo.common.SerializableConsumer;
import com.thesis2020.version2.model.greeting.Greeting;
import com.thesis2020.version2.infrastructure.data.UpdateGreetingData;
import io.vlingo.common.Completes;
import com.thesis2020.version2.infrastructure.data.GreetingData;
import com.thesis2020.version2.model.greeting.GreetingState;

public class Greeting__Proxy extends ActorProxyBase<com.thesis2020.version2.model.greeting.Greeting> implements com.thesis2020.version2.model.greeting.Greeting {

  private static final String updateMessageRepresentation1 = "updateMessage(com.thesis2020.version2.infrastructure.data.UpdateGreetingData)";
  private static final String updateDescriptionRepresentation2 = "updateDescription(com.thesis2020.version2.infrastructure.data.UpdateGreetingData)";
  private static final String defineGreetingRepresentation3 = "defineGreeting(com.thesis2020.version2.infrastructure.data.GreetingData)";

  private final Actor actor;
  private final Mailbox mailbox;

  public Greeting__Proxy(final Actor actor, final Mailbox mailbox){
    super(com.thesis2020.version2.model.greeting.Greeting.class, SerializationProxy.from(actor.definition()), actor.address());
    this.actor = actor;
    this.mailbox = mailbox;
  }

  public Greeting__Proxy(){
    super();
    this.actor = null;
    this.mailbox = null;
  }

  public io.vlingo.common.Completes<com.thesis2020.version2.model.greeting.GreetingState> updateMessage(com.thesis2020.version2.infrastructure.data.UpdateGreetingData data) {
    if (!actor.isStopped()) {
      ActorProxyBase<Greeting> self = this;
      final SerializableConsumer<Greeting> consumer = (actor) -> actor.updateMessage(ActorProxyBase.thunk(self, (Actor)actor, data));
      final io.vlingo.common.Completes<com.thesis2020.version2.model.greeting.GreetingState> returnValue = Completes.using(actor.scheduler());
      if (mailbox.isPreallocated()) { mailbox.send(actor, Greeting.class, consumer, Returns.value(returnValue), updateMessageRepresentation1); }
      else { mailbox.send(new LocalMessage<Greeting>(actor, Greeting.class, consumer, Returns.value(returnValue), updateMessageRepresentation1)); }
      return returnValue;
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, updateMessageRepresentation1));
    }
    return null;
  }
  public io.vlingo.common.Completes<com.thesis2020.version2.model.greeting.GreetingState> updateDescription(com.thesis2020.version2.infrastructure.data.UpdateGreetingData data) {
    if (!actor.isStopped()) {
      ActorProxyBase<Greeting> self = this;
      final SerializableConsumer<Greeting> consumer = (actor) -> actor.updateDescription(ActorProxyBase.thunk(self, (Actor)actor, data));
      final io.vlingo.common.Completes<com.thesis2020.version2.model.greeting.GreetingState> returnValue = Completes.using(actor.scheduler());
      if (mailbox.isPreallocated()) { mailbox.send(actor, Greeting.class, consumer, Returns.value(returnValue), updateDescriptionRepresentation2); }
      else { mailbox.send(new LocalMessage<Greeting>(actor, Greeting.class, consumer, Returns.value(returnValue), updateDescriptionRepresentation2)); }
      return returnValue;
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, updateDescriptionRepresentation2));
    }
    return null;
  }
  public io.vlingo.common.Completes<com.thesis2020.version2.model.greeting.GreetingState> defineGreeting(com.thesis2020.version2.infrastructure.data.GreetingData data) {
    if (!actor.isStopped()) {
      ActorProxyBase<Greeting> self = this;
      final SerializableConsumer<Greeting> consumer = (actor) -> actor.defineGreeting(ActorProxyBase.thunk(self, (Actor)actor, data));
      final io.vlingo.common.Completes<com.thesis2020.version2.model.greeting.GreetingState> returnValue = Completes.using(actor.scheduler());
      if (mailbox.isPreallocated()) { mailbox.send(actor, Greeting.class, consumer, Returns.value(returnValue), defineGreetingRepresentation3); }
      else { mailbox.send(new LocalMessage<Greeting>(actor, Greeting.class, consumer, Returns.value(returnValue), defineGreetingRepresentation3)); }
      return returnValue;
    } else {
      actor.deadLetters().failedDelivery(new DeadLetter(actor, defineGreetingRepresentation3));
    }
    return null;
  }
}
