package com.example.bsep2020.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bsep2020.model.CertificateData;
import com.example.bsep2020.repository.CertificateDataRepository;
import com.example.bsep2020.security.CertificateDataValidator;

@Service
public class CertificateDataServiceImp implements CertificateDataService {

	@Autowired
	CertificateDataRepository certDataRepository;
	
	@Override
	public CertificateData saveCertificateData(CertificateData certData) {
		if(CertificateDataValidator.isStringOnlyAlphabet(certData.getIssuerName()) &&
		   CertificateDataValidator.isStringOnlyAlphabet(certData.getSubjectName())) {
			return certDataRepository.save(certData);
		}else {
			throw new IllegalArgumentException("Lose popunjena polja!");
		}
		
	}
	
	@Transactional(readOnly = true)
	public List<CertificateData> findAllCertData() {
		return certDataRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public CertificateData findBySN(int sn) {
		return certDataRepository.getOne(sn);
	}
	
	
}
