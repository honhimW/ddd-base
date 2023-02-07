package com.yfway.base.ddd.jpa.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfway.base.utils.YfJsonUtils;

/**
 * @author hon_him
 * @since 2023-02-07
 */

public class JacksonUtils {

    public static <T> T readValue(Class<T> type, Object value) {
        try {
            return YfJsonUtils.getMvcObjectMapper().readerFor(type)
                .readValue(String.format("\"%s\"", value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
