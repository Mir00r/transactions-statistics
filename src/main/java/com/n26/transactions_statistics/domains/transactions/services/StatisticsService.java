package com.n26.transactions_statistics.domains.transactions.services;

import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionStatsDto;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

/**
 * @author mir00r on 13/6/21
 * @project IntelliJ IDEA
 */
@Service
public class StatisticsService {

    /**
     * Method that creates statistics based on transaction.
     *
     * @param entities
     * @return statistic
     * @author mir00r
     * @since 13/6/21
     */
    public TransactionStatsDto create(List<Transaction> entities) {
        TransactionStatsDto dto = new TransactionStatsDto();

        dto.setCount((long) entities.size());
        dto.setSum(entities.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP)
        );

        dto.setMax(entities.stream()
                .map(Transaction::getAmount)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP)
        );
        dto.setMin(entities.stream()
                .map(Transaction::getAmount)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP)
        );
        if (!dto.getSum().equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)))
            dto.setAvg(dto.getSum().divide(BigDecimal.valueOf(entities.size()), RoundingMode.HALF_UP));
        return dto;
    }
}
