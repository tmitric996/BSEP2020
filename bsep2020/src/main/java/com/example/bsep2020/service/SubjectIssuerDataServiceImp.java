package com.example.bsep2020.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;

import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.model.IssuerData;
import com.example.bsep2020.model.SubjectData;
import com.example.bsep2020.pki.KeysGenerator;

@Service
public class SubjectIssuerDataServiceImp implements SubjectIssuerDataService{

	@Override
	public IssuerData generateIssuerData(DigEntity digentity) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, digentity.getCommonName());
	    builder.addRDN(BCStyle.SURNAME, digentity.getSurName());
	    builder.addRDN(BCStyle.GIVENNAME, digentity.getGivenName());
	    builder.addRDN(BCStyle.O, digentity.getOrganization());
	    builder.addRDN(BCStyle.OU, digentity.getOrgUnitName());
	    builder.addRDN(BCStyle.C, digentity.getCountryCode());
	    builder.addRDN(BCStyle.E, digentity.getEmail());
	    
	    KeyPair kp= generateKeyPair();
		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(kp.getPrivate(), kp.getPublic(), builder.build());
	}

	@Override
	public SubjectData generateSubjectData(DigEntity digentity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyPair generateKeyPair() {
        try {
			//Generator para kljuceva
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			//Za kreiranje kljuceva neophodno je definisati generator pseudoslucajnih brojeva
			//Ovaj generator mora biti bezbedan (nije jednostavno predvideti koje brojeve ce RNG generisati)
			//U ovom primeru se koristi generator zasnovan na SHA1 algoritmu, gde je SUN provajder
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			//inicijalizacija generatora, 2048 bitni kljuc
			keyGen.initialize(2048, random);

			//generise par kljuceva koji se sastoji od javnog i privatnog kljuca
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
}
