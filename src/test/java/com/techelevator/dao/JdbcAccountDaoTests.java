package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.PrimaryAccountDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{
    private JdbcAccountDao sut;

    private PrimaryAccountDTO testPrimaryAccountDto;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);

        testPrimaryAccountDto = new PrimaryAccountDTO("bob", new BigDecimal("1000.00"));
    }

    @Test
    public void findAccountByUsernameTest() {
        Assert.assertEquals(testPrimaryAccountDto.getUsername(), sut.findAccountByUsername("bob").getUsername());
        Assert.assertEquals(testPrimaryAccountDto.getBalance(), sut.findAccountByUsername("bob").getBalance());
    }
}
