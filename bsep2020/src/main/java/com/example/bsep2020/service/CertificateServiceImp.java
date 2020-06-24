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
	
	
	static String serialNumber;
	
	@Override
	public void createSelfSignedCertificate(Long id) throws CertIOException, ParseException {
		DigEntity digEntity = digEntityService.findOneEntity(id);
		System.out.println(digEntity.toString());
		System.out.println("nasao entitet");
		IssuerData issuerData = subjectIssuerDataService.generateIssuerData(digEntity);
		System.out.println("izgenersiao subjiss data");
		System.out.println(issuerData);
		CertificateData cd=new CertificateData();
		System.out.println(cd.getSerialNumber());
		X509Certificate certificate = generateSelfSignedCertificate(issuerData, cd.getSerialNumber());
		System.out.println(cd.getSerialNumber());
		//izgenerisali smo rootCertificat.... i postavili mu serialnumber=0 i end i start date
		System.out.println("napravio certifi");
		System.out.println(certificate);
		char password[]= {'a', 'd', 'm', 'i', 'n'}; //postavljamo password za keystore 
		KeyStoreDTO ksdto= new KeyStoreDTO();
		ksdto.setFileName(null);
		ksdto.setPassword(password);
		
		keyStoreService.loadKeyStore(ksdto);
		System.out.println(cd.getSerialNumber());
		ksdto.setFileName("selfsigned.jks");
		String alias="selfsigned"; //postavljamo alias za pristupanje Selfsignedsertifikatu, saljemo njegov pprivatni kljuc, i password za pristupanje keystoru, i sertifikat kojiupisujemo
		keyStoreService.write(alias, issuerData.getPrivateKey(), password, certificate);
		System.out.println("upisao ga u jks");
		System.out.println(cd.getSerialNumber());
		keyStoreService.saveKeyStore(ksdto);
		System.out.println("sacuvao jks");
		System.out.println(cd.getSerialNumber());
		
		//postavi ekstenzije... ali to idesu generateSElfSignedCertificate metodi!!!
		
		//zelimo u tabelu CertificateData da upisemo podatke o ovom sertifikatu
		System.out.println("radi do certificate data");
		String issuersubjectName="";
		issuersubjectName=issuerData.getX500name().toString();
		issuersubjectName=issuersubjectName.split(",")[0];
		issuersubjectName=issuersubjectName.split("=")[1];
		System.out.println(cd.getSerialNumber());
		cd.setCA(true);
		cd.setCanIssueCA(true);
		cd.setIssuerName(issuersubjectName);
		cd.setRevoken(false);
		cd.setRoot(true);
		cd.setSubjectName(issuersubjectName);//ovo mora da se ispise jer mu ne upise common name vec mu upisesve
		cd.setFromDate((java.util.Date) certificate.getNotBefore());
		cd.setToDate((java.util.Date) certificate.getNotAfter());
		System.out.println(issuerData.getPublicKey().toString());
		String modulspk[] = issuerData.getPublicKey().toString().split(":");
		String modulspk1[] = modulspk[1].split(" ");
		System.out.println(modulspk1[1].length());
		cd.setPubKey(issuerData.getPublicKey());
		System.out.println(cd.getSerialNumber());
		certDataService.saveCertificateData(cd);
		System.out.println(issuersubjectName  + cd.getSerialNumber());
		return;
		
		
	}
	
	
	@Override
	public void createCACert(Long id, int SNIssuer, boolean canIssueCA) throws CertIOException {
		// da se pozove generisanje za sertifikat, da se implementira i pozove get issuerdata da se kreira certificateData, mada to moze da se napravi metoda pa da se poziva, a moze se pozivati
		DigEntity subjectEntity = digEntityService.findOneEntity(id);
		//samo za ovaj slucaj kejstore ce biti selfsigned, ali mora ce postojati neka baza, evidencija gde ce se ovi podaci pamtit
		String keyStoreFile="selfsigned.jks";
		String alias="selfsigned"; //alias za ovaj sertifikat, tjsertifikatisuera je 
		char password[]= {'a', 'd', 'm', 'i', 'n'}; 
		//iz baze izvuce public key, jer on bi svakkao trebalo da je dostupan
		CertificateData cd=certDataService.findBySN(SNIssuer);
		System.out.println("prosao trazeenje isuera");
		System.out.println(cd.getPubKey());
		IssuerData idata=keyStoreService.readIssuerFromStore(keyStoreFile, alias, password, password);
		System.out.println("prosao citanje iss iz keystora");
		// isad imamo issuer data na osnovu ser broja kao... 
		//imamo entitet za koji treba da generisemo subjectData
		//napravis istancu certificateData da ti izgenerise sn
		//a ostala poljau certData naknadno popunis i sacuvas ga 
		CertificateData certData=new CertificateData();
		System.out.println("napravio istancu certdata");
		SubjectData sd=subjectIssuerDataService.generateSubjectData(subjectEntity, certData.getSerialNumber());

		System.out.println("prosao generisanje sujdata");
		sd.setSerialNumber(certData.getSerialNumber());
		X509Certificate cert= generateCACertificate(idata, sd, canIssueCA);
		
		KeyStoreDTO ksdto= new KeyStoreDTO();
		ksdto.setFileName("intermediate.jks");
		ksdto.setPassword(password);
		
		keyStoreService.loadKeyStore(ksdto);
		System.out.println("lodovo keystore");
		alias="intermediate"; 
		keyStoreService.write(alias, sd.getPrivateKey(), password, cert);
		keyStoreService.saveKeyStore(ksdto);
		
		//postavi ekstenzije... ali to idesu generateSElfSignedCertificate metodi!!!
		
		//zelimo u tabelu CertificateData da upisemo podatke o ovom sertifikatu
		System.out.println("radi do certificate data");
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
		System.out.println("prosao upis datuma");
		String modulspk[] = sd.getPublicKey().toString().split(":");
		String modulspk1[] = modulspk[1].split(" ");
		System.out.println(modulspk1[1]);
		certData.setPubKey(sd.getPublicKey());
		certDataService.saveCertificateData(certData);
		System.out.println(issuersubjectName);
		System.out.println(cert);
		return;
		
	}
	
	@Override
	public void createCertificate(Long id, int SNIssuer) throws CertIOException {
		DigEntity subjectEntity = digEntityService.findOneEntity(id);
		
			//samo za ovaj slucaj kejstore ce biti selfsigned, ali mora ce postojati neka baza, evidencija gde ce se ovi podaci pamtit
					String keyStoreFile="intermediate.jks";
					String alias="intermediate"; //alias za ovaj sertifikat, tjsertifikatisuera je 
					char password[]= {'a', 'd', 'm', 'i', 'n'}; 
					//iz baze izvuce public key, jer on bi svakkao trebalo da je dostupan
					CertificateData cd=certDataService.findBySN(SNIssuer);
					IssuerData idata=keyStoreService.readIssuerFromStore(keyStoreFile, alias, password, password);
					idata.setPublicKey(cd.getPubKey());
					CertificateData certData=new CertificateData();
					SubjectData sd=subjectIssuerDataService.generateSubjectData(subjectEntity, certData.getSerialNumber());

					sd.setSerialNumber(certData.getSerialNumber());
					X509Certificate cert= generateCertificate(idata, sd);
					
					KeyStoreDTO ksdto= new KeyStoreDTO();
					ksdto.setFileName("endentity.jks");
					ksdto.setPassword(password);
					
					keyStoreService.loadKeyStore(ksdto);
					alias="endentity"; 
					keyStoreService.write(alias, sd.getPrivateKey(), password, cert);
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
					System.out.println(sd.getPublicKey());
					System.out.println(idata.getPublicKey());
					
					certDataService.saveCertificateData(certData);
					
					return;
	
	}
	
	//ovdemisad izlazi eksepsn, i nece da napravi sertifikat
	public X509Certificate generateSelfSignedCertificate(IssuerData idata, int sn) throws ParseException, CertIOException {
		try {
			System.out.println("serisji broj "+sn);
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
			
			//dodaj ekstenzije
			//trebalo bi da su ovo dobre, nasla na https://www.javatips.net/api/org.bouncycastle.asn1.x509.basicconstraints
		    certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true)).addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign | KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			serialNumber="1";//nz posle svake eneracije uvecati za1
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
			
			
			//-- greska -- gore --
			
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
