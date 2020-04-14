package com.example.bsep2020.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//ocsp sadrzi povucene sertifikate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ocsp")
public class OCSP {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "serialNumberOfCert")
	private String serialNumberOfCert; //serijski broj sertifikata koji je povucen
}
