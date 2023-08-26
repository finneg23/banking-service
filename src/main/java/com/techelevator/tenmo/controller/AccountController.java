package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.PrimaryAccountDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDao accountDao;
    public AccountController(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @GetMapping(path ="/accounts")
    public PrimaryAccountDTO getAccount(Principal principal){
        return accountDao.findAccountByUsername(principal.getName());
    }


}
