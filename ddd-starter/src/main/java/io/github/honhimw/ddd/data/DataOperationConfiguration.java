package io.github.honhimw.ddd.data;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import io.github.honhimw.ddd.jpa.DDDJpa;
import io.github.honhimw.ddd.jpa.util.PageUtils;
import io.github.honhimw.ddd.mp.DDDMp;
import io.github.honhimw.ddd.mp.domain.AbstractAR;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.annotation.PostConstruct;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Optional;

/**
 * @author hon_him
 * @since 2022-11-01
 */

abstract class DataOperationConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean(DataSource.class)
    @ConditionalOnClass(value = {
        DDDJpa.class,
        JpaRepository.class
    })
    static class JpaConfiguration {

        @Value("${spring.data.web.pageable.one-indexed-parameters:true}")
        private Boolean firstPageNo;

        @PostConstruct
        void setupPageUtils() {
            if (firstPageNo) {
                PageUtils.setFirstPageNo(1);
            } else {
                PageUtils.setFirstPageNo(0);
            }
        }

        @Bean
        @ConditionalOnMissingBean(AuditorAware.class)
        AuditorAware<?> auditorAware() {
            return Optional::empty;
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean(DataSource.class)
    @ConditionalOnClass(value = {
        DDDMp.class,
        MybatisConfiguration.class
    })
    static class MybatisPlusActiveRecord {
        @Bean
        @ConditionalOnClass(value = {
            DDDMp.class,
            MybatisConfiguration.class
        })
        MybatisPlusInterceptor mybatisPlusInterceptor() {
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
