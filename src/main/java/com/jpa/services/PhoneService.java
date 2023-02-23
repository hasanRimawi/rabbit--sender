package com.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.persistence.EmpRepo;
import com.jpa.persistence.PhoneRepo;
import com.jpa.persistence.entities.Employee;
import com.jpa.persistence.entities.PhoneNumber;

@Service
public class PhoneService {
	@Autowired
	PhoneRepo phoneRepo;
	@Autowired
	EmpRepo empRepo;

	public Iterable<PhoneNumber> getAll() {
		return phoneRepo.findAll();
	}

	public PhoneNumber addPhone(PhoneNumber num) {
		return phoneRepo.save(num);
	}

	public PhoneNumber attachEmployee(Long empId, Long phoneId) {
		try {
			Employee emp = empRepo.findById(empId).get();
			try {
				PhoneNumber num = phoneRepo.findById(phoneId).get();
				num.setEmp(emp);
				return phoneRepo.save(num);
			} catch (Exception e) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public void deletePhone(Long id) {
		phoneRepo.deleteById(id);
	}
}
