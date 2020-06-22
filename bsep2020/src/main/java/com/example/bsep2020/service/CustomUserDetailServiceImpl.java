package com.example.bsep2020.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bsep2020.model.CustomUserDetail;
import com.example.bsep2020.model.Users;
import com.example.bsep2020.repository.UserRepository;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> optionalUsers = userRepository.findByUsername(username);
		System.out.println("dosao do loaduserbyusername");
		optionalUsers.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return optionalUsers.map(CustomUserDetail::new).get();
	}
	
}
