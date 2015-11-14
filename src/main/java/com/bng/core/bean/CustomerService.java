package com.bng.core.bean;

import org.springframework.beans.factory.annotation.Autowired;

import com.bng.core.entity.Users;
import com.bng.core.service.UserService;

public class CustomerService {
	
	@Autowired
	private UserService userService;
	private int id;
	private String username;
	private String password;
	private String role; 
	private String cpassword;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void init() throws Exception {
		  System.out.println("--inside init method:-- ");
		  System.out.println("id"+ id);
		  System.out.println("username"+ username);
		  UserBean nuser = new UserBean();
		  nuser.setId(id);
		  nuser.setUserName(username);
		  nuser.setPassword(password);
		  nuser.setCpassword(cpassword);
		  nuser.setRole(role);
		  System.out.println("id from nuser"+ nuser.getId());
		  System.out.println("username from nuser"+ nuser.getUserName());
		  Users user=userService.addUser(nuser);
		  System.out.println("user data username"+ user.getUsername());
		  System.out.println("user data password"+ user.getPassword());
		  System.out.println("user data roles"+ user.getStatus());
	}
	 
	public void cleanUp() throws Exception {
	  System.out.println("Spring Container is destroy! Customer clean up");
	  
	  		
	  }

}
