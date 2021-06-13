package com.n26.transactions_statistics.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author mir00r on 13/6/21
 * @project IntelliJ IDEA
 */
public class AppUtil {

    /**
     * Method that check if JSON is valid.
     *
     * @param jsonInString
     * @return boolean
     * @author mir00r
     * @since 13/6/21
     */
    public static boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Method to parse the id field.
     *
     * @param transaction
     * @return long
     * @author mir00r
     * @since 13/6/21
     */
    public static long parseId(JSONObject transaction) {
        return (long) (int) transaction.get(Constants.JSON_FIELD_ID);
    }

    /**
     * Method to parse the amount field.
     *
     * @param transaction
     * @return BigDecimal
     * @author mir00r
     * @since 13/6/21
     */
    public static BigDecimal parseAmount(JSONObject transaction) {
        return new BigDecimal((String) transaction.get(Constants.JSON_FIELD_AMOUNT));
    }

    /**
     * Method to parse the amount field.
     *
     * @param transaction
     * @return BigDecimal
     * @author mir00r
     * @since 13/6/21
     */
    public static Instant parseTime(JSONObject transaction) {
        String time = (String) transaction.get(Constants.JSON_FIELD_TIMESTAMP);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        return ZonedDateTime.parse(time, formatter.withZone(ZoneId.of("UTC"))).toInstant();
    }
}
