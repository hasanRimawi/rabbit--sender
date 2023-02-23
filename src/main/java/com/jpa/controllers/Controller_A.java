package com.jpa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.persistence.entities.Address;
import com.jpa.persistence.entities.Car;
import com.jpa.persistence.entities.Employee;
import com.jpa.persistence.entities.PhoneNumber;
import com.jpa.services.AddressService;
import com.jpa.services.CarService;
import com.jpa.services.EmpService;
import com.jpa.services.PhoneService;

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
	// body: {
	// "id" = "..",
	// "firstName" = "..",
	// "lastName" = "..",
	// "position" = ".."
	// }
	
	@RequestMapping(path="/**")
	public ResponseEntity<String> wrongPath(){
		return new ResponseEntity<String>("Wrong URI entered", HttpStatus.BAD_REQUEST);
	}
	@PostMapping(value = "/addEmp")
	public Employee addEmp(@RequestBody Employee emp) {
		empservice.addUser(emp);
		return emp;
	}

	@GetMapping(path = "/getemp/{id}")
	public Optional<Employee> getCar(@PathVariable String id) {
		return empservice.findById(Long.parseLong(id));

	}

	@GetMapping(path = "/getAllEmps")
	public Iterable<Employee> getAll() {
		return empservice.getAll();
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
	public ResponseEntity<PhoneNumber> linkNumber(@PathVariable Long empId, @PathVariable Long phoneId) {
		return new ResponseEntity<PhoneNumber>(phoneService.attachEmployee((empId), (phoneId)),
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
	
	@DeleteMapping(path = "/deladd/{addid}/{empid}")
	public void deleteAddress(@PathVariable Long addid, @PathVariable Long empid) {
		empservice.deleteADDRESS(empid);
		addressService.deleteAddress(addid);
	}

	@GetMapping(path = "/cars")
	public Iterable<Car> getCars() {
		return carService.getCars();
	}

	@PutMapping(path = "/emptocar/{empId}/{carId}")
	public ResponseEntity<Car> linkCar(@PathVariable Long empId, @PathVariable Long carId) {
		return new ResponseEntity<Car>(
				carService.attachEmployee(empId, carId) != null ? carService.attachEmployee(empId, carId) : null,
				carService.attachEmployee(empId, carId) == null ? HttpStatus.NOT_FOUND : HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/addresses")
	public Iterable<Address> getAddresses(){
		return addressService.getAddresses();
	}
	
	@GetMapping(path = "/getAddress/{addressId}")
	public Optional<Address> getAddress(@PathVariable Long addressId) {
		return addressService.getAddress(addressId);
	}
	
	@PutMapping(path = "/attachAddressToEmp/{addressId}/{empId}")
	public ResponseEntity<Employee> linkAddress(@PathVariable Long addressId, @PathVariable Long empId){
		return new ResponseEntity<Employee>(empservice.attachAddress(empId, addressId),
				empservice.attachAddress(empId, addressId) == null ? HttpStatus.NOT_FOUND : HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/alikeEmp/{pattern}")
	public List<Employee> getAlike(@PathVariable String pattern){
		return empservice.getLikeEmp(pattern);
	}
	
	
}
