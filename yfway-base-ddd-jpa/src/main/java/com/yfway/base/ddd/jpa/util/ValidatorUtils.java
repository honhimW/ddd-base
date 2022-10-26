package com.yfway.base.ddd.jpa.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * @author hon_him
 * @since 2022-10-18
 */

public class ValidatorUtils {

    protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final Class<?>[] GROUPS = new Class[0];

    public static void validate(Object arugment) {
        validate(arugment, null);
    }

    public static void validate(Object arugment, String[] excludesArgs) {
        Set<ConstraintViolation<Object>> validResult = validator.validate(arugment, GROUPS);
        if (StringUtils.isNoneBlank(excludesArgs)) {
            Set<String> ea = Arrays.stream(excludesArgs).collect(Collectors.toSet());
            validResult = validResult.stream().filter(cv -> !ea.contains(cv.getPropertyPath().toString())).collect(Collectors.toSet());
        }
        if (!validResult.isEmpty()) {
            throw new ConstraintViolationException(validResult);
        }
    }

}
