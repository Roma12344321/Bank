package com.martynov.spring.repositories;

import com.martynov.spring.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByUsername(String username);
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
    @Query("SELECT p FROM Person p WHERE p.date > :date")
    Page<Person> findByDateAfter(@Param("date") Date date, Pageable pageable);

    Page<Person> findByPhone(String phone, Pageable pageable);

    @Query("SELECT p FROM Person p WHERE p.name LIKE :name%")
    Page<Person> findByNameStartingWith(@Param("name") String name, Pageable pageable);

    Page<Person> findByEmail(String email, Pageable pageable);
}