package com.javier.spring.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.javier.spring.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	
	private List<Employee> repository = new ArrayList<>();
	
	
	public Employee add(Employee e) {
		repository.add(e);
		return e;
	}
	
	public List<Employee> findAll() {
		return repository;
	}
	
	public Employee findById(long id) {
		Employee result = null;
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < repository.size()) {
			if (repository.get(i).getId() == id) {
				encontrado = true;
				result = repository.get(i);
			} else {
				i++;
			}
		}
		
		return result;
	}
	
	public Employee edit(Employee e) {
		boolean exist = false;
		int i = 0;
		while (!exist && i < repository.size()) {
			if (repository.get(i).getId() == e.getId()) {
				exist = true;
				repository.remove(i);
				repository.add(i, e);
			} else {
				i++;
			}
		}
		
		if (!exist)
			repository.add(e);
		
		return e;
	}


	
	@PostConstruct
	public void init() {
		repository.addAll(
				Arrays.asList(new Employee(1,"Antonio García", "antonio.garcia@openwebinars.net", "954000000"),
						new Employee(2,"María López", "maria.lopez@openwebinars.net", "954000000"),
						new Employee(3,"Ángel Antúnez", "angel.antunez@openwebinars.net", "954000000")
						)
				);
	}

}
