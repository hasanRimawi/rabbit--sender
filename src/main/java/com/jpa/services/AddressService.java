package com.jpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.persistence.AddressRepo;
import com.jpa.persistence.EmpRepo;
import com.jpa.persistence.entities.Address;
import com.jpa.persistence.entities.Employee;
@Service
public class AddressService {
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private EmpRepo empRepo;
	
	public Iterable<Address> getAddresses(){
		return addressRepo.findAll();
	}
	
	public Optional<Address> getAddress(Long id) {
		return addressRepo.findById(id);
	}
	
	public void deleteAddress(Long id) {
		List<Employee> wantedEmployee = empRepo.findByAddress(id);
		Employee temp = wantedEmployee.get(0);
		temp.setAddress(null);
		empRepo.save(temp);
		addressRepo.deleteById(id);
	}
}
