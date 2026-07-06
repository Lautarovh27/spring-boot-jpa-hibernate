package com.lautaro.spring.boot.jpa.springboot_jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.lautaro.spring.boot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

}


