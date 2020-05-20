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
	
	private X500Name x500name;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private int SerialNumber;
	private java.util.Date EndDate;
	private java.util.Date StartDate;
	
	public SubjectData(PublicKey publicKey, PrivateKey privkey, X500Name x500name, int SerialNumber, java.util.Date startDate2, java.util.Date endDate2) {
		this.EndDate=endDate2;
		this.privateKey=privkey;
		this.publicKey=publicKey;
		this.x500name=x500name;
		this.SerialNumber=SerialNumber;
		this.StartDate=startDate2;
	}
	

}
