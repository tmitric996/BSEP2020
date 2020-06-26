package com.example.bsep2020.controller;

import java.security.Security;
import java.security.cert.Certificate;

import javax.validation.Valid;

import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
	public void writeSelfSignedCert(@Valid @RequestBody Long id) throws Exception {
		certService.createSelfSignedCertificate(id);
	}

	@PostMapping("/writeint")
	public void writeCACert(@Valid @RequestBody String id, String SNIssuer) throws Exception {
		String []sp= id.split("\"");
		id=sp[1];
		SNIssuer=sp[3];
		String canIss=sp[5];
		boolean canIssueCA=false;
		System.out.println(canIss);
		if (canIss=="true") {canIssueCA=true;}
		certService.createCACert(Long.parseLong(id), Integer.parseInt(SNIssuer), canIssueCA);
	}
	
	@PostMapping("/writeee")
	public void writeCert(@Valid @RequestBody String id, String SNIssuer) throws Exception {
		//privremena metoda mozesposle izmeniti kada bude front da prima long i int
		String []sp= id.split("\"");
		id=sp[1];
		SNIssuer=sp[3];
		certService.createCertificate(Long.parseLong(id), Integer.parseInt(SNIssuer));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/secured")
	public String securedHello() {
		return "secured hello";
	}
	
	
	@PostMapping("/readcert")
	public void readCertificate(@Valid @RequestBody String keyStoreFile, String keyStorePass, String alias) {
		//unos da bude te tri stvari razmaknute razmakom
		String []sp= keyStoreFile.split("\"");
    	keyStoreFile=sp[1];
    	keyStorePass=sp[3];
    	alias=sp[5];
		keyStoreService.readCertificate(keyStoreFile, keyStorePass, alias);
	}
	
}
