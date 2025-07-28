package com.infinite.jsf.provider.model;

import java.sql.Timestamp;
import java.util.Date;

public class ProviderOtp {
	 private int otpId;
	 private String email;
	    private String otpCode;
	    private Timestamp createdAt;
	    private Timestamp expiresAt;
	    private boolean isVerified;   
	    
		public int getOtpId() {
			return otpId;
		}
		public void setOtpId(int otpId) {
			this.otpId = otpId;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getOtpCode() {
			return otpCode;
		}
		public void setOtpCode(String otpCode) {
			this.otpCode = otpCode;
		}
		public Timestamp getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Timestamp now) {
			this.createdAt = now;
		}
		public Timestamp getExpiresAt() {
			return expiresAt;
		}
		public void setExpiresAt(Timestamp expiresAt) {
			this.expiresAt = expiresAt;
		}
		
		public boolean getIsVerified() {
			return isVerified;
		}
		public void setIsVerified(boolean isVerified) {
			this.isVerified = isVerified;
		}
		public ProviderOtp(int otpId, String email, String otpCode, Timestamp createdAt, Timestamp expiresAt,
				boolean isVerified) {
			super();
			this.otpId = otpId;
			this.otpCode = otpCode;
			this.createdAt = createdAt;
			this.expiresAt = expiresAt;
			this.isVerified = isVerified;
		}
		public ProviderOtp() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	    
	    
		
}