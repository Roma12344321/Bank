package com.martynov.spring.service;

import com.martynov.spring.models.Person;
import com.martynov.spring.repositories.PersonRepository;
import com.martynov.spring.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public Optional<Person> findByUserName(String userName) {
        return personRepository.findByUsername(userName);
    }

    public Person getCurrentPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

    @Transactional
    public boolean updateContactInfo(int personId, String newEmail, String newPhone) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (!personOptional.isPresent()) return false;
        Person person = personOptional.get();
        if (newEmail != null && personRepository.existsByEmail(newEmail) && !newEmail.equals(person.getEmail())) {
            return false;
        }
        if (newPhone != null && personRepository.existsByPhone(newPhone) && !newPhone.equals(person.getPhone())) {
            return false;
        }
        if (newEmail != null) person.setEmail(newEmail);
        if (newPhone != null) person.setPhone(newPhone);

        personRepository.save(person);
        return true;
    }
}
