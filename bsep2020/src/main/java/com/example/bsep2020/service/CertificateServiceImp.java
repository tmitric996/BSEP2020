package com.example.bsep2020.service;



import java.math.BigInteger;
import java.security.KeyStore;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.bsep2020.dto.CertificateDTO;
import com.example.bsep2020.dto.KeyStoreDTO;
import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.model.IssuerData;
import com.example.bsep2020.pki.CertificateGenerator;
import com.example.bsep2020.pki.KeyStoreWriter;
import com.example.bsep2020.repository.DigEntityRepository;

@Service
public class CertificateServiceImp implements CertificateService{

	@Autowired
	SubjectIssuerDataService subjectIssuerDataService;
	
	@Autowired
	KeyStoreServiceImp keyStoreService;
	
	
	@Override
	public void createrootCert(DigEntity digEntity) throws CertIOException, Exception {
		
		
		IssuerData issuerData = subjectIssuerDataService.generateIssuerData(digEntity);
		X509Certificate certificate= generateRootCertificate(issuerData);
		char password[]= {'a', 'd', 'm', 'i', 'n'};
		KeyStoreDTO ksdto= new KeyStoreDTO();
		ksdto.setFileName("selfsigned.jks");
		ksdto.setPassword(password);
		keyStoreService.loadKeyStore(ksdto);// alias selfsigned
		keyStoreService.write("selfsigned", issuerData.getPrivateKey(), password, certificate);
		keyStoreService.saveKeyStore(ksdto);
		return;
	}
	
	public X509Certificate generateRootCertificate(IssuerData idata) throws CertIOException, ParseException {
		try {
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(idata.getPrivateKey());
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2020-3-31");
			Date endDate = iso8601Formater.parse("2030-12-31");
			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(idata.getX500name(),
					new BigInteger("0"),
					startDate,
					endDate,
					idata.getX500name(),
					idata.getPublicKey());
			//Generise se sertifikat
			certGen.addExtension(Extension.basicConstraints,true, new BasicConstraints(true));
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}

	


}
