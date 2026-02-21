package com.example.mysqlconection.controller;

import com.example.mysqlconection.model.Person;
import com.example.mysqlconection.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/people")
public class PersonWebController {
	private final PersonService personService;

	public PersonWebController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("people", personService.findAll());
		return "people/list";
	}

	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("person", new Person());
		return "people/form";
	}

	@PostMapping
	public String create(@Valid Person person, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "people/form";
		}
		personService.create(person);
		redirectAttributes.addFlashAttribute("message", "Saved successfully");
		return "redirect:/people";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("person", personService.findById(id));
		return "people/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid Person person, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "people/form";
		}
		personService.update(id, person);
		redirectAttributes.addFlashAttribute("message", "Updated successfully");
		return "redirect:/people";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		personService.delete(id);
		redirectAttributes.addFlashAttribute("message", "Deleted successfully");
		return "redirect:/people";
	}
}
