package com.lautaro.spring.boot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lautaro.spring.boot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p.name FROM Person p WHERE p.id = ?1")
    String getNameById(Long id);

    @Query("SELECT p.id FROM Person p WHERE p.id = ?1")
    Long getIdById(Long id);

    @Query("SELECT concat(p.name, ' ', p.lastName) as fullName FROM Person p WHERE p.id = ?1")
    String getFullNameById(Long id);

    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Optional<Person> findOne(Long id);

    
    @Query("SELECT p FROM Person p WHERE p.name = ?1")
    Optional<Person> findOneByName(String name);

    
    @Query("SELECT p FROM Person p WHERE p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1 and p.name = ?2")
    //List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("SELECT p.name, p.programmingLanguage from Person p ")
    List<Object[]> obtenerPersonData();

    @Query("SELECT p.name, p.lastName, p.programmingLanguage from Person p ")
    List<Object[]> obtenerPersonsFullData();

    @Query("SELECT p.name, p.lastName, p.programmingLanguage from Person p WHERE p.id = ?1")
    Optional<Object> obtenerPersonFullDataById(Long id);

    @Query("SELECT p.name, p.programmingLanguage from Person p WHERE p.name = ?1")
    List<Object[]> obtenerPersonData(String name);
    
    @Query("SELECT p.name, p.programmingLanguage from Person p WHERE p.programmingLanguage = ?1 and p.name = ?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);
    
    @Query("SELECT p.name, p.programmingLanguage from Person p WHERE p.programmingLanguage = ?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);

    Optional<Person> findByNameContaining(String name);
}


