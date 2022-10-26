package com.yfway.base.ddd.test;

import com.yfway.base.utils.YfJsonUtils;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hon_him
 * @since 2022-10-25
 */

public class Runner {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void instant() {
        long l = System.currentTimeMillis();
        log.info("System: {}", l);
        log.info("instant: {}", Instant.now());
        Map<String, Instant> instantMap = Map.of("instant", Instant.now());
        Map<String, LocalDateTime> ldtMap = Map.of("ldt", LocalDateTime.now());
        log.info("instant json: {}", YfJsonUtils.toJson(instantMap));
        log.info("date: {}", new Date(l));
        log.info("localdatetime json: {}", YfJsonUtils.toJson(ldtMap));
        log.info("localdatetime json: {}", LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
    }

    public void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception ignored) {
        }
    }

}
