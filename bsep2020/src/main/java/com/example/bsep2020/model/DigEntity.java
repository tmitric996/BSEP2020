package com.example.bsep2020.model;

import javax.persistence.*;

import com.example.bsep2020.enumeration.EntityType;

import lombok.*;
/*
 digitalni entitet, odnosno podaci o domenu kom ce se izdavati sertifikat 
 ili koji ce izdavati sertifikat
 */

@Data
@Entity
@Table(name="digEntity")
@AllArgsConstructor
@NoArgsConstructor
public class DigEntity {
	
	@Id
	@Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable=false, unique=true)
	public String commonName;
	
	//privremeno
	@Column(nullable=false)
	public String entityType;
	
	/*
	@Enumerated
	public EntityType entityType;
	//zavisno kog je tipa entitet neka polja ce biti prazna...ali neka polja ce uvek biti popunjena poput commonname cn i sl
	*/
	@Column
	private String surName;
	
	@Column
	private String givenName;
	
	@Column
	private String organization;
	
	@Column
	private String orgUnitName;
	
	@Column
	private String countryCode;
	
	//   email address in Verisign certificates
	@Column
	private String email;

	
	
	
	
}
