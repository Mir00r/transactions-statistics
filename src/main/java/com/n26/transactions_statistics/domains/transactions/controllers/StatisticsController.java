package com.n26.transactions_statistics.domains.transactions.controllers;

import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionDto;
import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionStatsDto;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import com.n26.transactions_statistics.domains.transactions.models.mappers.TransactionMapper;
import com.n26.transactions_statistics.domains.transactions.services.TransactionService;
import com.n26.transactions_statistics.utils.Constants;
import com.n26.transactions_statistics.utils.Router;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

/**
 * @author mir00r on 13/6/21
 * @project IntelliJ IDEA
 */
@RestController
@Api(tags = Constants.STATISTICS, description = Router.REST_API)
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public StatisticsController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @ApiOperation(value = "Get all list of transaction", response = TransactionDto[].class)
    @GetMapping(Router.TRANSACTIONS_STATISTICS)
    public ResponseEntity<TransactionStatsDto> getStatistics() {
        List<Transaction> entities = this.transactionService.find(Instant.now().minusSeconds(60), Instant.now());
        if (entities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        logger.info(entities.toString());
        return ResponseEntity.ok(this.transactionMapper.mapStats(entities));
    }
}
