package com.sevensemesterproject.infoJam.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensemesterproject.infoJam.controller.UserController;
import com.sevensemesterproject.infoJam.exception.NotFoundException;
import com.sevensemesterproject.infoJam.model.Login;
import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.repository.LoginRepository;
import com.sevensemesterproject.infoJam.repository.UserRepository;
import com.sevensemesterproject.infoJam.request.LoginRequest;
import com.sevensemesterproject.infoJam.response.LoginResponse;
import com.sevensemesterproject.infoJam.response.UserResponse;
import com.sevensemesterproject.infoJam.util.LoginStatus;

@Service
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private LoginRepository loginRepository;
	
	@Autowired
	private UserService userService;
	
	public LoginResponse login(LoginRequest request) {
		LOG.debug("---->checking email");
		User user = userRepository.findByEmail(request.getEmail());
		if(user == null ||  user.equals(null))
			throw new NotFoundException("Email not found.");
		LOG.debug("---->Email found. user's id is "+ user.getId());
		
		LoginResponse loginResponse = new LoginResponse();
		
		Login login = loginRepository.findByUserId(user.getId());
		LOG.debug("---->Login Null?:"+ login);
		if(login != null) {
			if(request.getPassword().equals(login.getPassword())) {
				LOG.debug("---->Login Successfull!");
				login.setLoginStatus(LoginStatus.LOGGEDIN);
				login.setToken("new tokem dsfad5fd4sfsd5f48dsfsfs4gr8");
				login.setModifiedDate(new Date());
				login.setTokenExpDateTime(new Date());
				
				loginRepository.save(login);
				LOG.debug("---->Login updated");
				
				//setting response 
				loginResponse.setAddress(user.getAddress());
				loginResponse.setCreatedDate(user.getCreatedDate());
				loginResponse.setDob(user.getDob());
				loginResponse.setEmail(user.getEmail());
				loginResponse.setFirstName(user.getFirstName());
				loginResponse.setLastName(user.getLastName());
				loginResponse.setLoginId(login.getId());
				loginResponse.setPhone(user.getPhone());
				loginResponse.setStatus(user.getStatus());
				loginResponse.setUserId(user.getId());
				loginResponse.setUsername(user.getUsername());
				
			}else {
				LOG.debug("---->Login Failed!");
				throw new NotFoundException("Password didnot match!");
			}
		}
		return loginResponse;
	}

	public void logout(Long loginId) {
		LOG.debug("---->Loggin out.....");
		Login login = loginRepository.getOne(loginId);
		if(login == null )
			throw new NotFoundException("No login found.");
		login.setLoginStatus(LoginStatus.LOGGEDOUT);
		login.setToken("");
		login.setTokenExpDateTime(null);
		login.setModifiedDate(new Date());
		loginRepository.save(login);
		LOG.debug("Logout successfull.");
	}
}
