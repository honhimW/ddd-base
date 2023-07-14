# Usage

```xml
<!-- maven -->
<dependencys>
  <dependency>
    <groupId>io.github.honhimw.ddd</groupId>
    <artifactId>ddd-starter</artifactId>
  </dependency>
  <dependency>
    <!-- 基于jpa -->
    <groupId>io.github.honhimw.ddd</groupId>
    <artifactId>ddd-jpa</artifactId>
  </dependency>
  <dependency>
    <!-- 驱动 -->
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
  </dependency>
  <dependency>
    <!-- spring-boot-jpa -->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <!-- 总线, 发布/监听远程事件 -->
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-kafka</artifactId>
  </dependency>
</dependencys>
```

```groovy
// gradle
dependencies {
    implementation 'io.github.honhimw.ddd:ddd-starter'
    implementation 'io.github.honhimw.ddd:ddd-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.cloud:spring-cloud-bus'
    implementation 'org.springframework.cloud:spring-cloud-starter-bus-kafka'
}
```

```java

import java.util.Optional;

@EnableJpaAuditing // 启动审查AuditorAware<T>
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    <T> AuditorAware<T> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal);
    }

}
```