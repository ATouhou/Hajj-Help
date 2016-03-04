package com.labbayak.model;

public class OperatorPackagesModel {
	
	
	public String oppackgId;
	public String created_at;
	public String updated_at;
	public String details;
	public String oppackgoperator;
	public String packagename;
	
	
	public OperatorPackagesModel() {
		// TODO Auto-generated constructor stub
	}
	public OperatorPackagesModel(String id,String created_at,String updated_at,String details,
			String oppackgoperator,String packagename) {
		// TODO Auto-generated constructor stub
		
		this.oppackgId=id;
		this.created_at=created_at;
		this.updated_at=updated_at;
		this.details=details;
		this.oppackgoperator=oppackgoperator;
		this.packagename=packagename;
		
	}
	
	//setters
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public void setOppackgId(String oppackgId) {
		this.oppackgId = oppackgId;
	}
	public void setOppackgoperator(String oppackgoperator) {
		this.oppackgoperator = oppackgoperator;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	//getters
	
	public String getCreated_at() {
		return created_at;
	}
	public String getDetails() {
		return details;
	}
	public String getOppackgId() {
		return oppackgId;
	}
	public String getOppackgoperator() {
		return oppackgoperator;
	}
	public String getPackagename() {
		return packagename;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	
}
