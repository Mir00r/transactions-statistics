package com.n26.transactions_statistics.commons.models.mapper;

import com.n26.transactions_statistics.commons.models.dtos.BaseDto;
import com.n26.transactions_statistics.commons.models.entities.BaseEntity;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
public interface BaseMapper<T extends BaseEntity, S extends BaseDto> {
    S map(T entity);

    T map(S dto, T exEntity);
}
