package org.hbird.business.core;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.hbird.exchange.commandrelease.CommandRequest;
import org.hbird.exchange.configurator.StandardEndpoints;
import org.hbird.exchange.constants.StandardArguments;
import org.hbird.exchange.core.Command;
import org.hbird.exchange.core.Event;
import org.hbird.exchange.core.Named;
import org.hbird.exchange.core.State;
import org.hbird.exchange.interfaces.IGroundStationSpecific;
import org.hbird.exchange.interfaces.ISatelliteSpecific;
import org.hbird.exchange.tasking.Task;

public abstract class HbirdRouteBuilder extends RouteBuilder {

    /**
     * Adds to a route the injection path into hummingbird. The injection path can
     * be complex. It will set headers for routing and filtering purposes. It will
     * route different kinds of messages to different endpoints for distribution.
     * 
     * @param route
     */
    protected void addInjectionRoute(ProcessorDefinition<?> route) {
        TransferScheduler trasferScehduler = new TransferScheduler();

        // @formatter:off
        route
            /* Dont route messages with a NULL body. */
            .choice()
                .when(simple("${in.body} == null"))
                .stop()
            .end()

            /* Set standard headers. */
            .setHeader(StandardArguments.NAME, simple("${in.body.name}"))
            .setHeader(StandardArguments.CLASS, simple("${in.body.class.simpleName}"))

            .choice()
                .when(body().isInstanceOf(Named.class))
                    .setHeader(StandardArguments.ISSUED_BY, simple("${in.body.issuedBy}"))
                    .setHeader(StandardArguments.TIMESTAMP, simple("${in.body.timestamp}"))
            .end()

            /* Set object specific headers. */
            .choice()
                .when(body().isInstanceOf(State.class))
                    .setHeader(StandardArguments.IS_STATE_OF, simple("${in.body.isStateOf}"))
                .when(body().isInstanceOf(Command.class))
                    .setHeader(StandardArguments.DESTINATION, simple("${in.body.destination}"))
                .when(body().isInstanceOf(IGroundStationSpecific.class))
                    .setHeader(StandardArguments.LOCATION, simple("${in.body.groundStationName}"))
                .when(body().isInstanceOf(ISatelliteSpecific.class))
                    .setHeader(StandardArguments.SATELLITE_NAME, simple("${in.body.satelliteName}"))
            .end()

            /* Schedule the release, if this object implements ITransferable. */
            .bean(trasferScehduler)

            /* Route to the topic / query. */
            .choice()
                .when((body().isInstanceOf(Task.class)))
                    .to(StandardEndpoints.TASKS)
                .when((body().isInstanceOf(CommandRequest.class)))
                    .to(StandardEndpoints.REQUESTS)
                .when((body().isInstanceOf(Command.class)))
                    .to(StandardEndpoints.COMMANDS)
                .when((body().isInstanceOf(Event.class)))
                    .to(StandardEndpoints.EVENTS)
                .otherwise()
                    .to(StandardEndpoints.MONITORING);

        // @formatter:on

    }

}
