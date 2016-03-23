package org.etms.domain;

public class Department {
	private String departmentId;
	private String departmentName;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String toString() {
		return "Department Id: " + departmentId + "\nDepartment Name:"
				+ departmentName + "\n";
	}
}
