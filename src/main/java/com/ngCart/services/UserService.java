package com.ngCart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngCart.dao.UserDao;
import com.ngCart.models.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;

	public String registerUser(User user){
		return userDao.registerUser(user);
	}
	
	public User login(User user){
		return userDao.login(user);
	}
}
