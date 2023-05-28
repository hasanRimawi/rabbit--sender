package com.jpa.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AsyncAmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.persistence.entities.Employee;

@Service
public class RabbitSender {
//	final static String  queueName = "spring-boot";
	final static String topicExchangeName = "spring-boot-exchange";
	@Autowired
	private AmqpTemplate rabbitTemplate;

//	@Autowired
//	private AsyncAmqpTemplate asyncRabbit;

	public void send(String routingKey, String whatever) {
//		rabbitTemplate.convertAndSend(topicExchangeName, routingKey, "I'm the transported message: " + whatever);
	}

	public void sendEmployees(String routingKey1, String routingKey2) {
		List<Employee> employees = makeEmployees();

		for (int i = 0; i < 9; i++) {
			rabbitTemplate.convertAndSend(topicExchangeName, routingKey1, new String("key1  + " + i));
			rabbitTemplate.convertAndSend(topicExchangeName, routingKey2, new String("key2  + " + i));
		}
	}

	public List<Employee> makeEmployees() {

		List<Employee> lista = new ArrayList<Employee>(List.of(new Employee("asd", "zxc", "qwe"),
				new Employee("qqq", "xxx", "qwe"), new Employee("John", "Doe", "IT"),
				new Employee("Jane", "Smith", "HR"), new Employee("Michael", "Johnson", "Sales"),
				new Employee("Emily", "Brown", "Marketing"), new Employee("David", "Davis", "Finance"),
				new Employee("Sarah", "Miller", "Operations"), new Employee("Robert", "Anderson", "Research"),
				new Employee("Jessica", "Wilson", "Legal"), new Employee("Christopher", "Lee", "Customer Service"),
				new Employee("Laura", "Moore", "Engineering"), new Employee("Kevin", "Taylor", "Production"),
				new Employee("Jennifer", "Thompson", "Quality Control"),
				new Employee("Brian", "Clark", "Administration")));
		return lista;
	}
}
