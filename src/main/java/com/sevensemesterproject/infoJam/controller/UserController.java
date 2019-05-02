package com.sevensemesterproject.infoJam.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.request.UserEditRequest;
import com.sevensemesterproject.infoJam.request.UserRegisterRequest;
import com.sevensemesterproject.infoJam.response.LoginResponse;
import com.sevensemesterproject.infoJam.response.UserResponse;
import com.sevensemesterproject.infoJam.service.UserService;

import io.swagger.annotations.ApiOperation;

@Controller 
@RequestMapping("/api/user")
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody UserRegisterRequest request){
		LOG.debug("---->Requesting for register user.");
		User user = userService.registerUser(request);
		return new ResponseEntity<Object>("User Created "+ user.getId(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get a user API", notes = "on successful request the response is as:",
			response = UserResponse.class)
	public ResponseEntity<Object> getUser(@PathVariable Long id){
		UserResponse userResponse = userService.getUser(id);
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		responseMap.put("user", userResponse);
		return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	 }
	
	
	  @RequestMapping(method = RequestMethod.PUT) public ResponseEntity<Object>
	  editUser(@RequestBody UserEditRequest userEditRequest) {
	  LOG.debug("Request accepted to edit user.");
	  userService.editUser(userEditRequest); return new
	  ResponseEntity<Object>(HttpStatus.OK); }
	

}
