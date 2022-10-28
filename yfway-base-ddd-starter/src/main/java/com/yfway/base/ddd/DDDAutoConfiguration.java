package com.yfway.base.ddd;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.yfway.base.ddd.jpa.YfDDDJpa;
import com.yfway.base.ddd.mp.YfDDDMp;
import com.yfway.base.ddd.mp.domain.AbstractAR;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hon_him
 * @since 2022-10-25
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.yfway.base.ddd")
public class DDDAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
    @ConditionalOnClass(value = {
        YfDDDJpa.class,
        JpaRepository.class
    })
    static class JpaConfiguration {

        @Bean
        @ConditionalOnMissingBean(AuditorAware.class)
        public AuditorAware<?> auditorAware() {
            return Optional::empty;
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(value = {
        YfDDDMp.class,
        MybatisConfiguration.class
    })
    static class MybatisPlusActiveRecord {
        @Bean
        @ConditionalOnClass(value = {
            YfDDDMp.class,
            MybatisConfiguration.class
        })
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
            return new MybatisPlusInterceptor() {
                @Override
                public Object intercept(Invocation invocation) throws Throwable {
                    // TODO 前置逻辑
                    Object intercept = super.intercept(invocation);
                    // TODO 后置逻辑

                    Object target = invocation.getTarget();
                    if (target instanceof AbstractAR<?> abstractAR) {
                        Collection<Object> objects = abstractAR.domainEvents();
                        // TODO 发布事件

                        abstractAR.clearDomainEvents();

                    }
                    return intercept;
                }
            };
        }
    }

}
