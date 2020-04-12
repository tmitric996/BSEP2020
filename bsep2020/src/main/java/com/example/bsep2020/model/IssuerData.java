package com.example.bsep2020.model;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.asn1.x500.X500Name;

public class IssuerData {

	private Long id;
	private X500Name x500name;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public IssuerData() {
	}

	public IssuerData(Long id, X500Name x500name, PrivateKey privateKey, PublicKey publicKey) {
		this.id = id;
		this.x500name = x500name;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public X500Name getX500name() {
		return x500name;
	}

	public void setX500name(X500Name x500name) {
		this.x500name = x500name;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	
	
	
	
}
