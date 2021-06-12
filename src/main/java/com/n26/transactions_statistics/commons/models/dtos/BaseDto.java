package com.n26.transactions_statistics.commons.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto implements Serializable {

    private Long id;

    @JsonProperty(value = "created_at")
    @ApiModelProperty(hidden = true, example = "2020-09-13T03:48:36Z", notes = "Date when this entity was first created. ( Format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z' )")
    private Instant createdAt;

    @JsonProperty("updated_at")
    @ApiModelProperty(hidden = true, example = "2020-09-13T03:48:36Z", notes = "Date when this entity was last updated. ( Format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z' )")
    private Instant updatedAt;

    @ApiModelProperty(hidden = true, example = "57f47dde-ba80-45e4-aada-1dac9d7923d4", notes = "unique string value for every row")
    private String uuid;
}
