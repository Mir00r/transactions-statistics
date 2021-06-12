package com.n26.transactions_statistics.domains.transactions.controllers;

import com.n26.transactions_statistics.commons.controllers.CrudController;
import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionDto;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import com.n26.transactions_statistics.domains.transactions.models.mappers.TransactionMapper;
import com.n26.transactions_statistics.domains.transactions.services.TransactionService;
import com.n26.transactions_statistics.exceptions.notfound.NotFoundException;
import com.n26.transactions_statistics.utils.Constants;
import com.n26.transactions_statistics.utils.Router;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@RestController
@Api(tags = Constants.TRANSACTIONS, description = Router.REST_API)
public class TransactionController implements CrudController<TransactionDto> {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @Override
    @ApiOperation(value = "Get all list of transaction", response = TransactionDto[].class)
    @GetMapping(Router.SEARCH_TRANSACTIONS)
    public ResponseEntity<Page<TransactionDto>> search(
            @RequestParam(value = "q", defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<Transaction> entities = this.transactionService.search(query, page, size);
        return ResponseEntity.ok(entities.map(this.transactionMapper::map));
    }

    @Override
    @ApiOperation(value = "Get transaction by id", response = TransactionDto[].class)
    @GetMapping(Router.FIND_TRANSACTIONS)
    public ResponseEntity<TransactionDto> find(@PathVariable("id") Long id) throws NotFoundException {
        Transaction transaction = this.transactionService.find(id).orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
        return ResponseEntity.ok(this.transactionMapper.map(transaction));
    }

    @Override
    @ApiOperation(value = "Create a new transaction", response = TransactionDto[].class)
    @PostMapping(Router.CREATE_TRANSACTIONS)
    public ResponseEntity<TransactionDto> create(@Valid @RequestBody TransactionDto dto) {
        Transaction entity = this.transactionService.save(this.transactionMapper.map(dto, null));
        return ResponseEntity.ok(this.transactionMapper.map(entity));
    }

    @Override
    @ApiOperation(value = "Update an existing transaction by id", response = TransactionDto[].class)
    @PatchMapping(Router.UPDATE_TRANSACTIONS)
    public ResponseEntity<TransactionDto> update(@PathVariable("id") Long id, @Valid @RequestBody TransactionDto dto) throws NotFoundException {
        Transaction transaction = this.transactionService.find(id).orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
        transaction = this.transactionService.save(this.transactionMapper.map(dto, transaction));
        return ResponseEntity.ok(this.transactionMapper.map(transaction));
    }

    @Override
    @ApiOperation(value = "Delete transaction by id", response = TransactionDto[].class)
    @DeleteMapping(Router.DELETE_TRANSACTIONS)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws NotFoundException {
        this.transactionService.delete(id, true);
        return ResponseEntity.ok().build();
    }
}
