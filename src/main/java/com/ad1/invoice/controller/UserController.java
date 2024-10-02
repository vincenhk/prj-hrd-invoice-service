package com.ad1.invoice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ad1.invoice.model.User;
import com.ad1.invoice.model.UserView;
import com.ad1.invoice.model.DTO.IdDeleteDto;
import com.ad1.invoice.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user/v1")
public class UserController {

	@Autowired
	UserService userService;

	// cari berdasarkan email
	@GetMapping("/get/{email}")
	public Optional<User> listEmail(@PathVariable String email) {
		return userService.listemail(email);
	}

	// cari berdasarkan username
	@GetMapping("/getby/{username}")
	public Optional<User> listUser(@PathVariable String username) {
		return userService.listuser(username);
	}

	// view all user
	@GetMapping("/getUser")
	public ResponseEntity<Object> getUserBranch(@RequestParam(required = false) String name) {
		if (name != null) {
			return userService.getUserByName(name);
		} else {
			return userService.getUserBranch();
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@PostMapping("/delete")
	public ResponseEntity<Object> deleteUser(@RequestBody IdDeleteDto id) {
		return userService.deleteUser(id);
	}

}
