package org.etms.domain;

import java.util.Date;

public class Employee {
	private int employeeId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String emailId;
	private Date birthDate;
	private Date joiningDate;
	private Date leavingDate;
	private String contactNo;
	private String currentAddress;
	private String department;
	private String designation;
	private Date createdDate;
	private Date modifiedDate;
	private String stock;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Date getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation
	 *            the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String toString() {
		String str = "\tEmployee Id: " + employeeId + "\n\tFirst Name: "
				+ firstName + "\n\tLast Name: " + lastName + "\n\tMiddle Name: "
				+ middleName + "\n\tEmail: " + emailId + "\n\tBirth date: "
				+ birthDate + "\n\tJoining date: " + joiningDate
				+ "\n\tContact No: " + contactNo + "\n\tAddress: " + currentAddress
				+ "\n\tDepartment: " + department + "\n\tStock: " + stock + "\n\tDesignation: " + designation ;
		return str;
	}
}
