package com.karan.DAO;

import com.karan.Model.Login;
import com.karan.Model.Register;

public interface UserMapper { 

	public void insertUser(Register user);

	public Register getUser(Login user);
	
	public String checkUser(String uname);
	
	public String getEmail(String uname);
	
	public String checkEmail(String email);
	
	public void updatePass(String email,String pass);
}