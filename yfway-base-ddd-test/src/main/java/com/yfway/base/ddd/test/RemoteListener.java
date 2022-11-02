package com.yfway.base.ddd.test;

import com.yfway.base.ddd.bus.event.RemoteDomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author hon_him
 * @since 2022-10-28
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "server.port", havingValue = "8081")
@RemoteApplicationEventScan(basePackageClasses = RemoteDomainEvent.class)
public class RemoteListener implements ApplicationListener<RemoteDomainEvent> {

//    @EventListener
//    public void lis(RemoteDomainEvent remoteDomainEvent) {
//        log.info(remoteDomainEvent.toString());
//    }

    @Override
    public void onApplicationEvent(RemoteDomainEvent remoteDomainEvent) {
        Object principal = remoteDomainEvent.getPrincipal();
        log.info(principal.toString());
    }
}
