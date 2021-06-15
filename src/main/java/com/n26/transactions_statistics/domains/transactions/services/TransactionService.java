package com.n26.transactions_statistics.domains.transactions.services;

import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import com.n26.transactions_statistics.utils.AppUtil;
import com.n26.transactions_statistics.utils.Constants;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author mir00r on 13/6/21
 * @project IntelliJ IDEA
 */
@Service
public class TransactionService {

    private List<Transaction> transactions;

    /**
     * Method to create the travel's list
     *
     * @author Mariana Azevedo
     * @since 09/14/2019
     */
    public void createTransactionList() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
    }

    /**
     * Method to fullfil the transaction object
     *
     * @param jsonTransaction
     * @param transaction
     * @author mir00r
     * @since 13/6/21
     */
    private void setTransactionValues(JSONObject jsonTransaction, Transaction transaction) {

        transaction.setId(jsonTransaction.get(Constants.JSON_FIELD_ID) != null ? AppUtil.parseId(jsonTransaction) : (long) (transactions.size() + 1));
        transaction.setAmount(jsonTransaction.get(Constants.JSON_FIELD_AMOUNT) != null ? AppUtil.parseAmount(jsonTransaction) : transaction.getAmount());
        transaction.setTime(jsonTransaction.get(Constants.JSON_FIELD_TIMESTAMP) != null ? AppUtil.parseTime(jsonTransaction) : transaction.getTime());
        transaction.setDeleted(false);
        transaction.setCreatedAt(Instant.now());
        transaction.setCreatedBy("admin");
        transaction.setUuid(UUID.randomUUID().toString());
    }

    /**
     * Method to create a trip
     *
     * @param jsonTransaction
     * @return Transaction
     * @author mir00r
     * @since 13/6/21
     */
    public Transaction create(JSONObject jsonTransaction) {

        this.createTransactionList();
        Transaction transaction = new Transaction();
        setTransactionValues(jsonTransaction, transaction);

        return transaction;
    }

    /**
     * Method to update a trip
     *
     * @param transaction
     * @param jsonTransaction
     * @return Transaction
     * @author mir00r
     * @since 13/6/21
     */
    public Transaction update(Transaction transaction, JSONObject jsonTransaction) {

        setTransactionValues(jsonTransaction, transaction);
        return transaction;
    }

    /**
     * Method that add an object Transaction
     *
     * @param Transaction
     * @author mir00r
     * @since 13/6/21
     */
    public void add(Transaction Transaction) {
        createTransactionList();
        transactions.add(Transaction);
    }

    /**
     * Method that get all trips
     *
     * @return List
     * @author mir00r
     * @since 13/6/21
     */
    public List<Transaction> find() {
        createTransactionList();
        return transactions;
    }

    /**
     * Method that get all trips
     *
     * @return List
     * @author mir00r
     * @since 13/6/21
     */
    public List<Transaction> find(Instant from, Instant to) {
        createTransactionList();
        return transactions.stream().filter(t -> t.getTime().isAfter(from) && t.getTime().isBefore(to)).collect(Collectors.toList());
    }

    /**
     * Method that get transactions by id
     *
     * @param id
     * @return Trip
     * @author mir00r
     * @since 13/6/21
     */
    public Transaction findById(long id) {
        return transactions.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
    }

    /**
     * Method that deletes the Transaction created
     *
     * @author mir00r
     * @since 13/6/21
     */
    public void delete() {
        this.createTransactionList();
        transactions.clear();
    }

    /**
     * Method to clean objects
     *
     * @author mir00r
     * @since 13/6/21
     */
    public void clearObjects() {
        transactions = null;
    }
}
