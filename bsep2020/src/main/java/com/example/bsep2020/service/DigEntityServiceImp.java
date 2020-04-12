package com.example.bsep2020.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.repository.DigEntityRepository;

@Service
public class DigEntityServiceImp implements DigEntityService {
	
	@Autowired
	DigEntityRepository digEntityRepo;

	@Override
	public DigEntity saveEntity(DigEntity digEntity) {
		return digEntityRepo.save(digEntity);
	}
	
	@Transactional(readOnly = true)
	public List<DigEntity> findAllEntities() {
		return digEntityRepo.findAll();
	}

	@Override
	public DigEntity findOneEntity(Long entId) {
		return digEntityRepo.findById(entId).orElse(null);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteEntity(DigEntity digEntity) {
	digEntityRepo.delete(digEntity);
		
		
	}


}
