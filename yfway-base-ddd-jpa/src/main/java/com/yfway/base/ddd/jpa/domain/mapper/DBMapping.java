package com.yfway.base.ddd.jpa.domain.mapper;

import com.yfway.base.ddd.common.TypeMapping;
import com.yfway.base.ddd.jpa.domain.ext.Auditor;
import com.yfway.base.ddd.jpa.model.AuditorDTO;
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
