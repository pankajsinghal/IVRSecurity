package com.bng.core.bean;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.bng.core.validator.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "cpassword", message = "The password fields must match")
})
public class UserBean {
	
	private int id;
	
	@NotEmpty
	@Size(min=2, max=30) 
	private String userName;
	
	@Size(min=2, max=10) 
	private String password;
	private String role; 
	private String cpassword;
	private String country;
	private String[] operator;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String[] getOperator() {
		return operator;
	}
	public void setOperator(String[] operator) {
		this.operator = operator;
	}
	
	
}
