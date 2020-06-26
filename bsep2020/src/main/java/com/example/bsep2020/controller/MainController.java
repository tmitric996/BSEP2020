package com.example.bsep2020.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bsep2020.model.Admin;
import com.example.bsep2020.model.DigEntity;
import com.example.bsep2020.model.OCSPTable;
import com.example.bsep2020.model.Users;
import com.example.bsep2020.repository.UserRepository;
import com.example.bsep2020.service.AdminService;
import com.example.bsep2020.service.CustomUserDetailServiceImpl;
import com.example.bsep2020.service.DigEntityService;
import com.example.bsep2020.service.OCSPService;
import com.example.bsep2020.service.UserService;


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
	
	//SAVE, radi, proverila preko postman-a
	@PostMapping("/digitalentity")
	public DigEntity addDigEntity(@Valid @RequestBody DigEntity de) {
		return digEntityService.saveEntity(de);
	}
	
	//FINDALL, radi, proverila
	@GetMapping("/digitalentity")
	public List<DigEntity> getAll(){
		return digEntityService.findAllEntities();
	}
	
	//radi, proverila
	@DeleteMapping("/digitalentity/{id}")
	public ResponseEntity<DigEntity> deleteEntity(@PathVariable(value="id") Long digId){
		DigEntity de=digEntityService.findOneEntity(digId);
		if (de==null) {
			return ResponseEntity.notFound().build();
		}
		digEntityService.deleteEntity(de);
		return ResponseEntity.ok().build();
	}
	
	//radi, proverila
	@GetMapping("/digitalentity/{id}")
	public ResponseEntity<DigEntity> getOneEntity(@PathVariable(value="id") Long digId){
		DigEntity de=digEntityService.findOneEntity(digId);
		if (de==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(de);	
	}
	
	@Autowired
	OCSPService ocspService;
	
	@PostMapping("/newocsp")
	public OCSPTable addToOCSP(@Valid @RequestBody int sn) {
		return ocspService.saveOCSP(sn);
	}
	
	@Autowired
	UserService userService;
	
	@PostMapping("/newuser")
	public void addUser(@Valid @RequestBody Users user) {
		userService.save(user);
	}
	  
}
