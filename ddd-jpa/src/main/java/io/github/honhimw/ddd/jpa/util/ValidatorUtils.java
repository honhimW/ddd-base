package io.github.honhimw.ddd.jpa.util;

import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hon_him
 * @since 2022-10-18
 */

public class ValidatorUtils {

    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final Class<?>[] GROUPS = new Class[0];
    private static final String[] ARGS = {};

    public static void validate(Object argument) {
        validate(argument, ARGS);
    }

    public static void validate(Object argument, String[] excludesArgs) {
        Set<ConstraintViolation<Object>> validResult = validator.validate(argument, GROUPS);
        if (StringUtils.isNoneBlank(excludesArgs)) {
            Set<String> ea = Arrays.stream(excludesArgs).collect(Collectors.toSet());
            validResult = validResult.stream().filter(cv -> !ea.contains(cv.getPropertyPath().toString())).collect(Collectors.toSet());
        }
        if (!validResult.isEmpty()) {
            throw new ConstraintViolationException(validResult);
        }
    }

}
