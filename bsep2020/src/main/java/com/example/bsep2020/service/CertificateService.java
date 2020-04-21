package com.example.bsep2020.service;

import org.bouncycastle.cert.CertIOException;

import com.example.bsep2020.dto.CertificateDTO;
import com.example.bsep2020.model.DigEntity;
public interface CertificateService {

	void createrootCert(DigEntity digEntity) throws CertIOException, Exception;
}
