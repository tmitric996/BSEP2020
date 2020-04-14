package com.example.bsep2020.model;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.asn1.x500.X500Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuerData {

	private Long id;
	private X500Name x500name;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public IssuerData(PrivateKey privatekey,X500Name x500name) {
		this.privateKey=privatekey;
		this.x500name=x500name;
	}
	
	
}
