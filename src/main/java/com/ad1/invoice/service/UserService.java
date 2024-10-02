package com.ad1.invoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ad1.invoice.model.ResponseMsg;
import com.ad1.invoice.model.User;
import com.ad1.invoice.model.UserView;
import com.ad1.invoice.model.DTO.IdDeleteDto;
import com.ad1.invoice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;
	@Autowired
	EntityService entityService;

	public Optional<User> listemail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> listuser(String username) {
		return userRepository.findByUsername(username);
	}

	public ResponseEntity<Object> deleteUser(IdDeleteDto id) {
		ResponseMsg msg = new ResponseMsg();
		Optional<User> delUser = userRepository.findById(id.getId());
		if (delUser.isPresent()) {
			User user = delUser.get();
			msg.setMessage("Berhasil delete");
			msg.setData(user);
			userRepository.delete(user);
			return entityService.jsonResponse(HttpStatus.OK, msg);
		}else {
			msg.setMessage("ID tidak tersedia");
			msg.setData(id);
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, msg);
		}

	}

	public ResponseEntity<Object> getUserBranch() {
		List<UserView> responseResult = new ArrayList<UserView>();
		List<Object[]> userBranch = userRepository.getUser();

		userBranch.forEach(item -> {
			UserView temp = new UserView();
			temp.setId(item[0] + "");
			temp.setRegional(item[1] + "");
			temp.setLocation(item[2] + "");
			temp.setName(item[3] + "");
			responseResult.add(temp);
		});

		if (responseResult.isEmpty()) {
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Data : " + "tidak ditemukan");
		}
		return entityService.jsonResponse(HttpStatus.OK, responseResult);
	}

	public ResponseEntity<Object> getUserByName(String name) {
		List<UserView> responseResult = new ArrayList<UserView>();
		List<Object[]> userBranch = userRepository.getUserByName(name);

		userBranch.forEach(item -> {
			UserView temp = new UserView();
			temp.setId(item[0] + "");
			temp.setRegional(item[1] + "");
			temp.setLocation(item[2] + "");
			temp.setName(item[3] + "");
			responseResult.add(temp);
		});

		if (responseResult.isEmpty()) {
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Data : " + "tidak ditemukan");
		}
		return entityService.jsonResponse(HttpStatus.OK, responseResult);
	}

	public ResponseEntity<Object> updateUser(User user) {
		ResponseMsg msg = new ResponseMsg();
		try {
			User existsUser = userRepository.findById(user.getId()).orElse(null);
			if(existsUser == null) {
				msg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.OK, msg);
			}
			
			existsUser.setEmail(user.getEmail());
			existsUser.setName(user.getName());
			existsUser.setUsername(user.getUsername());
			existsUser.setLocation(user.getLocation());
			existsUser.setRegional(user.getRegional());
			existsUser.setPassword(passwordEncoder.encode(user.getPassword()));

			msg.setMessage("Berhasil edit");
			msg.setData(userRepository.save(existsUser));
			return entityService.jsonResponse(HttpStatus.OK, msg);
		} catch (Exception e) {
			msg.setMessage("Gagal Edit");
			msg.setData(userRepository.save(user));
			return entityService.jsonResponse(HttpStatus.BAD_REQUEST, msg);
		}
	}
	
	
}
