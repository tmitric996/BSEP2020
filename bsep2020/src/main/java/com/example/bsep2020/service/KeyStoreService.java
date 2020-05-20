package com.example.bsep2020.service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import com.example.bsep2020.dto.KeyStoreDTO;
import com.example.bsep2020.model.IssuerData;

public interface KeyStoreService {

	void createKeyStore(KeyStoreDTO skdto);
	void saveKeyStore(KeyStoreDTO skdto);
	void loadKeyStore(KeyStoreDTO skdto);
	void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate);
	PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass);
	
	Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias);
	
	IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass);
}
