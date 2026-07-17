package com.lautaro.spring.boot.jpa.springboot_jpa;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.lautaro.spring.boot.jpa.springboot_jpa.entities.Person;
import com.lautaro.spring.boot.jpa.springboot_jpa.dto.PersonDto;
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
		System.out.println("================================================================================================");
		//personalizedQueries();
		personalizedQueries2();
		//delete();
		//delete2();
		//update();
		//create();
		//findOne();
		//List();
		System.out.println("================================================================================================");
	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a buscar: ");
		Long id = scanner.nextLong();
		scanner.close();
		
		String name = personRepository.getNameById(id);
		System.out.println("Nombre de la persona con id " + id + ": " + name);

		Long userId = personRepository.getIdById(id);
		System.out.println("ID de la persona con id " + id + ": " + userId);

		String fullName = personRepository.getFullNameById(id);
		System.out.println("Nombre completo de la persona con id " + id + ": " + fullName);

		Optional<Object> optionalReg = personRepository.obtenerPersonFullDataById(id);
		 if(optionalReg.isPresent()) {
			 Object[] reg = (Object[]) optionalReg.get();
			 System.out.println("Datos de la persona con nombre: " + reg[0] + ", Apellido: " + reg[1] + ", Lenguaje de programación: " + reg[2]);
			 ;

		List<Object[]> personsData = personRepository.obtenerPersonsFullData();
		System.out.println("Datos de todas las personas:");
		for (Object[] data : personsData) {
			System.out.println("Nombre: " + data[0] + ", Apellido: " + data[1] + ", Lenguaje de programación: " + data[2]);
		}
	}

	}

@Transactional(readOnly = true)
	public void personalizedQueries2(){
		List<Object[]> personsData = personRepository.findAllMixPersonDataList();
		System.out.println("Datos de todas las personas:");
		for (Object[] data : personsData) {
			System.out.println("Lenguaje de programación: " + data[1] + ", Persona: " + data[0]);
		}

		List<Person> persons = personRepository.findAllClassPerson();
		System.out.println("Datos de todas las personas (usando constructor):");
		for (Person person : persons) {
			System.out.println(person);	
		}
		
		List<PersonDto> personsDto = personRepository.findAllClassPersonDto();
		System.out.println("Datos de todas las personas (usando DTO):");
		for (PersonDto person : personsDto) {
			System.out.println(person);
		}
	}

@Transactional
	public void delete2() {
		personRepository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar: ");
		Long id = scanner.nextLong();
		Optional<Person> optionalPerson = personRepository.findById(id);
		optionalPerson.ifPresentOrElse(person -> personRepository.delete(person),
		 () -> System.out.println("Persona no encontrada con id: " + id));
		personRepository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@Transactional
	public void delete() {
		personRepository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar: ");
		Long id = scanner.nextLong();
		Optional<Person> optionalPerson = personRepository.findById(id);
		optionalPerson.ifPresentOrElse(person -> personRepository.delete(person),
		 () -> System.out.println("Persona no encontrada con id: " + id));
		personRepository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@Transactional
	public void update() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a actualizar: ");
		Long id = scanner.nextLong();
		Optional<Person> optionalPerson = personRepository.findById(id);
		//optionalPerson.ifPresent(person -> {
		if (optionalPerson.isPresent()) {
			Person person = optionalPerson.get();	
			System.out.println("Persona encontrada: " + person);
			System.out.println("Ingrese el nuevo lenguaje de programacion: ");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage);
			personRepository.save(person);
			System.out.println("Persona actualizada: " + person);
		} else {
			System.out.println("Persona no encontrada con id: " + id);
		}
		scanner.close();
	}

	@Transactional
public void create() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Ingrese el nombre:");
    String name = scanner.nextLine();

    System.out.println("Ingrese el apellido:");
    String lastName = scanner.nextLine();

    System.out.println("Ingrese el lenguaje de programación:");
    String programmingLanguage = scanner.nextLine();

    Person person = new Person(null, name, lastName, programmingLanguage);
    Person savedPerson = personRepository.save(person);

    System.out.println(savedPerson);

    String fullName = personRepository.getFullNameById(savedPerson.getId());
    System.out.println("Nombre completo: " + fullName);
	scanner.close();
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
