package com.n26.transactions_statistics.domains.transactions.controllers;

import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionDto;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import com.n26.transactions_statistics.domains.transactions.models.mappers.TransactionMapper;
import com.n26.transactions_statistics.domains.transactions.services.TransactionService;
import com.n26.transactions_statistics.utils.AppUtil;
import com.n26.transactions_statistics.utils.Constants;
import com.n26.transactions_statistics.utils.Router;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mir00r on 13/6/21
 * @project IntelliJ IDEA
 */
@RestController
@Api(tags = Constants.TRANSACTIONS, description = Router.REST_API)
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }


    /**
     * Method that list all transaction
     *
     * @return ResponseEntity with a < code >List<Transaction></code> object and the HTTP status
     * <p>
     * HTTP Status:
     * <p>
     * 200 - OK: Everything worked as expected.
     * 204 - Not Found: The requested resource not found.
     * @author mir00r
     * @since 13/6/21
     */
    @ApiOperation(value = "Get all list of transaction", response = TransactionDto[].class)
    @GetMapping(Router.SEARCH_ALL_TRANSACTIONS)
    public ResponseEntity<List<TransactionDto>> find() {
        List<Transaction> entities = this.transactionService.find();
        if (entities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        logger.info(entities.toString());
        return ResponseEntity.ok(entities.stream().map(this.transactionMapper::map).collect(Collectors.toList()));
    }

    /**
     * Method that creates a transaction.
     *
     * @param jsonTransaction, where:
     *                         <p>
     *                         id - transaction id;
     *                         amount – transaction amount; a string of arbitrary length that is parsable as a BigDecimal;
     *                         timestamp – transaction time in the ISO 8601 format
     *                         YYYY-MM-DDThh:mm:ss.sss in the UTC timezone (this is not the current timestamp)
     * @return ResponseEntity with a < code >Trip</code> object and the HTTP status
     * <p>
     * HTTP Status:
     * <p>
     * 201 - Created: Everything worked as expected.
     * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
     * 422 - Unprocessable Entity: if any of the fields are not parsable or the initial date is greater than the final date.
     * 500 - Server Errors: something went wrong on API end (These are rare).
     * @author mir00r
     * @since 13/6/21
     */
    @ApiOperation(value = "Create a new transaction")
    @PostMapping(Router.CREATE_ALL_TRANSACTIONS)
    public ResponseEntity<Transaction> create(@Valid @RequestBody JSONObject jsonTransaction) {
        try {
            if (AppUtil.isJSONValid(jsonTransaction.toString())) {
                Transaction transaction = transactionService.create(jsonTransaction);
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(transaction.getUuid()).build().toUri();

                transactionService.add(transaction);
                return ResponseEntity.created(uri).body(null);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            logger.error("JSON fields are not parsable. " + e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }


    /**
     * Method that deletes all existing transaction.
     *
     * @return Returns an empty body with one of the following:
     * <p>
     * 204 - if delete with success
     * 205 - if hasn't delete with success.
     * 500 - Server Errors: something went wrong on API end (These are rare).
     * @author mir00r
     * @since 13/6/21
     */
    @ApiOperation(value = "Delete all transaction")
    @DeleteMapping(Router.DELETE_ALL_TRANSACTIONS)
    public ResponseEntity<Boolean> deleteAll() {
        try {
            this.transactionService.delete();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
