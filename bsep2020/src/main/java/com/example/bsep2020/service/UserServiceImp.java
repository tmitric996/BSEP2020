package com.example.bsep2020.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bsep2020.model.Users;
import com.example.bsep2020.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void save(Users user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public Optional<Users> findByUsername(String username) {
		 return userRepository.findByUsername(username);
	}
	
	

}
