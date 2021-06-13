package com.n26.transactions.ut;

import com.n26.Application;
import com.n26.transactions_statistics.domains.transactions.models.dtos.TransactionStatsDto;
import com.n26.transactions_statistics.domains.transactions.models.entities.Transaction;
import com.n26.transactions_statistics.domains.transactions.services.StatisticsService;
import com.n26.transactions_statistics.domains.transactions.services.TransactionService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author mir00r on 13/6/21
 * @project IntelliJ IDEA
 */
//@RunWith(SpringRunner.class)
@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {
        Application.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionApiUnitTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatisticsService statisticsService;

    @BeforeAll
    public void setUp() {
        this.transactionService.createTransactionList();
    }

    @Test
    @Order(1)
    public void shouldReturnNotNullTransactionService() {
        Assertions.assertNotNull(transactionService);
    }

    @Test
    @Order(2)
    public void shouldReturnNotNullStatisticsService() {
        Assertions.assertNotNull(statisticsService);
    }

    @Test
    @Order(3)
    @SuppressWarnings("unchecked")
    public void shouldReturnTransactionCreatedWithSuccess() {

        String time = "2021-06-21T09:59:51.312Z";

        JSONObject jsonTransaction = new JSONObject();
        jsonTransaction.put("id", 1);
        jsonTransaction.put("timestamp", time);
        jsonTransaction.put("amount", "22.88");
        jsonTransaction.put("uuid", UUID.randomUUID().toString());
        jsonTransaction.put("created_at", Instant.now());


        Transaction transaction = transactionService.create(jsonTransaction);

        Assertions.assertNotNull(transaction);
        Assertions.assertNotNull(transaction.getId().toString(), String.valueOf(jsonTransaction.get("id")));
        Assertions.assertNotNull(transaction.getAmount().toString(), (String) jsonTransaction.get("amount"));
        Assertions.assertNotNull(transaction.getTime().toString(), (String) jsonTransaction.get("timestamp"));
        Assertions.assertNotNull(transaction.getUuid(), (String) jsonTransaction.get("uuid"));
        Assertions.assertNotNull(transaction.getCreatedAt().toString(), jsonTransaction.get("created_at").toString());
    }

    @Test
    @Order(4)
    @SuppressWarnings("unchecked")
    public void shouldReturnTransactionsStatisticsCalculated() {

        this.transactionService.delete();

        String time = Instant.now().toString();

        JSONObject jsonTransaction1 = new JSONObject();
        jsonTransaction1.put("id", 1);
        jsonTransaction1.put("timestamp", time);
        jsonTransaction1.put("amount", "22.88");
        jsonTransaction1.put("uuid", UUID.randomUUID().toString());
        jsonTransaction1.put("created_at", Instant.now());


        Transaction transaction1 = transactionService.create(jsonTransaction1);
        this.transactionService.add(transaction1);

        JSONObject jsonTransaction2 = new JSONObject();
        jsonTransaction2.put("id", 2);
        jsonTransaction2.put("timestamp", time);
        jsonTransaction2.put("amount", "30.88");
        jsonTransaction2.put("uuid", UUID.randomUUID().toString());
        jsonTransaction2.put("created_at", Instant.now());


        Transaction transaction2 = transactionService.create(jsonTransaction2);
        this.transactionService.add(transaction2);

        List<Transaction> entities = this.transactionService.find(Instant.now().minusSeconds(60), Instant.now());
        TransactionStatsDto statistic = this.statisticsService.create(entities);

        Assertions.assertNotNull(statistic);
        Assertions.assertNotNull("53.76", statistic.getSum().toString());
        Assertions.assertNotNull("26.88", statistic.getAvg().toString());
        Assertions.assertNotNull("22.88", statistic.getMin().toString());
        Assertions.assertNotNull("30.00", statistic.getMax().toString());
        Assertions.assertNotNull("2", statistic.getCount().toString());
    }

    @AfterAll
    public void tearDown() {
        this.transactionService.clearObjects();
    }
}
