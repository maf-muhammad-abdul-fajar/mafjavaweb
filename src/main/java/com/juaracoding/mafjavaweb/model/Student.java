package com.juaracoding.mafjavaweb.model;

import javax.persistence.*;

@Entity
@Table(name = "MstStudent")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "FirstName", nullable = false)
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "Email")
	private String email;
	
	public Student() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
