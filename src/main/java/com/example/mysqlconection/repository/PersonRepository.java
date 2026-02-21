package com.example.mysqlconection.repository;

import com.example.mysqlconection.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
