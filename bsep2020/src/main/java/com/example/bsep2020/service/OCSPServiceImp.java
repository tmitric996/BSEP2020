package com.example.bsep2020.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bsep2020.model.OCSPTable;
import com.example.bsep2020.repository.OCSPRepository;
//kada zelimda povucem neki sertifikat povlacim ga na osnovu serialnumbera


@Service
public class OCSPServiceImp implements OCSPService{

	@Autowired
	OCSPRepository ocspRepo;
	
	@Override
	public OCSPTable saveOCSP(OCSPTable ocspTable) {
		//mozda ce biti izmene da ovde odma pretrazuje listu sertifikata koji su potpsani od strane ovog i da onda i njihpovlaci
		return ocspRepo.save(ocspTable);
	}
}
