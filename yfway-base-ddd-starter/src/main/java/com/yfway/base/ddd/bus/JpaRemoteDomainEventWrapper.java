package com.yfway.base.ddd.bus;

import com.yfway.base.ddd.event.RemoteDomainEvent;
import com.yfway.base.ddd.jpa.domain.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author hon_him
 * @since 2022-10-28
 */
@Slf4j
public class JpaRemoteDomainEventWrapper implements RemoteDomainEventWrapper {

    public static final String BUS_ID = "spring.cloud.bus.id";

    public static final String APPLICATION_NAME = "spring.application.name";

    private final ApplicationEventPublisher publisher;

    private final ConfigurableEnvironment environment;

    public JpaRemoteDomainEventWrapper(ApplicationEventPublisher publisher, ConfigurableEnvironment environment) {
        this.publisher = publisher;
        this.environment = environment;
    }

    @EventListener
    public void jpaEvent(DomainEvent<?, ?> domainEvent) {
        if (log.isDebugEnabled()) {
            log.debug("wrap jpa event: {}, {}", domainEvent.getAction(), domainEvent.getEntity());
        }
        String busId = environment.getProperty(BUS_ID);
        if (StringUtils.equals(busId, "application")) {
            busId = environment.getProperty(APPLICATION_NAME);
        }
        RemoteDomainEvent remoteEvent = new RemoteDomainEvent(domainEvent, busId, null);
        publisher.publishEvent(remoteEvent);
    }

}
