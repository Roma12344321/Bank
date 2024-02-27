package com.martynov.spring.service;

import com.martynov.spring.models.BankAccount;
import com.martynov.spring.models.Person;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final PersonService personService;
    private final BankAccountService bankAccountService;
    @Transactional
    public Map<String,String> transferToBankAccount(String username, double sum) {
        Person personFrom = personService.getCurrentPerson();
        BankAccount accountFrom = personFrom.getBankAccount();
        Person personTo = personService.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Пользователь для перевода не найден."));
        BankAccount accountTo = personTo.getBankAccount();
        if (accountFrom.getSum() >= sum) {
            accountFrom.setSum(accountFrom.getSum() - sum);
            accountTo.setSum(accountTo.getSum() + sum);
            bankAccountService.save(accountFrom);
            bankAccountService.save(accountTo);
            return Map.of("success","success");
        } else {
            throw new RuntimeException("Недостаточно средств для выполнения перевода.");
        }
    }
}
