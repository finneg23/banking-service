package com.techelevator.dao;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.CreateTransactionDTO;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.security.Principal;

public class JdbcTransactionDaoTests extends BaseDaoTests{
    private JdbcTransactionDao sut3;
    private JdbcUserDao sut1;
    private JdbcAccountDao sut2;
    private final TransactionDTO TEST_TRANSACTIONDTO_1 =
            new TransactionDTO(3003, BigDecimal.valueOf(50), "bob", "user");
    private final CreateTransactionDTO TEST_CREATETRANSACTIONDTO_1 =
            new CreateTransactionDTO(BigDecimal.valueOf(50), "user");

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut1 = new JdbcUserDao(jdbcTemplate);
        sut3 = new JdbcTransactionDao(jdbcTemplate, sut1);
        sut2 = new JdbcAccountDao(jdbcTemplate);
    }
/*TODO
    @Test
    public void transactionCreatedCorrectly() {
        TransactionDTO actualTransactionDTO = sut3.create(TEST_CREATETRANSACTIONDTO_1, sut2.getAccount("bob"));
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTransferId(), actualTransactionDTO.getTransferId());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getFrom(), actualTransactionDTO.getFrom());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTo(), actualTransactionDTO.getTo());
        Assert.assertEquals(TEST_TRANSACTIONDTO_1.getTransferAmount(), actualTransactionDTO.getTransferAmount());
    }
 */
}
