package com.martynov.spring.controllers;

import com.martynov.spring.dto.AuthenticationDto;
import com.martynov.spring.dto.PersonDto;
import com.martynov.spring.models.Person;
import com.martynov.spring.security.JWTUtil;
import com.martynov.spring.service.RegistrationService;
import com.martynov.spring.util.PersonValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/registration")
    public Map<String,String> performRegistration(@RequestBody @Valid PersonDto personDto,
                                                  BindingResult bindingResult) {
        Person person = convertToPerson(personDto);
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return Map.of("message","error");
        }
        registrationService.register(person,personDto.getBankAccountDto().getSum());
        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt_token",token);
    }
    @PostMapping("/login")
    public Map<String,String> performLogin(@RequestBody AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDto.getUsername(),authenticationDto.getPassword()
        );
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e){
            return Map.of("message","incorrect credentials");
        }
        String token = jwtUtil.generateToken(authenticationDto.getUsername());
        return Map.of("jwt_token",token);
    }
    private Person convertToPerson(PersonDto personDto) {
        return modelMapper.map(personDto,Person.class);
    }
}