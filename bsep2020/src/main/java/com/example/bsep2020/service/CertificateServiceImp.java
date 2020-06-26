package com.example.bsep2020.service;



import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
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
import com.example.bsep2020.model.CertificateData;
import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.model.IssuerData;
import com.example.bsep2020.model.SubjectData;
import com.example.bsep2020.pki.CertificateGenerator;
import com.example.bsep2020.pki.KeyStoreWriter;
import com.example.bsep2020.repository.DigEntityRepository;

@Service
public class CertificateServiceImp implements CertificateService{

	@Autowired
	SubjectIssuerDataService subjectIssuerDataService;
	
	@Autowired
	KeyStoreServiceImp keyStoreService;
	
	@Autowired
	DigEntityServiceImp digEntityService;
	
	@Autowired
	CertificateDataServiceImp certDataService;
	
	@Override
	public void createSelfSignedCertificate(Long id) throws CertIOException, ParseException {
		DigEntity digEntity = digEntityService.findOneEntity(id);
		
		IssuerData issuerData = subjectIssuerDataService.generateIssuerData(digEntity);
		
		CertificateData cd=new CertificateData(id.intValue());
		
		X509Certificate certificate = generateSelfSignedCertificate(issuerData, cd.getSerialNumber());
		
		char password[]= {'a', 'd', 'm', 'i', 'n'}; //postavljamo password za keystore 
		KeyStoreDTO ksdto= new KeyStoreDTO();
		ksdto.setFileName(null);
		ksdto.setPassword(password);
		
		keyStoreService.loadKeyStore(ksdto);
		
		ksdto.setFileName("selfsigned.jks");
		String alias="selfsigned"+id.toString(); //postavljamo alias za pristupanje Selfsignedsertifikatu, saljemo njegov pprivatni kljuc, i password za pristupanje keystoru, i sertifikat kojiupisujemo
		keyStoreService.write(alias, issuerData.getPrivateKey(), password, certificate);
		keyStoreService.saveKeyStore(ksdto);
		
		//zelimo u tabelu CertificateData da upisemo podatke o ovom sertifikatu
		String issuersubjectName="";
		issuersubjectName=issuerData.getX500name().toString();
		issuersubjectName=issuersubjectName.split(",")[0];
		issuersubjectName=issuersubjectName.split("=")[1];
		cd.setCA(true);
		cd.setCanIssueCA(true);
		cd.setIssuerName(issuersubjectName);
		cd.setRevoken(false);
		cd.setRoot(true);
		cd.setSubjectName(issuersubjectName);//ovo mora da se ispise jer mu ne upise common name vec mu upisesve
		cd.setFromDate((java.util.Date) certificate.getNotBefore());
		cd.setToDate((java.util.Date) certificate.getNotAfter());
		cd.setPubKey(issuerData.getPublicKey());
		
		certDataService.saveCertificateData(cd);
		System.out.println(certificate);
		return;
		
		
	}
		
	@Override
	public void createCACert(Long id, int SNIssuer, boolean canIssueCA) throws CertIOException {
		
		DigEntity subjectEntity = digEntityService.findOneEntity(id);
		CertificateData certD = certDataService.findBySN(SNIssuer);
		String keyStoreFile="";
		String alias="";
		if (certD.isRoot()) {
			keyStoreFile="selfsigned.jks";
			alias="selfsigned"+SNIssuer;
		}
		else {
			keyStoreFile="intermediate.jks";
			alias="intermediate"+SNIssuer;
		}
		char password[]= {'a', 'd', 'm', 'i', 'n'}; 

		IssuerData idata=keyStoreService.readIssuerFromStore(keyStoreFile, alias, password, password);
		 
		CertificateData certData=new CertificateData(id.intValue());		
		SubjectData sd=subjectIssuerDataService.generateSubjectData(subjectEntity, certData.getSerialNumber());
		sd.setSerialNumber(certData.getSerialNumber());
		X509Certificate cert= generateCACertificate(idata, sd, canIssueCA);
		
		KeyStoreDTO ksdto= new KeyStoreDTO();
		//try kec za lodovanje jks
		ksdto.setFileName("intermediate.jks");
		ksdto.setPassword(password);
		
		keyStoreService.loadKeyStore(ksdto);
		alias="intermediate"+id.toString(); 
		keyStoreService.write(alias, sd.getPrivateKey(), password, cert);
		//ksdto.setFileName("intermediate.jks"); 
		keyStoreService.saveKeyStore(ksdto);
		
		String issuersubjectName="";
		issuersubjectName=idata.getX500name().toString();
		issuersubjectName=issuersubjectName.split(",")[0];
		issuersubjectName=issuersubjectName.split("=")[1];
		certData.setCA(true);
		certData.setCanIssueCA(true);
		certData.setIssuerName(issuersubjectName);
		certData.setRevoken(false);
		certData.setRoot(false);
		issuersubjectName=sd.getX500name().toString();
		issuersubjectName=issuersubjectName.split(",")[0];
		issuersubjectName=issuersubjectName.split("=")[1];
		certData.setSubjectName(issuersubjectName);//ovo mora da se ispise jer mu ne upise common name vec mu upisesve
		certData.setFromDate((java.util.Date) cert.getNotBefore());
		certData.setToDate((java.util.Date) cert.getNotAfter());
		certData.setSerialNumber(id.intValue());		
		certData.setPubKey(sd.getPublicKey());
		
		certDataService.saveCertificateData(certData);
		System.out.println(cert);
		return;
		
	}
	
	@Override
	public void createCertificate(Long id, int SNIssuer) throws CertIOException {
		
		DigEntity subjectEntity = digEntityService.findOneEntity(id);
		CertificateData certD = certDataService.findBySN(SNIssuer);
		String keyStoreFile="";
		String alias="";
		if (certD.isRoot()) {
			keyStoreFile="selfsigned.jks";
			alias="selfsigned"+SNIssuer;
		}
		else {
			keyStoreFile="intermediate.jks";
			alias="intermediate"+SNIssuer;
		}

			char password[]= {'a', 'd', 'm', 'i', 'n'}; 
			CertificateData cd=certDataService.findBySN(SNIssuer);
			IssuerData idata=keyStoreService.readIssuerFromStore(keyStoreFile, alias, password, password);
			idata.setPublicKey(cd.getPubKey());
			CertificateData certData=new CertificateData(id.intValue());
					
			SubjectData sd=subjectIssuerDataService.generateSubjectData(subjectEntity, certData.getSerialNumber());
			sd.setSerialNumber(certData.getSerialNumber());
			X509Certificate cert= generateCertificate(idata, sd);
				
			KeyStoreDTO ksdto= new KeyStoreDTO();
			ksdto.setFileName("endentity.jks");
			ksdto.setPassword(password);
					
			keyStoreService.loadKeyStore(ksdto);
			alias="endentity"+id.toString(); 
			keyStoreService.write(alias, sd.getPrivateKey(), password, cert);
			//ksdto.setFileName("endentity.jks");
			keyStoreService.saveKeyStore(ksdto);
					
			String issuersubjectName="";
			issuersubjectName=idata.getX500name().toString();
			issuersubjectName=issuersubjectName.split(",")[0];
			issuersubjectName=issuersubjectName.split("=")[1];
			certData.setCA(false);
			certData.setCanIssueCA(false);
			certData.setIssuerName(issuersubjectName);
			certData.setRevoken(false);
			certData.setRoot(false);
			issuersubjectName=sd.getX500name().toString();
			issuersubjectName=issuersubjectName.split(",")[0];
			issuersubjectName=issuersubjectName.split("=")[1];
			certData.setSubjectName(issuersubjectName);//ovo mora da se ispise jer mu ne upise common name vec mu upisesve
			certData.setFromDate((java.util.Date) cert.getNotBefore());
			certData.setToDate((java.util.Date) cert.getNotAfter());
			certData.setPubKey(sd.getPublicKey());

			certDataService.saveCertificateData(certData);
			System.out.println(cert);
			return;
	
	}
	
	public X509Certificate generateSelfSignedCertificate(IssuerData idata, int sn) throws ParseException, CertIOException {
		try {
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(idata.getPrivateKey());
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2020-3-31");
			Date endDate = iso8601Formater.parse("2030-12-31");
			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(idata.getX500name(),
					new BigInteger(Integer.toString(sn)),
					startDate,
					endDate,
					idata.getX500name(),
					idata.getPublicKey());
			
			//dodaj ekstenzije //trebalo bi da su ovo dobre, nasla na https://www.javatips.net/api/org.bouncycastle.asn1.x509.basicconstraints
		    certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true)).addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign | KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
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
	
	public X509Certificate generateCACertificate(IssuerData idata, SubjectData sdata, boolean canIssueCA) throws CertIOException {//negde drugde da se vrsi provera issuera da bude odgovarajuci... tipa da imas findall, ili findthatcanCA i sl
		try {
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			builder = builder.setProvider("BC");
			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(idata.getPrivateKey());
			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(idata.getX500name(),
					new BigInteger(Integer.toString(sdata.getSerialNumber())),
					sdata.getStartDate(),
					sdata.getEndDate(),
					sdata.getX500name(),
					sdata.getPublicKey());
			//Generise se sertifikat
			
			certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
			certGen.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyEncipherment | KeyUsage.cRLSign | KeyUsage.dataEncipherment | KeyUsage.digitalSignature | KeyUsage.keyCertSign));
			//certGen.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.anyExtendedKeyUsage));
			
			
			X509CertificateHolder certHolder = certGen.build(contentSigner);
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
	
	public X509Certificate generateCertificate(IssuerData idata, SubjectData sdata) throws CertIOException {
		try {
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			builder = builder.setProvider("BC");
			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(idata.getPrivateKey());
			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(idata.getX500name(),
					new BigInteger(Integer.toString(sdata.getSerialNumber())),
					sdata.getStartDate(),
					sdata.getEndDate(),
					sdata.getX500name(),
					sdata.getPublicKey());
			//Generise se sertifikat

		X509CertificateHolder certHolder = certGen.build(contentSigner);
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
