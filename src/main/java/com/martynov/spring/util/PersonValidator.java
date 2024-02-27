package com.martynov.spring.util;

import com.martynov.spring.models.Person;
import com.martynov.spring.service.PersonDetailsService;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person)o;
        if (personService.findByUserName(person.getUsername()).isPresent()) {
            errors.rejectValue("username","","Человек уже существует");
        }
    }
}