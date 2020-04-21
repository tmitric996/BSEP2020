package com.example.bsep2020.dto;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.example.bsep2020.enumeration.CertificateRole;
import com.example.bsep2020.model.IssuerData;
import com.example.bsep2020.model.SubjectData;

import ch.qos.logback.core.status.Status;
import lombok.Data;

@Data
public class CertificateDTO {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String serialNumber;
	private CertificateRole certRole; //ssigned, intrem, endentyty
	private Status certStatus; //valid or revoken
	private IssuerData issuerData;
	private SubjectData subjectData;
	
	
}
