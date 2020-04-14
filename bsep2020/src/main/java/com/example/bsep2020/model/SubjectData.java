package com.example.bsep2020.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Date;

import org.bouncycastle.asn1.x500.X500Name;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectData {
	
	private Long ig;
	private X500Name x500name;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private String SerialNumber;
	private Date EndDate;
	private Date StartDate;
	
	
	

}
