package com.example.bsep2020.service;

import org.springframework.stereotype.Service;

import com.example.bsep2020.dto.KeyStoreDTO;
import com.example.bsep2020.pki.KeyStoreWriter;

@Service
public class KeyStoreServiceImp implements KeyStoreService{

	@Override
	public void createKeyStore(KeyStoreDTO skdto) {
		KeyStoreWriter ksw=new KeyStoreWriter();
		ksw.loadKeyStore(null, skdto.getPassword());
		ksw.saveKeyStore(skdto.getFileName(), skdto.getPassword());
		return;
	}

}
