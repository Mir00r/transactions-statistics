package com.n26.transactions_statistics.commons.services;

import com.n26.transactions_statistics.commons.models.entities.BaseEntity;
import com.n26.transactions_statistics.exceptions.notfound.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
public interface CrudService<T extends BaseEntity> {
    Page<T> search(String query, Integer page, Integer size);

    T save(T entity);

    Optional<T> find(Long id);

    void delete(Long id, Boolean softDelete) throws NotFoundException;

}
