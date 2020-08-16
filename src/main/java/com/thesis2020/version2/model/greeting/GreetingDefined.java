package com.thesis2020.version2.model.greeting;


import com.thesis2020.version2.infrastructure.data.GreetingData;
import io.vlingo.lattice.model.DomainEvent;

public final class GreetingDefined extends DomainEvent {
  public final String id;
  public final GreetingData value;

  public GreetingDefined(final String id, final GreetingData value) {
    this.id = id;
    this.value = value;
  }
}
