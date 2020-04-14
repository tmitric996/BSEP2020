package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.DigEntity;

public interface DigEntityRepository extends JpaRepository <DigEntity, Long>{

	
}
