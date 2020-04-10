package com.example.bsep2020.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bsep2020.model.Admin;
import com.example.bsep2020.repository.AdminRepository;

@Service
public class AdminServiceImp implements AdminService{

	@Autowired
	AdminRepository adminRepo;

	@Override
	public Admin save(Admin admin) {
		
		return adminRepo.save(admin);
	}
	
	
}
