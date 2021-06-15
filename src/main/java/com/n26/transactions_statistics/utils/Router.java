package com.n26.transactions_statistics.utils;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
public class Router {

    public static final String REST_API = "Rest API";
    public static final String API = "api/";
    public static final String VERSION = "v1";

    public static final String TRANSACTIONS_STATISTICS = "/statistics";
    public static final String DELETE_ALL_TRANSACTIONS = "/transactions";
    public static final String CREATE_ALL_TRANSACTIONS = "/transactions";
    public static final String FIND_TRANSACTIONS_BY_ID = "/transactions/{id}";
    public static final String SEARCH_ALL_TRANSACTIONS = "/transactions";

    public static final String SEARCH_TRANSACTIONS = API + VERSION + "/search/transactions";
    public static final String CREATE_TRANSACTIONS = API + VERSION + "/create/transactions";
    public static final String FIND_TRANSACTIONS = API + VERSION + "/find/transactions/{id}";
    public static final String UPDATE_TRANSACTIONS = API + VERSION + "/update/transactions/{id}";
    public static final String DELETE_TRANSACTIONS = API + VERSION + "/delete/transactions/{id}";
}
