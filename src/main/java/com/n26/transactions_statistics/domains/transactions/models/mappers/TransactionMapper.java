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

    /**
     * Method to mapping data from entity to dto class
     *
     * @param entity
     * @return
     */
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

    /**
     * Method to mapping transaction data from entities to statistics dto class
     *
     * @param entities
     * @return
     */
    public TransactionStatsDto mapStats(List<Transaction> entities) {
        TransactionStatsDto dto = new TransactionStatsDto();

        BigDecimal sum = entities.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        dto.setCount((long) entities.size());
        dto.setSum(entities.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP).toString()
        );

        dto.setMax(entities.stream()
                .map(Transaction::getAmount)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP).toString()
        );
        dto.setMin(entities.stream()
                .map(Transaction::getAmount)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP).toString()
        );
        if (!sum.equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)))
            dto.setAvg(sum.divide(BigDecimal.valueOf(entities.size()), RoundingMode.HALF_UP).toString());
        else dto.setAvg("0.00");
        return dto;
    }

    /**
     * Method to mapping data from dto to entity class for creating new entry
     *
     * @param dto
     * @param exEntity
     * @return
     */
    @Override
    public Transaction map(TransactionDto dto, Transaction exEntity) {
        Transaction entity = new Transaction();
        entity.setAmount(dto.getAmount());
        entity.setTime(dto.getTime());
        return entity;
    }
}
