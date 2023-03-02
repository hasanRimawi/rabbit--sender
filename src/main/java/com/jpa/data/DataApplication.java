package com.jpa.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jpa.persistence.AddressRepo;
import com.jpa.persistence.CarRepo;
import com.jpa.persistence.EmpRepo;
import com.jpa.persistence.PhoneRepo;
import com.jpa.persistence.entities.Address;
import com.jpa.persistence.entities.Car;
import com.jpa.persistence.entities.Employee;
import com.jpa.persistence.entities.PhoneNumber;

@ComponentScan({"com.jpa.controllers","com.jpa.services"})
@EntityScan("com.jpa.persistence.entities")
@EnableJpaRepositories("com.jpa.persistence")
@SpringBootApplication
public class DataApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
		
	}

	@Bean
	CommandLineRunner cLR(EmpRepo empR, PhoneRepo phoR, CarRepo carR, AddressRepo addR) {
		return args ->{
			Set<Car> list1 = new HashSet<Car>();
			list1.add(new Car("BMW"));
			list1.add(new Car("MERCEDES"));
			Employee has = new Employee("Hasan", "Alrimawi", "Front-End");
			empR.save(has);
			empR.save(new Employee("Hamody", "Mokbel", "QA"));
			empR.save(new Employee("Bara", "Jabaly", "Mobile"));
			empR.save(new Employee("Mohammed", "Balawi", "Back-End", new Address("Test Address")));
			phoR.save(new PhoneNumber(Long.parseLong("0592512460")));
			phoR.save(new PhoneNumber(Long.parseLong("0598351456")));
			carR.save(new Car("BMW"));
			carR.save(new Car("MERCEDES"));
			carR.save(new Car("AUDI"));
			carR.save(new Car("VOLKSWAGEN"));
			addR.save(new Address("Ramllah"));
			addR.save(new Address("Nablus"));
			addR.save(new Address("Jenin"));
			addR.save(new Address("Gaza"));
		};
	}
}
