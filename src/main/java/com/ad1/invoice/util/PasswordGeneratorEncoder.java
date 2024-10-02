package com.ad1.invoice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println("admin :" + passwordEncoder.encode("admin"));
		System.out.println("passtest :" + passwordEncoder.encode("080062023"));
	}
}
