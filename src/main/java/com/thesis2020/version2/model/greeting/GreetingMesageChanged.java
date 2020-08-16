package com.thesis2020.version2.model.greeting;

import io.vlingo.lattice.model.DomainEvent;

public class GreetingMesageChanged extends DomainEvent {

    public final String id;
    public final String message;

    public GreetingMesageChanged(String id, String message) {
        this.id = id;
        this.message = message;
    }
}
