package com.yfway.base.ddd.jpa.domain.ext;

/**
 * 用于标记逻辑删除事件, DomainEntity implements LogicDelete
 * @author hon_him
 * @since 2022-10-27
 */

public interface LogicDelete {

    boolean isDeleted();

}
