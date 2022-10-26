package com.yfway.base.ddd.bus;

import com.yfway.base.ddd.event.JpaRemoteEvent;
import com.yfway.base.ddd.event.MpRemoteEvent;
import com.yfway.base.ddd.jpa.YfDDDJpa;
import com.yfway.base.ddd.jpa.domain.event.JpaBaseEvent;
import com.yfway.base.ddd.mp.YfDDDMp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

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
    static class JpaEventListenerWrapper {

        @Autowired
        private ApplicationEventPublisher publisher;

        @EventListener
        public void jpaEvent(JpaBaseEvent<?, ?> event) {
            JpaRemoteEvent remoteEvent = new JpaRemoteEvent(event, "");
            log.info("wrap jpa event: {}", event);
            publisher.publishEvent(remoteEvent);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(value = {
        RemoteApplicationEvent.class,
        BusAutoConfiguration.class,
        YfDDDMp.class
    })
    static class MpEventListenerWrapper {

        @Autowired
        private ApplicationEventPublisher publisher;

        @EventListener
        public void jpaEvent(Object event) {
            MpRemoteEvent remoteEvent = new MpRemoteEvent(event, "");
            publisher.publishEvent(remoteEvent);
        }

    }


}
