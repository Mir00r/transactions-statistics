package com.n26.transactions_statistics.domains.transactions.repositoriies;

import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE (:q IS NULL OR t.createdBy LIKE %:q%) AND t.deleted=FALSE")
    Page<Transaction> search(@Param("q") String query, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.time between :from AND :to AND t.deleted=FALSE")
    List<Transaction> getStatistics(@Param("from") Instant from, @Param("to") Instant to);

    @Query("SELECT t FROM Transaction t WHERE t.id=:id AND t.deleted=FALSE")
    Optional<Transaction> find(@Param("id") Long id);
}
