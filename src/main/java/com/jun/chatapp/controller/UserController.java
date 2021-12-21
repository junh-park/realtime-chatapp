package com.jun.chatapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jun.chatapp.UserStorage;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UserController {

	@GetMapping("/registration/{userName}")
	public ResponseEntity<Void> register(@PathVariable String userName) {
		log.info("handling registering user request: " + userName);
		try {
			UserStorage.getInstance().setUser(userName);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}
}
