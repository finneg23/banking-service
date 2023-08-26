package com.techelevator.dao;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.CreateTransactionDTO;
import com.techelevator.tenmo.model.TransactionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransactionDaoTests extends BaseDaoTests{
    private JdbcTransactionDao sut3;
    private JdbcUserDao sut1;
    private JdbcAccountDao sut2;
    private final TransactionDTO TEST_TRANSACTIONDTO_1 =
            new TransactionDTO(3003, new BigDecimal("50.00"), "bob", "user", "pending");

    private final CreateTransactionDTO TEST_CREATEDTO_1 =
            new CreateTransactionDTO(new BigDecimal("123"), "bob" );

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut1 = new JdbcUserDao(jdbcTemplate);
        sut3 = new JdbcTransactionDao(jdbcTemplate, sut1);
        sut2 = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAllTransactionsByUsernameTest() {
        List<TransactionDTO> res = sut3.allTransactionsByUsername("bob");

        Assert.assertEquals(3001, res.get(0).getTransactionId());
        Assert.assertEquals(3002, res.get(1).getTransactionId());
    }

    @Test
    public void transactionCreatedCorrectly() {
        TransactionDTO actualTransactionDTO = sut3.create(TEST_CREATEDTO_1, sut2.getAccount("bob"), true);
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTransactionId(), actualTransactionDTO.getTransactionId());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getFrom(), actualTransactionDTO.getFrom());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTo(), actualTransactionDTO.getTo());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTransferAmount(), actualTransactionDTO.getTransferAmount());
    }

//    @Test
//    public void createTransactionWithInvalidUser() {
//        TEST_TRANSACTIONDTO_1.setFrom("zombie");
//        try {
//            sut3.create(TEST_CREATEDTO_1, sut2.getAccount("bob"), true);
//        } catch (Exception e) {
//            String message = "One of these users do not exist: (zombie / user). Check your spelling.";
//            Assert.assertEquals(message, e.getMessage());
//        }
//    }
//
//    @Test
//    public void createTransactionWithInvalidUsers() {
//        TEST_TRANSACTIONDTO_1.setFrom("test1");
//        try {
//            sut3.create(TEST_CREATEDTO_1, sut2.getAccount("bob"), true);
//        } catch (Exception e) {
//            String message = "You're not God. You can't make transfers happen between other people.";
//            Assert.assertEquals(message, e.getMessage());
//        }
//    }
//
//    @Test
//    public void createTransactionWithSameUser() {
//        TEST_TRANSACTIONDTO_1.setTo("bob");
//        try {
//            sut3.create(TEST_TRANSACTIONDTO_1, sut2.getAccount("bob"));
//        } catch (Exception e) {
//            String message = "You cannot make a transaction to yourself.";
//            Assert.assertEquals(message, e.getMessage());
//        }
//    }
//
//    @Test
//    public void createTransactionWithAmountLargerThanBalance() {
//        TEST_TRANSACTIONDTO_1.setTransferAmount(new BigDecimal(5000));
//        try {
//            sut3.create(TEST_TRANSACTIONDTO_1, sut2.getAccount("bob"));
//        } catch (Exception e) {
//            String message = "Insufficient funds.";
//            Assert.assertEquals(message, e.getMessage());
//        }
//    }
//
//    @Test
//    public void createTransactionWithNegativeAmount() {
//        TEST_TRANSACTIONDTO_1.setTransferAmount(new BigDecimal(-100));
//        try {
//            sut3.create(TEST_TRANSACTIONDTO_1, sut2.getAccount("bob"));
//        } catch (Exception e) {
//            String message = "Insufficient funds.";
//            Assert.assertEquals(message, e.getMessage());
//        }
//    }
}
