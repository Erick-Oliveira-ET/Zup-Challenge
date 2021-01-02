package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public User save(User user) {
		try {
			return userRepository.save(user);
			
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.toString());
		}
	}

}
