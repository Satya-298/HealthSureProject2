package com.infinite.jsf.provider.model;

import java.util.Set;

public class Doctors {
	    private String doctorId;
	    private String doctorName;
	    private Gender gender;
	    private String qualification;
	    private String specialization;
	    private String licenseNo;
	    private String email;
	    private String phoneNumber;
	    private String address;
	    private DoctorType doctorType;  // STANDARD or ADHOC
	    private DoctorStatus doctorStatus;
	    
	    private Provider provider = new Provider();
	    
	    private Set<Doctors> doctors;

		public String getDoctorId() {
			return doctorId;
		}
		public void setDoctorId(String doctorId) {
			this.doctorId = doctorId;
		}
		public Set<Doctors> getDoctors() {
			return doctors;
		}
		public void setDoctors(Set<Doctors> doctors) {
			this.doctors = doctors;
		}
		public Provider getProvider() {
			return provider;
		}
		public void setProvider(Provider provider) {
			this.provider = provider;
		}
		public Provider getProviders() {
			return provider;
		}
		public void setProviders(Provider providers) {
			this.provider = providers;
		}
		public String getDoctorName() {
			return doctorName;
		}
		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}
		public Gender getGender() {
			return gender;
		}
		public void setGender(Gender gender) {
			this.gender = gender;
		}
		public String getQualification() {
			return qualification;
		}
		public void setQualification(String qualification) {
			this.qualification = qualification;
		}
		public String getSpecialization() {
			return specialization;
		}
		public void setSpecialization(String specialization) {
			this.specialization = specialization;
		}
		public String getLicenseNo() {
			return licenseNo;
		}
		public void setLicenseNo(String licenseNo) {
			this.licenseNo = licenseNo;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}

		public DoctorType getDoctorType() {
			return doctorType;
		}
		public void setDoctorType(DoctorType doctorType) {
			this.doctorType = doctorType;
		}
		public DoctorStatus getDoctorStatus() {
			return doctorStatus;
		}
		public void setDoctorStatus(DoctorStatus doctorStatus) {
			this.doctorStatus = doctorStatus;
		}
		public Doctors(String doctorId, Provider provider, String providerId, String doctorName, Gender gender,
				String qualification, String specialization, String licenseNo, String email,String phoneNumber, String address,
				 DoctorType doctorType, DoctorStatus doctorStatus) {
			super();
			this.doctorId = doctorId;
			this.provider = provider;
			this.doctorName = doctorName;
			this.gender = gender;
			this.qualification = qualification;
			this.specialization = specialization;
			this.licenseNo = licenseNo;
			this.email = email;
			this.phoneNumber = phoneNumber;
			this.address = address;
			this.doctorType = doctorType;
			this.doctorStatus = doctorStatus;
		}

		public Doctors() {
			 this.provider = new Provider();
			 this.doctorType = DoctorType.STANDARD;
			 this.doctorStatus = DoctorStatus.ACTIVE;
		}
		

}
