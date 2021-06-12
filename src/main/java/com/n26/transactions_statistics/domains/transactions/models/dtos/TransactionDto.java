package com.n26.transactions_statistics.domains.transactions.models.dtos;

import com.n26.transactions_statistics.commons.models.dtos.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto extends BaseDto {

    @ApiModelProperty(example = "12.3343", notes = "transaction amount; a string of arbitrary length that is parsable as a BigDecimal")
    private BigDecimal amount;

    @ApiModelProperty(example = "2021-06-17T09:59:51.312Z", notes = "transaction time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ\u200B in the UTC timezone (this is not the current timestamp)")
    private Instant time;
}
