package com.sevensemesterproject.infoJam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sevensemesterproject.infoJam.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	User findByEmail(String email);
	
    	
}
