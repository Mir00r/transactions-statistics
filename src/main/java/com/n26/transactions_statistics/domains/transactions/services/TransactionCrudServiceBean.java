package com.n26.transactions_statistics.domains.transactions.services;

import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import com.n26.transactions_statistics.domains.transactions.repositoriies.TransactionRepository;
import com.n26.transactions_statistics.exceptions.notfound.NotFoundException;
import com.n26.transactions_statistics.utils.PageAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@Service
public class TransactionCrudServiceBean implements TransactionCrudService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionCrudServiceBean(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Page<Transaction> search(String query, Integer page, Integer size) {
        return this.transactionRepository.search(query, PageAttr.getPageRequestExact(page, size));
    }

    @Override
    public Transaction save(Transaction entity) {
        return this.transactionRepository.save(entity);
    }

    @Override
    public Optional<Transaction> find(Long id) {
        return this.transactionRepository.find(id);
    }

    @Override
    public void delete(Long id, Boolean softDelete) throws NotFoundException {
        if (!softDelete) {
            this.transactionRepository.deleteById(id);
            return;
        }
        Transaction transaction = this.find(id).orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
        transaction.setDeleted(true);
        this.save(transaction);
    }

    @Override
    public void deleteAll() {
        this.transactionRepository.deleteAll();
    }

    @Override
    public List<Transaction> statistics(Instant from, Instant to) {
        return this.transactionRepository.getStatistics(from, to);
    }
}
