package com.example.mysqlconection.controller;

import com.example.mysqlconection.model.Person;
import com.example.mysqlconection.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonRestController {
	private final PersonService personService;

	public PersonRestController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	public List<Person> all() {
		return personService.findAll();
	}

	@GetMapping("/{id}")
	public Person one(@PathVariable Long id) {
		return personService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Person create(@Valid @RequestBody Person person) {
		return personService.create(person);
	}

	@PutMapping("/{id}")
	public Person update(@PathVariable Long id, @Valid @RequestBody Person person) {
		return personService.update(id, person);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		personService.delete(id);
	}
}
