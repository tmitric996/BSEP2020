package com.example.bsep2020.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SubjectData {
	
	private Long ig;
	private X500Name x500name;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private String serialNumber;
	private Date startDate;
	private Date endDate;



	
	

}
