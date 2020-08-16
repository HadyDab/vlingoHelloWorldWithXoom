package com.thesis2020.version2.infrastructure.persistence;

import com.thesis2020.version2.model.greeting.GreetingState;
import io.vlingo.common.Completes;

import java.util.Collection;

public interface Queries {
    Completes<GreetingState> greetingWithId(final String id);
    Completes<Collection<GreetingState>> greetings();
}
