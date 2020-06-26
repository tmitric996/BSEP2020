package com.example.bsep2020.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bsep2020.model.CertificateData;
import com.example.bsep2020.service.CertificateDataServiceImp;

@RequestMapping("/demo/certdata")
@Controller
public class CertificateDataController {

	@Autowired
	CertificateDataServiceImp certDataService;
	//ova metodace se zaprao pozivati kad kreiram sertifikat,pa ovo se cuva u bazi a sertifikat u keystory
	@PostMapping
	public CertificateData addCertData(@Valid @RequestBody CertificateData certData) {
		System.out.println(certData.getSerialNumber());
		return certDataService.saveCertificateData(certData);
	}
	
	@GetMapping
	public List<CertificateData> getAllCertData(){
		return certDataService.findAllCertData();
	}
	
}
