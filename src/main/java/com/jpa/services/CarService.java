package com.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.persistence.CarRepo;
import com.jpa.persistence.EmpRepo;
import com.jpa.persistence.entities.Car;
import com.jpa.persistence.entities.Employee;

@Service
public class CarService {

	@Autowired
	private CarRepo carRepo;
	
	@Autowired
	private EmpRepo empRepo;
	public Iterable<Car> getCars(){
		return carRepo.findAll();
	}
	
	public Car attachEmployee(Long empId, Long carId) {
		try {
			Employee emp = empRepo.findById(empId).get();
			try {
				Car num = carRepo.findById(carId).get();
				num.addToSet(emp);
				return carRepo.save(num);
			} catch (Exception e) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
