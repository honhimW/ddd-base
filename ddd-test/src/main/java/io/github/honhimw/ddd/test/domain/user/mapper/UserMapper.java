package io.github.honhimw.ddd.test.domain.user.mapper;

import io.github.honhimw.ddd.common.TypeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hon_him
 * @since 2022-10-26
 */
@Mapper
public interface UserMapper extends TypeMapping {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);


}
