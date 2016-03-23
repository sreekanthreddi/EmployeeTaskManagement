package org.etms.dao;

import org.etms.domain.EmployeeTask;
import org.etms.exceptions.EmployeeDbFailure;

public interface EmployeeTaskDAO {
	
	// require transaction
	public boolean assignProjectToEmployee(EmployeeTask empTask) throws EmployeeDbFailure;

}
