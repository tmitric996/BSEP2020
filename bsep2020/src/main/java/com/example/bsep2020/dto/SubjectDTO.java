package com.example.bsep2020.dto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Date;

import org.bouncycastle.asn1.x500.X500Name;

public class SubjectDTO {

	private X500Name x500name;
	private String SerialNumber;
	private Date EndDate;
	private Date StartDate;
}
