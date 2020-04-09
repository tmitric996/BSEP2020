package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Admin;
import repository.AdminRepository;

@Service
public class AdminServiceImp implements AdminService{

	@Autowired
	AdminRepository adminRepo;

	public Admin save(Admin admin) {
		return adminRepo.save(admin);
	}
	
	
}
