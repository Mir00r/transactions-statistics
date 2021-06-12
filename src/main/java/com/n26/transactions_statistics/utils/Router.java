package com.n26.transactions_statistics.utils;

/**
 * @author mir00r on 12/6/21
 * @project IntelliJ IDEA
 */
public class Router {

    public static final String REST_API = "Rest API";
    public static final String API = "api/";
    public static final String VERSION = "version/";

    public static final String SEARCH_TRANSACTIONS = API + VERSION + "transactions";
    public static final String CREATE_TRANSACTIONS = API + VERSION + "transactions";
    public static final String FIND_TRANSACTIONS = API + VERSION + "transactions/{id}";
    public static final String UPDATE_TRANSACTIONS = API + VERSION + "transactions/{id}";
    public static final String DELETE_TRANSACTIONS = API + VERSION + "transactions/{id}";
}
