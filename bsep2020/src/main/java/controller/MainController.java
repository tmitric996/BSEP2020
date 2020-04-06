package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import model.Admin;
import model.DigEntity;
import service.AdminService;
import service.DigEntityService;

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
