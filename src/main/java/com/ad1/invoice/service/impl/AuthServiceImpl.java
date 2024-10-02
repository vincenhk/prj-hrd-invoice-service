package com.ad1.invoice.service.impl;

import com.ad1.invoice.model.ResponseMsg;
import com.ad1.invoice.model.Role;
import com.ad1.invoice.model.User;
import com.ad1.invoice.exception.BlogAPIException;
import com.ad1.invoice.model.DTO.LoginDto;
import com.ad1.invoice.model.DTO.RegisterDto;
import com.ad1.invoice.repository.RoleRepository;
import com.ad1.invoice.repository.UserRepository;
import com.ad1.invoice.service.AuthService;
import com.ad1.invoice.service.EntityService;

import lombok.extern.slf4j.Slf4j;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EntityService entityService;

	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ResponseEntity<Object> login(LoginDto loginDto) {
		ResponseMsg msg = new ResponseMsg();
		try {
			Optional<User> cekUsername = userRepository.findByUsername(loginDto.getUsernameOrEmail());
			Optional<User> cekEmail = userRepository.findByEmail(loginDto.getUsernameOrEmail());
			if (cekUsername.isPresent()) {
				boolean matched = BCrypt.checkpw(loginDto.getPassword(), cekUsername.get().getPassword());

				if (matched) {
					msg.setMessage("Berhasil login");
					return entityService.jsonResponse(HttpStatus.OK, msg);
				}
				msg.setMessage("Password salah");
				return entityService.jsonResponse(HttpStatus.CONFLICT, msg);
			}

			if (cekEmail.isPresent()) {
				boolean matched = BCrypt.checkpw(loginDto.getPassword(), cekEmail.get().getPassword());
				if (matched) {
					msg.setMessage("Berhasil login");
					return entityService.jsonResponse(HttpStatus.OK, msg);
				}
				msg.setMessage("Password salah");
				return entityService.jsonResponse(HttpStatus.CONFLICT, msg);
			}
		} catch (Exception e) {
			msg.setMessage("error, " + e.getMessage());
			return entityService.jsonResponse(HttpStatus.PRECONDITION_FAILED, "Gagal Proses");
		}
		return entityService.jsonResponse(HttpStatus.EXPECTATION_FAILED, "Gagal masuk kondisi");
	}

	@Override
	public ResponseEntity<Object> register(RegisterDto registerDto) {

		ResponseMsg msg = new ResponseMsg();
		try {
			// add check for username exists in database
			if (userRepository.existsByUsername(registerDto.getUsername())) {
				msg.setMessage("Username is already exists!");
				msg.setData(registerDto.getUsername());
				return entityService.jsonResponse(HttpStatus.BAD_REQUEST, msg);
			}
			// add check for email exists in database
			if (userRepository.existsByEmail(registerDto.getEmail())) {
				msg.setMessage("Email is already exists!");
				msg.setData(registerDto.getEmail());
				return entityService.jsonResponse(HttpStatus.BAD_REQUEST, msg);
			}

			String hashPass = BCrypt.hashpw(registerDto.getPassword(), BCrypt.gensalt());

			User user = new User();
			user.setName(registerDto.getName());
			user.setUsername(registerDto.getUsername());
			user.setEmail(registerDto.getEmail());
			user.setPassword(hashPass);
			user.setLocation(registerDto.getLocation());
			user.setRegional(registerDto.getRegional());

			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findByName("ROLE_USER").get();
			roles.add(userRole);
			user.setRoles(roles);

			userRepository.save(user);
			msg.setMessage("Berhasil Register");
			msg.setData(userRepository.save(user));
			return entityService.jsonResponse(HttpStatus.OK, msg);

		} catch (Exception e) {
			msg.setMessage("Gagal proses, " + e.getMessage());
			return entityService.jsonResponse(HttpStatus.BAD_REQUEST, msg);
		}
	}

	@Override
	public ResponseEntity<Object> registerAll(List<RegisterDto> registerDto) {
		ResponseMsg msg = new ResponseMsg();
		List<User> data = new ArrayList<>();
		try {
			for (RegisterDto usr : registerDto) {
				// add check for username exists in database
				if (userRepository.existsByUsername(usr.getUsername())) {
					String alUserName = usr.getEmail();
					msg.setMessage("Username is already exists!");
					msg.setData(alUserName);
					;
					return entityService.jsonResponse(HttpStatus.BAD_REQUEST, msg);
				}
				// add check for email exists in database
				if (userRepository.existsByEmail(usr.getEmail())) {
					String alEmail = usr.getEmail();
					msg.setMessage("Email is already exists!");
					msg.setData(alEmail);
					;
					return entityService.jsonResponse(HttpStatus.BAD_REQUEST, msg);
				}

				User user = new User();
				user.setName(usr.getName());
				user.setUsername(usr.getUsername());
				user.setEmail(usr.getEmail());
				user.setPassword(usr.getPassword());
				user.setLocation(usr.getLocation());
				user.setRegional(usr.getRegional());

				Set<Role> roles = new HashSet<>();
				Role userRole = roleRepository.findByName("ROLE_USER").get();
				roles.add(userRole);
				user.setRoles(roles);

				data.add(userRepository.save(user));
			}
			msg.setMessage("Berhasil Register All");
			msg.setData(data);
			return entityService.jsonResponse(HttpStatus.OK, msg);

		} catch (Exception e) {
			msg.setMessage("Gagal proses, " + e.getMessage());
			return entityService.jsonResponse(HttpStatus.BAD_REQUEST, msg);
		}
	}

}
