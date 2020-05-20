package com.example.bsep2020.controller;

import java.security.Security;
import java.security.cert.Certificate;

import javax.validation.Valid;

import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bsep2020.dto.CertificateDTO;
import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.model.IssuerData;
import com.example.bsep2020.model.SubjectData;
import com.example.bsep2020.service.CertificateService;
import com.example.bsep2020.service.KeyStoreServiceImp;

@RestController
@RequestMapping("/demo/cert") 
public class CertificateController {
	
	static {
	    Security.addProvider(new BouncyCastleProvider());
	}
	
	@Autowired
	CertificateService certService;
	
	@Autowired
	KeyStoreServiceImp keyStoreService;
	
	@PostMapping("/writess")
	public void writeSelfSignedCert(@Valid @RequestBody DigEntity digEntity ) throws Exception {
		certService.createSelfSignedCertificate(digEntity);
	}

	@PostMapping("/writeint")
	public void writeCACert(@Valid @RequestBody DigEntity digEntity ) throws Exception {
		//privremena metoda
		int SNIssuer = 1;
		boolean canIssueCA=false;
		certService.createCACert(digEntity, SNIssuer, canIssueCA);
	}
	
	@PostMapping("/writeee")
	public void writeCert(@Valid @RequestBody DigEntity digEntity ) throws Exception {
		//privremena metoda
		int SNIssuer = 2;
		certService.createCertificate(digEntity, SNIssuer);
	}
	
	@PostMapping("/readcert")
	public void readCertificate(@Valid @RequestBody String keyStoreFile, String keyStorePass, String alias) {
		keyStoreService.readCertificate(keyStoreFile, keyStorePass, alias);
	}
	
}
