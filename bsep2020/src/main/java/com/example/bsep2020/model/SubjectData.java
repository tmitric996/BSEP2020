package com.example.bsep2020.model;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.asn1.x500.X500Name;

public class SubjectData {
	
	private Long ig;
	private X500Name x500name;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public SubjectData() {
		super();
	}

	public SubjectData(Long ig, X500Name x500name, PrivateKey privateKey, PublicKey publicKey) {
		this.ig = ig;
		this.x500name = x500name;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public Long getIg() {
		return ig;
	}

	public void setIg(Long ig) {
		this.ig = ig;
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
