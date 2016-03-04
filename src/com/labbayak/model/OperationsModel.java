package com.labbayak.model;

public class OperationsModel {
	
	public String id;
	public String name;
	public String email;
	public String photo;
	
	public String passport;
	public String mobile;
	public String gender;
	public String nationality;
	
	public String country;
	public String state;
	public String city;
	public String volunteer;
	
	public String type;
	public String profession;
	public String data;
	public String created_at;
	
	public String updated_at;
	
	public OperationsModel() {
		// TODO Auto-generated constructor stub
	}
	
	public OperationsModel(String id,String name, String email,String photo,String passport,String mobile,
		String gender,String nationality,String country,String state,String city,String volunteer,String type,
		String profession,String data,String created_at,String updated_at) {
	// TODO Auto-generated constructor stub
	
	this.id=id;
	this.name=name;
	this.email=email;
	this.photo=photo;
	
	this.passport=passport;
	this.mobile=mobile;
	this.gender=gender;
	this.nationality=nationality;
	
	this.country=country;
	this.state=state;
	this.city=city;
	this.volunteer=volunteer;
	
	this.type=type;
	this.profession=profession;
	this.data=data;
	this.created_at=created_at;
	
	this.updated_at=updated_at;
	
	
}
	
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public void setVolunteer(String volunteer) {
		this.volunteer = volunteer;
	}
	
	
	//getters
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getCreated_at() {
		return created_at;
	}
	public String getData() {
		return data;
	}
	public String getEmail() {
		return email;
	}
	public String getGender() {
		return gender;
	}
	public String getId() {
		return id;
	}
	public String getMobile() {
		return mobile;
	}
	public String getName() {
		return name;
	}
	public String getNationality() {
		return nationality;
	}
	public String getPassport() {
		return passport;
	}
	public String getPhoto() {
		return photo;
	}
	public String getProfession() {
		return profession;
	}
	public String getState() {
		return state;
	}
	public String getType() {
		return type;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getVolunteer() {
		return volunteer;
	}
	
	
	
}
