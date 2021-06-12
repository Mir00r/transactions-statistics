package com.n26.transactions_statistics.domains.transactions.repositoriies;

import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT p FROM Transaction p WHERE (:q IS NULL OR p.createdBy LIKE %:q%) AND p.deleted=FALSE")
    Page<Transaction> search(@Param("q") String query, Pageable pageable);

    @Query("SELECT p FROM Transaction p WHERE p.id=:id AND p.deleted=FALSE")
    Optional<Transaction> find(@Param("id") Long id);
}
