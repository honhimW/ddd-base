package io.github.honhimw.ddd.test;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * @author hon_him
 * @since 2022-10-27
 */
@Component
public class IAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("honhim");
    }
}
