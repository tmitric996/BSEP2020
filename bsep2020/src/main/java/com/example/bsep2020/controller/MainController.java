package com.example.bsep2020.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bsep2020.model.Admin;
import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.service.AdminService;
import com.example.bsep2020.service.DigEntityService;

@RestController
@RequestMapping("/demo") 
public class MainController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/admin")
	public Admin addAdmin(@Valid @RequestBody Admin a) {
		return adminService.save(a);
	}
	
	@Autowired
	private DigEntityService digEntityService;
	
	@PostMapping("/digitalentity")
	public DigEntity addDigEntity(@Valid @RequestBody DigEntity de) {
		return digEntityService.save(de);
	}
	
	
	  
}
