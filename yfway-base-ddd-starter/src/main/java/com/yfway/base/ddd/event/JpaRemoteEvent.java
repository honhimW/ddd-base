package com.yfway.base.ddd.event;

import com.yfway.base.ddd.jpa.domain.event.JpaBaseEvent;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author hon_him
 * @since 2022-10-26
 */

public class JpaRemoteEvent extends RemoteApplicationEvent {

    public JpaRemoteEvent(JpaBaseEvent<?, ?> event, String destinationService) {
        super(event, "", DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }
}
