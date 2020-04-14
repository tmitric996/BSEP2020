package com.example.bsep2020.model;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.asn1.x500.X500Name;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuerData {

	private Long id;
	private X500Name x500name;
	private PrivateKey privateKey;
	private PublicKey publicKey;


	public IssuerData(PrivateKey privateKey, X500Name x500name) {
		this.x500name = x500name;
		this.privateKey = privateKey;
	}

	
	
}
