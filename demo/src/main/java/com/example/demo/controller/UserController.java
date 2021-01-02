package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<User> get() {
		return userRepository.findAll();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<String> save(@Valid @RequestBody User user, BindingResult errors) {
		
		if(errors.hasErrors()) {
			List<String> errosArray = new ArrayList<>();
			
			 for(ObjectError error: errors.getAllErrors()){
				errosArray.add(error.getDefaultMessage().toString());
			};
			
			return ResponseEntity.badRequest().body(errosArray.toString());
		}
		
		try {
			userRepository.save(user);
			return new ResponseEntity<String>(HttpStatus.CREATED);
			
		} catch(DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().body("CPF or email already registered");
		}
		
		
		
	}
}
