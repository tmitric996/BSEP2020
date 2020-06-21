package com.example.bsep2020.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.repository.DigEntityRepository;
import com.example.bsep2020.security.DigEntityValidator;

@Service
public class DigEntityServiceImp implements DigEntityService {

	@Autowired
	 DigEntityRepository digEntityRepo;

	@Override
	public DigEntity saveEntity(DigEntity digEntity) {
		if (DigEntityValidator.isStringOnlyAlphabet(digEntity.getCommonName()) &&
				DigEntityValidator.isStringOnlyAlphabet(digEntity.getSurname()) &&
				DigEntityValidator.isStringOnlyAlphabet(digEntity.getGivenName()) &&
				DigEntityValidator.isStringOnlyAlphabet(digEntity.getOrganization()) &&
				DigEntityValidator.isStringOnlyAlphabet(digEntity.getOrganizationUnitName())) {
			
			return digEntityRepo.save(digEntity);
		} else {
			throw new IllegalArgumentException("Lose popunjena polja!");
		}
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
