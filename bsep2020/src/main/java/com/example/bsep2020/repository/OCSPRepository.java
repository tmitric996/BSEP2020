package com.example.bsep2020.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.bsep2020.model.OCSPTable;

@Repository
public interface OCSPRepository extends JpaRepository<OCSPTable, Long>{

}
