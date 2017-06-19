package com.example.demo.model;

public class Department {

	private String deptName;
	private String deptId;
	
	public Department() {
		
	}
	
	public Department(String deptName, String deptId) {
		this.deptName = deptName;
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
}
