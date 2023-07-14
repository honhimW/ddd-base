package com.yfway.base.ddd.bus.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author hon_him
 * @since 2022-10-26
 */

public class RemoteDomainEvent extends RemoteApplicationEvent {

    private final Object principal;

    public RemoteDomainEvent() {
        this.principal = new Object();
    }

    public RemoteDomainEvent(Object event, String originService, String destinationService) {
        super(event, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.principal = event;
    }

    @SuppressWarnings("unchecked")
    public <T> T getPrincipal() {
        return (T) principal;
    }
}
