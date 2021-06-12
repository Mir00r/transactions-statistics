package com.n26.transactions_statistics.domains.transactions.controllers;

import com.n26.transactions_statistics.commons.controllers.CrudController;
import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionDto;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import com.n26.transactions_statistics.domains.transactions.models.mappers.TransactionMapper;
import com.n26.transactions_statistics.domains.transactions.services.TransactionService;
import com.n26.transactions_statistics.exceptions.notfound.NotFoundException;
import com.n26.transactions_statistics.utils.Constants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@RestController
@Api(tags = Constants.TRANSACTIONS, description = Constants.REST_API)
public class TransactionController implements CrudController<TransactionDto> {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public ResponseEntity<Page<TransactionDto>> search(String query, Integer page, Integer size) {
        Page<Transaction> entities = this.transactionService.search(query, page, size);
        return ResponseEntity.ok(entities.stream().map(this.transactionMapper.map(it)));
    }

    @Override
    public ResponseEntity<TransactionDto> find(Long id) throws NotFoundException {
        Transaction transaction = this.transactionService.find(id).orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
        return ResponseEntity.ok(this.transactionMapper.map(transaction));
    }

    @Override
    public ResponseEntity<TransactionDto> create(TransactionDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<TransactionDto> update(Long id, TransactionDto dto) throws NotFoundException {
        Transaction transaction = this.transactionService.find(id).orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
        transaction = this.transactionService.save(this.transactionMapper.map(dto, transaction));
        return ResponseEntity.ok(this.transactionMapper.map(transaction));
    }

    @Override
    public ResponseEntity<Object> delete(Long id) throws NotFoundException {
        this.transactionService.delete(id, true);
        return ResponseEntity.ok().build();
    }
}
