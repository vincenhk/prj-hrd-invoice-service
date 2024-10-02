package com.ad1.invoice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ad1.invoice.model.DTO.LoginDto;
import com.ad1.invoice.model.DTO.RegisterDto;

public interface AuthService {
	ResponseEntity<Object> register(RegisterDto registerDto);
	
	ResponseEntity<Object> registerAll(List<RegisterDto> registerDto);

	ResponseEntity<Object> login(LoginDto loginDto);
}
