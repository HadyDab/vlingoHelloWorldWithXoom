package com.thesis2020.version2.resource;


import com.thesis2020.version2.infrastructure.data.GreetingData;
import com.thesis2020.version2.infrastructure.data.UpdateGreetingData;
import com.thesis2020.version2.infrastructure.persistence.Queries;
import com.thesis2020.version2.infrastructure.persistence.StateStoreProvider;
import com.thesis2020.version2.model.greeting.Greeting;
import com.thesis2020.version2.model.greeting.GreetingEntity;
import io.vlingo.actors.AddressFactory;
import io.vlingo.actors.Stage;
import io.vlingo.common.Completes;
import io.vlingo.common.serialization.JsonSerialization;
import io.vlingo.http.Response;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.http.resource.Resource;
import io.vlingo.http.resource.ResourceBuilder;
import io.vlingo.http.resource.ResourceHandler;

import static io.vlingo.http.resource.ResourceBuilder.resource;

public class GreetingResource extends ResourceHandler {

    private final Stage stage;
    private final Queries queries;
    private final AddressFactory addressFactory;

    public GreetingResource(final Stage stage) {
        this.stage = stage;
        this.addressFactory = stage.addressFactory();
        this.queries = StateStoreProvider.instance().queries;
    }

    @Override
    public Resource<?> routes() {

        return resource("GreetingResource",
                postGreeting(),
                getGreetingWithID(),
                changeMessage(),
                changeDescription());
    }

    private RequestHandler postGreeting(){
        return ResourceBuilder.post("/greetings")
                .body(GreetingData.class)
                .handle(this::defineGreeting);
    }

    private RequestHandler getGreetingWithID(){
        return ResourceBuilder.get("/greetings/{greetingId}")
                .param(String.class)
                .handle(this::getGreeting);
    }

    private RequestHandler changeMessage(){
        return ResourceBuilder.patch("/greetings/{greetingId}/message")
                .param(String.class)
                .body(UpdateGreetingData.class)
                .handle(this::updateMessage);
    }

    private RequestHandler changeDescription(){
        return ResourceBuilder.patch("/greetings/{greetingId}/description")
                .param(String.class)
                .body(UpdateGreetingData.class)
                .handle(this::updateDescription);
    }




    private Completes<Response> defineGreeting(GreetingData data){
        return  Greeting.defineGreeting(stage,data)
                .andThenTo(state -> Completes.withSuccess(Response.of(Response.Status.Created, JsonSerialization.serialized(state))));
    }

    private Completes<Response> getGreeting(String greetingId){
        return queries.greetingWithId(greetingId)
                .andThenTo(state -> Completes.withSuccess(Response.of(Response.Status.Ok, JsonSerialization.serialized(state))))
                .otherwise(nodata -> Response.of(Response.Status.NotFound,notFoundMessage(greetingId)));
    }

    private Completes<Response> updateMessage(String greetingId,UpdateGreetingData data){
        return resolve(greetingId)
                .andThenTo(greeting -> greeting.updateMessage(data))
                .andThenTo(state -> Completes.withSuccess(Response.of(Response.Status.Ok,JsonSerialization.serialized(state))))
                .otherwise(nodata -> Response.of(Response.Status.NotFound,notFoundMessage(greetingId)));
    }

    private Completes<Response> updateDescription(String greetingId, UpdateGreetingData data){
        return resolve(greetingId)
                .andThenTo(greeting -> greeting.updateDescription(data))
                .andThenTo(state -> Completes.withSuccess(Response.of(Response.Status.Ok,JsonSerialization.serialized(state))))
                .otherwise(nodata -> Response.of(Response.Status.NotFound,notFoundMessage(greetingId)));
    }

    private Completes<Greeting> resolve(final String greetingId) {
        return stage.actorOf(Greeting.class, addressFactory.from(greetingId), GreetingEntity.class);
    }


    private String notFoundMessage(String id){
        return "Greeting for ID: " + id + " not found";
    }



}