package com.n26.transactions_statistics.commons.controllers;

import com.n26.transactions_statistics.commons.models.dtos.BaseDto;
import com.n26.transactions_statistics.exceptions.notfound.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
public interface CrudController<T extends BaseDto> {
    ResponseEntity<Page<T>> search(String query, Integer page, Integer size);

    ResponseEntity<T> find(Long id) throws NotFoundException;

    ResponseEntity<T> create(T dto);

    ResponseEntity<T> update(Long id, T dto) throws NotFoundException;

    ResponseEntity<Object> delete(Long id) throws NotFoundException;
}
