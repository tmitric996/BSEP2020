package com.example.bsep2020.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bsep2020.dto.KeyStoreDTO;
import com.example.bsep2020.service.KeyStoreService;

@RestController
@RequestMapping("/demo/keystore") 
public class KeyStoreController {
	
	@Autowired
	KeyStoreService ksService;
	
	@PostMapping("/cretekeystore")
	public void addKeyStore(@Valid @RequestBody KeyStoreDTO ksdto) {
		ksService.createKeyStore(ksdto);
		return;
	}
	

}
