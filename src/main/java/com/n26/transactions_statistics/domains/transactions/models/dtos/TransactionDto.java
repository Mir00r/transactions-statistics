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

    @ApiModelProperty(hidden = true, example = "1000.00", notes = "specifying the total sum of transaction value in the last 60 seconds")
    private BigDecimal sum;

    @ApiModelProperty(hidden = true, example = "1000.53", notes = "specifying the average amount of transaction value in the last 60 seconds")
    private BigDecimal avg;

    @ApiModelProperty(hidden = true, example = "200000.49", notes = "specifying the single highest transaction value in the last 60 seconds")
    private BigDecimal max;

    @ApiModelProperty(hidden = true, example = "50.49", notes = "specifying the single lowest transaction value in the last 60 seconds")
    private BigDecimal min;

    @ApiModelProperty(hidden = true, example = "10", notes = "specifying the number of transaction value in the last 60 seconds")
    private Long count;
}
