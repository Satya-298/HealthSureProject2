package com.infinite.ejb.recipient.model;

import java.util.Date;
import java.util.Set;

import com.infinite.ejb.provider.model.Appointment;

public class Recipient {

    private String hId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String userName;
    private Gender gender;
    private Date dob;
    private String address;
    private Date createdAt;
    private String password;
    private String email;
    private RecipientStatus status;
    private int loginAttempts;
    private Date lockedUntil;
    private Date lastLogin;
    private Date passwordUpdatedAt;
    
	public String gethId() {
		return hId;
	}
	public void sethId(String hId) {
		this.hId = hId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RecipientStatus getStatus() {
		return status;
	}
	public void setStatus(RecipientStatus status) {
		this.status = status;
	}
	public int getLoginAttempts() {
		return loginAttempts;
	}
	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}
	public Date getLockedUntil() {
		return lockedUntil;
	}
	public void setLockedUntil(Date lockedUntil) {
		this.lockedUntil = lockedUntil;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Date getPasswordUpdatedAt() {
		return passwordUpdatedAt;
	}
	public void setPasswordUpdatedAt(Date passwordUpdatedAt) {
		this.passwordUpdatedAt = passwordUpdatedAt;
	}
	
	public Recipient(String hId, String firstName, String lastName, String mobile, String userName, Gender gender,
			Date dob, String address, Date createdAt, String password, String email, RecipientStatus status,
			int loginAttempts, Date lockedUntil, Date lastLogin, Date passwordUpdatedAt) {
		this.hId = hId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.userName = userName;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.createdAt = createdAt;
		this.password = password;
		this.email = email;
		this.status = status;
		this.loginAttempts = loginAttempts;
		this.lockedUntil = lockedUntil;
		this.lastLogin = lastLogin;
		this.passwordUpdatedAt = passwordUpdatedAt;
	}
	public Recipient() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	

}
