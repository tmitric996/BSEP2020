package com.example.bsep2020.service;

import java.util.Optional;

import com.example.bsep2020.model.Users;

public interface UserService {

    Optional<Users> findByUsername(String username);

	void save(Users user);
}
