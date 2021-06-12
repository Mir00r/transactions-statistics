package com.n26.transactions_statistics.domains.transactions.services;

import com.n26.transactions_statistics.commons.services.CrudService;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
public interface TransactionService extends CrudService<Transaction> {

    void deleteAll();

    List<Transaction> statistics(Instant from, Instant to);
}
