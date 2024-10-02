package com.ad1.invoice.controller;

import com.ad1.invoice.model.DTO.LoginDto;
import com.ad1.invoice.model.DTO.RegisterDto;
import com.ad1.invoice.service.AuthService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// untuk login
	@CrossOrigin
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
		return authService.login(loginDto);
	}

	// untuk tambah user
	@CrossOrigin
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
		return authService.register(registerDto);
	}

	@PostMapping("/register/registerall")
	public ResponseEntity<Object> registerAll(@RequestBody List<RegisterDto> registerDto) {
		return authService.registerAll(registerDto);
	}
}
