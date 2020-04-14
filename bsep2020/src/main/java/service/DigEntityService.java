package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.DigEntity;
import repository.DigEntityRepository;

@Service
public class DigEntityService {

	@Autowired
	DigEntityRepository digEntityRepository;

	public DigEntity save(DigEntity digEntity) {
		return digEntityRepository.save(digEntity);
	}
}
