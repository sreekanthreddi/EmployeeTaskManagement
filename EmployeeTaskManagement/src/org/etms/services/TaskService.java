package org.etms.services;

import org.etms.dao.EmployeeTaskDAO;
import org.etms.dao.jdbc.EmployeeTaskDAOImpl;
import org.etms.domain.EmployeeTask;
import org.etms.exceptions.EmployeeDbFailure;

public class TaskService {

	// Assign new project with first task
	// for that insert a record in emptask table and update team member count in
	// project table

	public boolean assignProjectToEmployee(EmployeeTask empTask) throws EmployeeDbFailure {
		EmployeeTaskDAO emp = new EmployeeTaskDAOImpl();
		boolean isAssign = emp.assignProjectToEmployee(empTask);
		return isAssign;
	}
}
