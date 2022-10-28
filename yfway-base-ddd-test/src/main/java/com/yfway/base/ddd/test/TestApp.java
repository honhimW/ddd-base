package com.yfway.base.ddd.test;

import com.yfway.base.ddd.event.RemoteDomainEvent;
import com.yfway.base.ddd.test.domain.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author hon_him
 * @since 2022-10-25
 */
@EnableWebMvc
@EnableJpaRepositories
@SpringBootApplication(exclude = {

})
@EnableJpaAuditing
public class TestApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(TestApp.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
