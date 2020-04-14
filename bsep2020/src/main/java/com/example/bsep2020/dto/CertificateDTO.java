package com.example.bsep2020.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
public class CertificateDTO {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String serialNumber;
	
	
}
