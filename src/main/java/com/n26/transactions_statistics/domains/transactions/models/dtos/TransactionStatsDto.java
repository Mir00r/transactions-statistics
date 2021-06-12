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
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatsDto {

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
