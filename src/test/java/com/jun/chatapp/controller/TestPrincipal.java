package com.jun.chatapp.controller;

import java.security.Principal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class TestPrincipal implements Principal {
	private int id;
	private String name;

	public String getName() {
		return name;
	}
}
