package com.martynov.spring.controllers;

import com.martynov.spring.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trans")
public class TransactionalController {

    private final TransactionService transactionService;

    @PostMapping("")
    public Map<String, String> transferToBankAccount(@RequestParam("username") String username,
                                                     @RequestParam("sum") double sum) {
        return transactionService.transferToBankAccount(username,sum);
    }
}
