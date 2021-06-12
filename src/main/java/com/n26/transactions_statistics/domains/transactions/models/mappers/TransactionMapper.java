package com.n26.transactions_statistics.domains.transactions.models.mappers;

import com.n26.transactions_statistics.commons.models.mapper.BaseMapper;
import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionDto;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import org.springframework.stereotype.Component;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@Component
public class TransactionMapper implements BaseMapper<Transaction, TransactionDto> {

    @Override
    public TransactionDto map(Transaction entity) {
        TransactionDto dto = new TransactionDto();
        dto.setAmount(entity.getAmount());
        dto.setTime(entity.getTime());
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
