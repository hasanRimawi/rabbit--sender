package com.jpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jpa.persistence.AddressRepo;
import com.jpa.persistence.EmpRepo;
import com.jpa.persistence.entities.Address;
import com.jpa.persistence.entities.Employee;

@Service
public class EmpService {
	@Autowired
	private EmpRepo empRepo;

	@Autowired
	private AddressRepo addressRepo;

	public void addUser(Employee userRecord) {
		empRepo.save(userRecord);
	}

	public Optional<Employee> findById(Long id) {
		return empRepo.findById(id);
	}

	public Iterable<Employee> getAll() {
		return empRepo.findAll(Sort.by("position"));
	}

	public Long findCount() {
		return empRepo.count();
	}

	public void deleteEmp(Long id) {
		empRepo.deleteById(id);
	}

	public Employee attachAddress(Long empId, Long addressId) {

		Employee emp = empRepo.findById(empId).get();
		Address num = addressRepo.findById(addressId).get();
		emp.setAddress(num);
		return empRepo.save(emp);

	}

	public List<Employee> getLikeEmp(String pattern) {
		return empRepo.findByFirstNameLike(pattern);
	}

//	public Employee deleteADDRESS(Long id) {
//		Employee temp = empRepo.findById(id).get();
//		temp.setAddress(null);
//		return empRepo.save(temp);
//	}

//	public List<Employee> gett(Long empId){
//		return empRepo.getThem(empId);
//	}
}
