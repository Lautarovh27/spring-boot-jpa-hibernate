package com.lautaro.spring.boot.jpa.springboot_jpa;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.lautaro.spring.boot.jpa.springboot_jpa.entities.Person;
import com.lautaro.spring.boot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		update();
		//create();
		//findOne();
		//List();
	}

	@Transactional
	public void update() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a actualizar: ");
		Long id = scanner.nextLong();
		Optional<Person> optionalPerson = personRepository.findById(id);
		optionalPerson.ifPresent(person -> {
			System.out.println("Persona encontrada: " + person);
			System.out.println("Ingrese el nuevo lenguaje de programacion: ");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage);
			personRepository.save(person);
			System.out.println("Persona actualizada: " + person);
		});
		scanner.close();
	}

	@Transactional
	public void create() {
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		String lastName = scanner.next();
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastName, programmingLanguage);
		Person savedPerson = personRepository.save(person);
		System.out.println(savedPerson);

		personRepository.findById(savedPerson.getId()).ifPresent(System.out::println);

	}
	
	@Transactional(readOnly = true)
	public void findOne(){
		//Person person = null;
		//Optional<Person> optionalPerson = personRepository.findById(1L);
		//if(!optionalPerson.isEmpty()){
		//	person = optionalPerson.get();
		//}
		//System.out.println(person);
		personRepository.findByNameContaining("ria").ifPresent(System.out::println);

	}

	@Transactional(readOnly = true)
	public void List(){

		//List<Person> persons = (List<Person>) personRepository.findAll();
		
		List<Person> persons = personRepository.findByProgrammingLanguageAndName("Java", "Andres");
		persons.stream().forEach(person -> System.out.println(person));

		List<Object[]> personsData = personRepository.obtenerPersonData("Pepe");
		personsData.stream().forEach(data -> System.out.println("Name: " + data[0] + ", Programming Language: " + data[1]));

	}
	
}
