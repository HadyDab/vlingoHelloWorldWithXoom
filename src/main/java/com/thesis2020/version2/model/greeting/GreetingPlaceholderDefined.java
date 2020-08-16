package com.thesis2020.version2.model.greeting;

import io.vlingo.lattice.model.DomainEvent;

public final class GreetingPlaceholderDefined extends DomainEvent {
  public final String id;
  public final String placeholderValue;

  public GreetingPlaceholderDefined(final String id, final String placeholderValue) {
    this.id = id;
    this.placeholderValue = placeholderValue;
  }
}
