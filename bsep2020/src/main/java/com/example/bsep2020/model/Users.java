package com.example.bsep2020.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

	public Users(Users users) {
		// TODO Auto-generated constructor stub
		this.active=users.getActive();
		this.email=users.getEmail();
		this.roles=users.getRoles();
		this.lastName=users.getLastName();
		this.name=users.getName();
		this.password=users.getPassword();
		this.user_id=users.getUser_id();
		this.username=users.getUsername();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long user_id;
	@Column 
	private String username;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private String lastName;
	@Column
	private int active;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	private Set<Role> roles;
}
