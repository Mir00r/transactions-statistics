package com.n26.transactions_statistics.domains.transactions.models.mappers;

import com.n26.transactions_statistics.commons.models.mapper.BaseMapper;
import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionDto;
import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionStatsDto;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@Component
public class TransactionMapper implements BaseMapper<Transaction, TransactionDto> {

    @Override
    public TransactionDto map(Transaction entity) {
        TransactionDto dto = new TransactionDto();
        dto.setId(entity.getId());
        dto.setUuid(entity.getUuid());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setAmount(entity.getAmount());
        dto.setTime(entity.getTime());
        return dto;
    }

    public TransactionStatsDto mapStats(List<Transaction> entities) {
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

    @Override
    public Transaction map(TransactionDto dto, Transaction exEntity) {
        Transaction entity = new Transaction();
        entity.setAmount(dto.getAmount());
        entity.setTime(dto.getTime());
        return entity;
    }
}
