package com.example.bsep2020.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bsep2020.model.CertificateData;
import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.model.IssuerData;
import com.example.bsep2020.model.SubjectData;
import com.example.bsep2020.pki.KeysGenerator;
import com.example.bsep2020.repository.DigEntityRepository;

@Service
public class SubjectIssuerDataServiceImp implements SubjectIssuerDataService{

	@Autowired
	CertificateDataServiceImp certDataService;
	
	@Override //ovo je samo generisanje za root... imacemo metodu getIssuerData koja e da dobavlja podatke
	public IssuerData generateIssuerData(DigEntity digentity) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, digentity.getCommonName());
	    builder.addRDN(BCStyle.SURNAME, digentity.getSurname());
	    builder.addRDN(BCStyle.GIVENNAME, digentity.getGivenName());
	    builder.addRDN(BCStyle.O, digentity.getOrganization());
	    builder.addRDN(BCStyle.OU, digentity.getOrganizationUnitName());
	    builder.addRDN(BCStyle.C, digentity.getCountryCode());
	    builder.addRDN(BCStyle.E, digentity.getEmail());
	    
	    KeyPair kp= generateKeyPair();
		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(kp.getPrivate(), kp.getPublic(), builder.build());
	}

	
	@Override
	public SubjectData generateSubjectData(DigEntity digentity, int sernum) { //morace da dobija podatek i o isssueruda bi mogao da se setuje datum vazenja
		// TODO Auto-generated method stub
		try {
			KeyPair keyPairSubject = generateKeyPair();
			
			//Datumi od kad do kad vazi sertifikat .....   treba namestiti datume mozda ih caki slati
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2020-11-3");
			Date endDate = iso8601Formater.parse("2022-12-31"); //namesti za datum trajanja da gacita od issuera i da ne moze biti duzi od toga
			
			//Serijski broj sertifikata
			
			//serijski brojtreba sam da se generise, i morabiti jedisntven zasvaki sertifikat
			//znaci neki final static da bude il nesto
			//morace nesto sto generise serialnumber
			
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, digentity.getCommonName());
		    builder.addRDN(BCStyle.SURNAME, digentity.getSurname());
		    builder.addRDN(BCStyle.GIVENNAME, digentity.getGivenName());
		    builder.addRDN(BCStyle.O, digentity.getOrganization());
		    builder.addRDN(BCStyle.OU, digentity.getOrganizationUnitName());
		    builder.addRDN(BCStyle.C, digentity.getCountryCode());
		    builder.addRDN(BCStyle.E, digentity.getEmail());
		    
		    return new SubjectData(keyPairSubject.getPublic(), keyPairSubject.getPrivate(), builder.build(), sernum, (java.util.Date) startDate, (java.util.Date) endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
