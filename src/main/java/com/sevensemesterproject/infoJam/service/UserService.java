package com.sevensemesterproject.infoJam.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensemesterproject.infoJam.exception.NotFoundException;
import com.sevensemesterproject.infoJam.model.Login;
import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.repository.LoginRepository;
import com.sevensemesterproject.infoJam.repository.UserRepository;
import com.sevensemesterproject.infoJam.request.UserEditRequest;
import com.sevensemesterproject.infoJam.request.UserRegisterRequest;
import com.sevensemesterproject.infoJam.response.UserResponse;
import com.sevensemesterproject.infoJam.util.LoginStatus;
import com.sevensemesterproject.infoJam.util.Status;

@Service
public class UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private CommonService commonService;

	public User registerUser(UserRegisterRequest request) {
		LOG.debug("---->Registering User.");
		
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPhone(request.getPhone());
		user.setAddress(request.getAddress());
		user.setEmail(request.getEmail());
		user.setDob(request.getDob());
		user.setUsername(commonService.createUsername(request.getFirstName(), request.getLastName()));
		user.setStatus(Status.ACTIVE);
		user.setCreatedDate(new Date());
		
		user = userRepository.save(user);
		
		LOG.debug("---->creating login for user "+ user.getId());
		createLoginForRegisteredUser(user, request.getPassword());
		
		LOG.debug("---->user "+ user.getId()+ " registered.");
		return user;	
	}
	
	@Transactional
	public void createLoginForRegisteredUser(User user, String password) {
		Login login = new Login();
		login.setUserId(user.getId());
		login.setPassword(password);//need encryption. to be done later
		login.setToken("sample token a44dfn757uhr435fw5we7ew5few4f8e4f");
		login.setTokenExpDateTime(new Date());
		login.setCreatedDate(new Date());
		login.setLoginStatus(LoginStatus.LOGGEDOUT);
		login.setStatus(Status.ACTIVE);
		
		loginRepository.save(login);
		LOG.debug("---->Login created for userID "+ user.getId()+ " with loginId "+login.getId());
	}
	
	@Transactional 
	public UserResponse getUser(Long id) {
		
		User user = userRepository.getOne(id);
		UserResponse userResponse = new UserResponse();
	
		userResponse.setCreatedDate(user.getCreatedDate());
		userResponse.setFirstName(user.getFirstName());
		userResponse.setLastName(user.getLastName());
		userResponse.setEmail(user.getEmail());
		userResponse.setUsername(user.getUsername());
		userResponse.setAddress(user.getAddress());
		userResponse.setDob(user.getDob());
		userResponse.setPhone(user.getPhone());
		userResponse.setStatus(user.getStatus());
		
		return userResponse;
	}

	
	 public User editUser(UserEditRequest userEditRequest) {
	  
	  User user = userRepository.getOne(userEditRequest.getId());
	  if(user == null) {
		  throw new NotFoundException("User not found related to this id."); 
		  }
	  
	  user.setFirstName(userEditRequest.getFirstName());
	  user.setLastName(userEditRequest.getLastName());
	  user.setPhone(userEditRequest.getPhone());
	  user.setAddress(userEditRequest.getAddress());
	  user.setDob(userEditRequest.getDob());
	  user.setUsername(userEditRequest.getUsername()); 
	  user.setModifiedDate(new Date());
	  
	 return user = userRepository.save(user);
	  
	  
	  }
	 
}
