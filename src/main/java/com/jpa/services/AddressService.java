package com.jpa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.persistence.AddressRepo;
import com.jpa.persistence.entities.Address;
@Service
public class AddressService {
	@Autowired
	private AddressRepo addressRepo;
	
	public Iterable<Address> getAddresses(){
		return addressRepo.findAll();
	}
	
	public Optional<Address> getAddress(Long id) {
		return addressRepo.findById(id);
	}
	
	public void deleteAddress(Long id) {
		addressRepo.deleteById(id);
	}
	
}
