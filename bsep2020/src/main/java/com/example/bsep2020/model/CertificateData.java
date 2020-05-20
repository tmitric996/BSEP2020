package com.example.bsep2020.model;

import java.security.PublicKey;
import java.sql.Blob;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CertificateData {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int serialNumber;
	
	
	@Column(nullable=false, unique=true)
	private String subjectName;
	
	@Column
	private String issuerName;
	
	@Column(length = 2048)
	private PublicKey pubKey;
	
	@Column
	private java.util.Date fromDate;
	
	@Column
	private java.util.Date toDate;
	
	@Column
	private boolean isRoot;
	
	@Column
	private boolean isCA;
	
	@Column
	private boolean canIssueCA;
	
	@Column
	private boolean isRevoken;
	
	public CertificateData(int sn, String sun, String isn, Date from, Date to, boolean root, boolean ca, boolean canca, boolean rev, PublicKey pk) {
		this.serialNumber=sn;
		this.subjectName=sun;
		this.issuerName=isn;
		this.fromDate=from;
		this.toDate=to;
		this.isRoot=root;
		this.isCA=ca;
		this.canIssueCA=canca;
		this.isRevoken=rev;
		this.pubKey=pk;
	}
}
