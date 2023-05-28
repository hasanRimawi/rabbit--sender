package com.jpa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.jpa.persistence.entities.Address;
import com.jpa.persistence.entities.Car;
import com.jpa.persistence.entities.Employee;
import com.jpa.persistence.entities.PhoneNumber;
import com.jpa.services.AddressService;
import com.jpa.services.CarService;
import com.jpa.services.EmpService;
import com.jpa.services.PhoneService;
import com.jpa.services.RabbitSender;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequestMapping(path = "/ser")
@RestController
public class Controller_A {
	@Autowired
	private EmpService empservice;

	@Autowired
	private PhoneService phoneService;

	@Autowired
	private CarService carService;

	@Autowired
	private AddressService addressService;

	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private RabbitSender rabbitService;

	@Value("${server.port}")
	private String port;

	// body: {
	// "id" = "..",
	// "firstName" = "..",
	// "lastName" = "..",
	// "position" = ".."
	// }

	@RequestMapping(path = "/**")
	public ResponseEntity<String> wrongPath() {
		return new ResponseEntity<String>("Wrong URI entered", HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/addEmp")
	public Employee addEmp(@RequestBody Employee emp) {
		empservice.addUser(emp);
		return emp;
	}
	@GetMapping(path="/rabbit/{routingKey1}/{routingKey2}/{identifier}")
	public String sendIt(@PathVariable String routingKey1, @PathVariable String routingKey2, @PathVariable String identifier) {
		rabbitService.sendEmployees(routingKey1, routingKey2);
		System.out.println("after requesting employees: " + Thread.currentThread().getId());
		rabbitService.send(routingKey1, identifier);
		System.out.println("after requesting msg: " + Thread.currentThread().getId());
		return "DONE";
	}

	@GetMapping(path = "/flux")
	public Flux<String> flux() {
		return webClient.build().get().uri("http://localhost:8085/api/stream").retrieve().bodyToFlux(String.class)
				.subscribeOn(Schedulers.single());
	}

	@GetMapping(path = "/getemp/{id}")
	public Optional<Employee> getCar(@PathVariable String id) {
		return empservice.findById(Long.parseLong(id));

	}

	@GetMapping(path = "/getAllEmps")
	public Iterable<Employee> getAll() {
		System.out.println("GetAllEmps: " + Thread.currentThread().getId());

		System.out.println("this service port is: " + port);

		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empservice.getAll();
	}

	@GetMapping(path = "/getWord")
	public ResponseEntity<String> getWord() {
		return restTemplate.getForEntity("http://localhost:8085/api/getAll", String.class);
	}

	@GetMapping(path = "/phoneNumbers")
	public Iterable<PhoneNumber> getNumbersp() {
		return phoneService.getAll();
	}

	@PostMapping(path = "/addPhoneNumber")
	public PhoneNumber addNumber(@RequestBody PhoneNumber num) {
		return phoneService.addPhone(num);
	}

	@PutMapping(path = "/{empId}/phoneEmp/{phoneId}")
	public ResponseEntity<Employee> linkNumber(@PathVariable Long empId, @PathVariable Long phoneId) {
		return new ResponseEntity<Employee>(phoneService.attachEmployee((empId), (phoneId)),
				phoneService.attachEmployee((empId), (phoneId)) == null ? HttpStatus.NOT_FOUND : HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "/delEmp/{id}")
	public void deleteEmp(@PathVariable Long id) {
		empservice.deleteEmp(id);
	}

	@DeleteMapping(path = "/delNum/{id}")
	public void deleteNum(@PathVariable Long id) {
		phoneService.deletePhone(id);
	}

	@DeleteMapping(path = "/deladd/{addid}")
	public void deleteAddress(@PathVariable Long addid) {
		addressService.deleteAddress(addid);
	}

	@GetMapping(path = "/cars")
	public Iterable<Car> getCars() {
		System.out.println("AFTER UPDATE");
		System.out.println("H");
		return carService.getCars();
	}

	@PutMapping(path = "/emptocar/{empId}/{carId}")
	public ResponseEntity<Car> linkCar(@PathVariable Long empId, @PathVariable Long carId) {
		return new ResponseEntity<Car>(
				carService.attachEmployee(empId, carId) != null ? carService.attachEmployee(empId, carId) : null,
				carService.attachEmployee(empId, carId) == null ? HttpStatus.NOT_FOUND : HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/addresses")
	public Iterable<Address> getAddresses() {
		return addressService.getAddresses();
	}

	@GetMapping(path = "/getAddress/{addressId}")
	public Optional<Address> getAddress(@PathVariable Long addressId) {
		return addressService.getAddress(addressId);
	}

	@PutMapping(path = "/attachAddressToEmp/{addressId}/{empId}")
	public ResponseEntity<Employee> linkAddress(@PathVariable Long addressId, @PathVariable Long empId) {
		return new ResponseEntity<Employee>(empservice.attachAddress(empId, addressId),
				empservice.attachAddress(empId, addressId) == null ? HttpStatus.NOT_FOUND : HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/alikeEmp/{pattern}")
	public List<Employee> getAlike(@PathVariable String pattern) {
		return empservice.getLikeEmp(pattern);
	}

}
