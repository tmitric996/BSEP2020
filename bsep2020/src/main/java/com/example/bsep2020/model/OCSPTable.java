package com.example.bsep2020.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class OCSPTable {

	@Id
	@Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String serialNumber;
	
}
