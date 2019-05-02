package com.sevensemesterproject.infoJam.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	private static final Logger LOG = LoggerFactory.getLogger(CommonService.class);

	public String createUsername(String firstName, String lastName) {

		return firstName+"."+lastName;//need to make more relevant later by checking into DB
		
	}
}
