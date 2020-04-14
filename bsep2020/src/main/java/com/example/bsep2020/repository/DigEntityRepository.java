package com.example.bsep2020.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bsep2020.model.DigEntity;


@Repository
public interface DigEntityRepository extends JpaRepository <DigEntity, Long>{

	@Query(value = "SELECT * FROM dig_entity AS d", nativeQuery = true)
	List<DigEntity> findAll();
		
	
}
