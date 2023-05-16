package com.jpa.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.jpa.persistence.entities.Employee;
import com.jpa.services.EmpService;

@Controller
public class QlController {
	
	@Autowired
	private EmpService empservice;

	@SchemaMapping(typeName = "Query", value = "allEmployees")
//	or @QueryMapping
	public Iterable<Employee> allEmployees() {
//		Optional<Employee> x = empservice.findById(Long.parseLong("2"));
//		return x==null ? null :x.stream().findFirst().orElse(null);
		return empservice.getAll();
	}

	@MutationMapping
	public Employee addEmployee(@Argument(name= "employee") Employee input) {
		Employee user = new Employee(input.getFirstName(),input.getLastName(), input.getPosition());
//		System.out.println(input.firstname() + " " + input.lastName());
		empservice.addUser(user);
		return user;
	}
	
}

class EmployeeInput{
	private String firstName;
	private String lastName;
	private String position;
	public EmployeeInput(String firstName, String lastName, String position) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
