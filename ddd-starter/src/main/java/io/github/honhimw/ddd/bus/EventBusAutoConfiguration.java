package io.github.honhimw.ddd.bus;

import io.github.honhimw.ddd.bus.event.RemoteDomainEvent;
import io.github.honhimw.ddd.jpa.DDDJpa;
import io.github.honhimw.ddd.mp.DDDMp;
import io.github.honhimw.ddd.mp.domain.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.cloud.bus.ConditionalOnBusEnabled;
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
@ConditionalOnBusEnabled
@ConditionalOnClass(RemoteApplicationEvent.class)
@AutoConfigureAfter(BusAutoConfiguration.class)
public class EventBusAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(value = {
        RemoteApplicationEvent.class,
        BusAutoConfiguration.class,
        DDDJpa.class
    })
    @Slf4j
    static class JpaBusConfiguration {

        @Bean
        @ConditionalOnMissingBean(RemoteDomainEventWrapper.class)
        RemoteDomainEventWrapper domainEventWrapper(ApplicationEventPublisher publisher, ConfigurableEnvironment environment) {
            return new JpaRemoteDomainEventWrapper(publisher, environment);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(value = {
        RemoteApplicationEvent.class,
        BusAutoConfiguration.class,
        DDDMp.class
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
