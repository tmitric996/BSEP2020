package com.example.bsep2020.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bsep2020.model.CertificateData;
import com.example.bsep2020.model.OCSPTable;
import com.example.bsep2020.repository.OCSPRepository;
//kada zelimda povucem neki sertifikat povlacim ga na osnovu serialnumbera


@Service
public class OCSPServiceImp implements OCSPService{

	@Autowired
	OCSPRepository ocspRepo;
	
	@Autowired 
	CertificateDataServiceImp certDataService;
	
	@Override
	public OCSPTable saveOCSP(int sn) {
		//mozda ce biti izmene da ovde odma pretrazuje listu sertifikata koji su potpsani od strane ovog i da onda i njihpovlaci
		OCSPTable ocspTable = new OCSPTable();
		ocspTable.setSerialNumber(sn);
		CertificateData cd=certDataService.findBySN(sn);
		cd.setRevoken(true);
		certDataService.saveCertificateData(cd);
		ocspRepo.save(ocspTable);
		//nalazi certdatasa tim sn i stavlja da je revoken ++ radi
		//ucitava sertifikat iz jks, i revoken -- ovomozdase ne radi rucno? vec samopogledas ovde jel povucen i tjt
		
		//povlacisa sobom i lanac seertifikata... to prvo mora lanac da se proveri napravi ++ radi
		List<CertificateData> certificateDatas = certDataService.findAllCertData();
		for (CertificateData certs : certificateDatas)
		{
			if (certs.getIssuerName().toString().equals(cd.getSubjectName().toString())) {
				saveOCSP(certs.getSerialNumber());
			}
		}
		return ocspRepo.save(ocspTable);
	}
}
