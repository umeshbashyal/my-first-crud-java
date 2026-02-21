package com.example.mysqlconection.service;

import com.example.mysqlconection.model.Person;
import com.example.mysqlconection.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public List<Person> findAll() {
		return personRepository.findAll();
	}

	public Person findById(Long id) {
		return personRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Person not found: " + id));
	}

	public Person create(Person person) {
		person.setId(null);
		return personRepository.save(person);
	}

	public Person update(Long id, Person input) {
		Person existing = findById(id);
		existing.setName(input.getName());
		existing.setEmail(input.getEmail());
		return personRepository.save(existing);
	}

	public void delete(Long id) {
		personRepository.deleteById(id);
	}
}
