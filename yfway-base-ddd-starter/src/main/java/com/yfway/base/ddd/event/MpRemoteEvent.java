package com.yfway.base.ddd.event;

import com.yfway.base.ddd.jpa.domain.event.JpaBaseEvent;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author hon_him
 * @since 2022-10-26
 */

public class MpRemoteEvent extends RemoteApplicationEvent {

    public MpRemoteEvent(Object event, String destinationService) {
        super(event, "", DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }
}
