package com.jpa.persistence;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.persistence.entities.Employee;

@Repository
public interface EmpRepo extends JpaRepository<Employee, Long>{
	@Query("SELECT t FROM Employee t WHERE t.firstName LIKE %:pattern%")
	public List<Employee> findByFirstNameLike(@Param("pattern") String pattern);
	
	@Query(value = "SELECT * FROM Employee t where t.address_id = :addressId", nativeQuery = true)
	public List<Employee> findByAddress(@Param("addressId") Long addressId);

}
