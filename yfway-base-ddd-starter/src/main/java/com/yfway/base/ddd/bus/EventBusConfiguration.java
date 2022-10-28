package com.yfway.base.ddd.bus;

import com.yfway.base.ddd.event.RemoteDomainEvent;
import com.yfway.base.ddd.jpa.YfDDDJpa;
import com.yfway.base.ddd.mp.YfDDDMp;
import com.yfway.base.ddd.mp.domain.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author hon_him
 * @since 2022-10-26
 */
@Configuration(proxyBeanMethods = false)
class EventBusConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(value = {
        RemoteApplicationEvent.class,
        BusAutoConfiguration.class,
        YfDDDJpa.class
    })
    @Slf4j
    static class JpaBusConfiguration {

        @Bean
        @ConditionalOnMissingBean
        DomainEventWrapper domainEventWrapper(ApplicationEventPublisher publisher, ConfigurableEnvironment environment) {
            return new JpaDomainEventWrapper(publisher, environment);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(value = {
        RemoteApplicationEvent.class,
        BusAutoConfiguration.class,
        YfDDDMp.class
    })
    static class MpBusConfiguration {

        @Autowired
        private ApplicationEventPublisher publisher;

        @EventListener
        public void mpEvent(DomainEvent event) {
            RemoteDomainEvent remoteEvent = new RemoteDomainEvent(event, "", null);
            publisher.publishEvent(remoteEvent);
        }

    }


}
