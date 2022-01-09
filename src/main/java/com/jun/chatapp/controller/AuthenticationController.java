package com.jun.chatapp.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
	
	@GetMapping("/")
	public String indexPage() {
		return "index.html";
	}
	
	@GetMapping("/greeting")
	public String greeting(@AuthenticationPrincipal(expression = "username") String username) {
		return "index.html";
	}

//	@GetMapping("/login")
//	public String logIn() {
//		return "login";
//	}
	
//	@PostMapping("/login")
//	public String login(@AuthenticationPrincipal Principal principal) {
//		System.out.println(principal.getName());
//		return "login.html";
//	}

	@GetMapping("/chat")
	public String chat() {
		return "chat";
	}
	
}
