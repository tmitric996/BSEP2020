package com.example.bsep2020.service;

import java.security.KeyPair;
import java.security.PrivateKey;

import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.model.IssuerData;
import com.example.bsep2020.model.SubjectData;

public interface SubjectIssuerDataService {

	IssuerData generateIssuerData(DigEntity digentity);
	

	KeyPair generateKeyPair();

	SubjectData generateSubjectData(DigEntity digentity, int sernum);
}
