package com.example.bsep2020.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bsep2020.model.CertificateData;
@Repository
public interface CertificateDataRepository extends JpaRepository <CertificateData, Integer>{

	@Query(value = "SELECT * FROM certificate_data AS cd", nativeQuery = true)
	List<CertificateData> findAll();
}
