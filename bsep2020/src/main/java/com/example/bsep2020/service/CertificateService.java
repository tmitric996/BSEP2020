package com.example.bsep2020.service;

import java.text.ParseException;

import javax.validation.Valid;

import org.bouncycastle.cert.CertIOException;

import com.example.bsep2020.dto.CertificateDTO;
import com.example.bsep2020.model.DigEntity;
public interface CertificateService {


	//void createSelfSignedCertificate(DigEntity digEntity) throws CertIOException, ParseException;

	//void createCACert(DigEntity subjectEntity, int SNIssuer, boolean canIssueCA) throws CertIOException;

	//void createCertificate(DigEntity subjectEntity, int SNIssuer) throws CertIOException;

	void createCertificate(Long id, int SNIssuer) throws CertIOException;

	void createCACert(Long id, int SNIssuer, boolean canIssueCA) throws CertIOException;

	void createSelfSignedCertificate(Long id) throws CertIOException, ParseException;
}
