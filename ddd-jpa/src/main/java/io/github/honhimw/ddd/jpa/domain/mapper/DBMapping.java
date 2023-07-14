package io.github.honhimw.ddd.jpa.domain.mapper;

import io.github.honhimw.ddd.common.TypeMapping;
import io.github.honhimw.ddd.jpa.domain.ext.Auditor;
import io.github.honhimw.ddd.jpa.model.AuditorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author hon_him
 * @since 2022-12-14
 */
@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DBMapping extends TypeMapping {

    AuditorDTO do2dto(Auditor auditor);

}
