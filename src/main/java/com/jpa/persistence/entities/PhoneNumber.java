package com.jpa.persistence.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class PhoneNumber {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Long number;
//	@JsonIgnore
//	@ManyToOne(cascade = CascadeType.MERGE)	// the merge is to attach the employee with the phone entity
//	@JoinColumn(name = "employee_id", referencedColumnName = "id")
//	private Employee emp;
	
	// the preceding was commented out in order to make the relation unidirectional
	// if it was bidirectional then no penalty of executing more SQL statements or making new table will be found
	
	public PhoneNumber() {}
//	public PhoneNumber(Long number, Employee emp) {
//		this.number = number;
//		this.emp = emp;
//	}
	public PhoneNumber(Long number) {
		this.number = number;
	}
	
//	public Employee getEmp() {
//		return emp;
//	}
//
//	public void setEmp(Employee emp) {
//		this.emp = emp;
//	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	
	
}
