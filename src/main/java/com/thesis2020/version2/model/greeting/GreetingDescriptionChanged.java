package com.thesis2020.version2.model.greeting;

import io.vlingo.lattice.model.DomainEvent;

public class GreetingDescriptionChanged extends DomainEvent {

    public final String id;
    public final String value;

    public GreetingDescriptionChanged(String id, String value) {
        this.id = id;
        this.value = value;
    }
}
