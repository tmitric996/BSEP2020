package com.example.bsep2020.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.repository.DigEntityRepository;

@Service
public class DigEntityService {

	@Autowired
	DigEntityRepository digEntityRepository;

	public DigEntity save(DigEntity digEntity) {
		return digEntityRepository.save(digEntity);
	}
}
