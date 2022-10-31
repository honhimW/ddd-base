# Usage

```xml
<!-- maven -->
  ...
<dependency>
  <groupId>com.yfway.base</groupId>
  <artifactId>yfway-base-ddd-starter</artifactId>
</dependency>
<dependency>
  <!-- 基于jpa -->
  <groupId>com.yfway.base</groupId>
  <artifactId>yfway-base-ddd-jpa</artifactId>
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
  <!-- 总线, 配置好可自动发布/监听远程事件 -->
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bus-kafka</artifactId>
</dependency>
  ...
```
```groovy
// gradle
dependencies {
    implementation 'com.yfway.base:yfway-base-ddd-starter'
    implementation 'com.yfway.base:yfway-base-ddd-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.cloud:spring-cloud-bus'
    implementation 'org.springframework.cloud:spring-cloud-starter-bus-kafka'
}
```
```java
@EnableJpaAuditing
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
```