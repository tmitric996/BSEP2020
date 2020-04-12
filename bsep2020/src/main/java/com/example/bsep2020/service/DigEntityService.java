package com.example.bsep2020.service;

import java.util.List;
import com.example.bsep2020.model.DigEntity;

public interface DigEntityService {

	public DigEntity saveEntity(DigEntity digEntity);
	public DigEntity findOneEntity(Long entId);
	public List<DigEntity> findAllEntities();
	public void deleteEntity(DigEntity digEntity);
	
	
}
