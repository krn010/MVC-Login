package com.karan.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karan.DAO.UserMapperImpl;
import com.karan.Model.Login;
import com.karan.Model.Register;

@Service
public class UserService {

	@Autowired
	UserMapperImpl mapperimpl;

	public void insertuser(Register user) {
		mapperimpl.insertUser(user);
	}

	public Register getUser(Login user) {
		Register reg = mapperimpl.getUser(user);

		return reg;
	}

	public String checkUser(String uname) {
		String name = mapperimpl.checkUser(uname);

		return name;
	}

	public String getEmail(String uname) {
		String email = mapperimpl.getEmail(uname);

		return email;
	}

	public String checkEmail(String email) {
		String email1 = mapperimpl.getEmail(email);

		return email1;
	}
	
	public void updatePass(String email, String pass) {

		mapperimpl.updatePass(email, pass);

	}
}