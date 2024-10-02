package com.ad1.invoice.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
	private String name;
	private String username;
	private String email;
	private String password;
	private String location;
	private String regional;
}
