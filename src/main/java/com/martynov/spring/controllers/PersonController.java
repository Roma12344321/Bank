package com.martynov.spring.controllers;

import com.martynov.spring.models.Person;
import com.martynov.spring.repositories.PersonRepository;
import com.martynov.spring.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;

    @PutMapping("/updateContactInfo/{id}")
    public ResponseEntity<?> updateContactInfo(@PathVariable int id, @RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        boolean updateResult = personService.updateContactInfo(id, email, phone);
        if (!updateResult) {
            return new ResponseEntity<>("Email or phone is already in use or user not found.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Contact information updated successfully.", HttpStatus.OK);
    }
    @GetMapping("/search")
    public Page<Person> searchPersons(
            @RequestParam(required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date date,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            Pageable pageable) {
        if (date != null) {
            return personRepository.findByDateAfter(date, pageable);
        } else if (phone != null) {
            return personRepository.findByPhone(phone, pageable);
        } else if (name != null) {
            return personRepository.findByNameStartingWith(name, pageable);
        } else if (email != null) {
            return personRepository.findByEmail(email, pageable);
        } else {
            return personRepository.findAll(pageable);
        }
    }
}
