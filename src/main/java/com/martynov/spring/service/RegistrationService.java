package com.martynov.spring.service;

import com.martynov.spring.models.BankAccount;
import com.martynov.spring.models.Person;
import com.martynov.spring.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final BankAccountService bankAccountService;

    @Value("${initial_sum}")
    private int INITIAL_SUM;

    @Transactional
    public void register(Person person, double sum) {
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setRole("ROLE_USER");
        person.setPassword(encodedPassword);
        personRepository.save(person);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setSum(sum);
        bankAccount.setPerson(person);
        bankAccountService.save(bankAccount);
        person.setBankAccount(bankAccount);
    }
}
