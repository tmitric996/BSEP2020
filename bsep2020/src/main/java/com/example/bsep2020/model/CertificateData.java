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

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@Entity
public class CertificateData {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int serialNumber;
	
	
	@Column(nullable=false, unique=true)
//	@NotNull(message = "Obavezno polje!")
	private String subjectName;
	
	@Column
//	@NotNull(message = "Obavezno polje!")
	private String issuerName;
	
	@Column(length = 2048)
	//@NotNull(message = "Obavezno polje!")
	private PublicKey pubKey;
	
	@Column
//	@NotNull(message = "Obavezno polje!")
	private java.util.Date fromDate;
	
	@Column
//	@NotNull(message = "Obavezno polje!")
	private java.util.Date toDate;
	
	@Column
	//@NotNull(message = "Obavezno polje!")
	private boolean isRoot;
	
	@Column
//	@NotNull(message = "Obavezno polje!")
	private boolean isCA;
	
	@Column
	//@NotNull(message = "Obavezno polje!")
	private boolean canIssueCA;
	
	@Column
//	@NotNull(message = "Obavezno polje!")
	private boolean isRevoken;
	
	
	
	public CertificateData() {
		super();
	}

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
	
	public CertificateData(int sn) {
		this.serialNumber=sn;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public PublicKey getPubKey() {
		return pubKey;
	}

	public void setPubKey(PublicKey pubKey) {
		this.pubKey = pubKey;
	}

	public java.util.Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(java.util.Date fromDate) {
		this.fromDate = fromDate;
	}

	public java.util.Date getToDate() {
		return toDate;
	}

	public void setToDate(java.util.Date toDate) {
		this.toDate = toDate;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isCA() {
		return isCA;
	}

	public void setCA(boolean isCA) {
		this.isCA = isCA;
	}

	public boolean isCanIssueCA() {
		return canIssueCA;
	}

	public void setCanIssueCA(boolean canIssueCA) {
		this.canIssueCA = canIssueCA;
	}

	public boolean isRevoken() {
		return isRevoken;
	}

	public void setRevoken(boolean isRevoken) {
		this.isRevoken = isRevoken;
	}
	
	
	
}
