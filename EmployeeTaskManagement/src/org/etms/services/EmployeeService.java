package org.etms.services;

import java.util.List;

import org.etms.dao.EmployeeDAO;
import org.etms.dao.jdbc.EmployeeDAOImpl;
import org.etms.domain.Employee;
import org.etms.exceptions.EmployeeDbFailure;

public class EmployeeService {
	public Employee findEmployeeById(int employeeId) throws EmployeeDbFailure {
		EmployeeDAO emp = new EmployeeDAOImpl();
		Employee findEmployee = emp.findEmployeeById(employeeId);
		return findEmployee;
	}

	public List<Employee> findAllEmployees() throws EmployeeDbFailure {
		EmployeeDAO emp = new EmployeeDAOImpl();
		List<Employee> findEmployee = emp.findAllEmployees();
		return findEmployee;
	}

	public int insertEmployee(Employee newEmployee) throws EmployeeDbFailure {
		// check email id exist or not
		EmployeeDAO employeeDao = new EmployeeDAOImpl();

		Employee emp = employeeDao.findEmployeeByEmail(
				newEmployee.getEmailId(), false, 0);
		if (emp != null) {
			throw new EmployeeDbFailure(EmployeeDbFailure.UNIQUE_EMAIL);
		} else {
			int newEmployeeId = employeeDao.insertEmployee(newEmployee);
			return newEmployeeId;
		}
	}

	public boolean updateEmployeeById(Employee employee)
			throws EmployeeDbFailure {
		// check email id exist or not
		EmployeeDAO employeeDao = new EmployeeDAOImpl();

		Employee emp = employeeDao.findEmployeeByEmail(employee.getEmailId(),
				true, employee.getEmployeeId());

		if (emp != null) {
			throw new EmployeeDbFailure(EmployeeDbFailure.UNIQUE_EMAIL);
		} else {
			boolean isUpdated = employeeDao.updateEmployeeById(employee);
			return isUpdated;
		}
	}

	public boolean removeEmployeeById(int employeeId) throws EmployeeDbFailure {
		EmployeeDAO emp = new EmployeeDAOImpl();
		boolean isDeleted = emp.removeEmployeeById(employeeId);
		return isDeleted;
	}

	public List<Employee> findEmployeeByDepartment(String department)
			throws EmployeeDbFailure {
		EmployeeDAO emp = new EmployeeDAOImpl();
		List<Employee> findEmployee = emp.findEmployeeByDepartment(department);
		return findEmployee;
	}

	public Employee findEmployeeByEmail(String email) throws EmployeeDbFailure {
		EmployeeDAO emp = new EmployeeDAOImpl();
		Employee findEmployee = emp.findEmployeeByEmail(email, false, 0);
		return findEmployee;
	}

	public List<Employee> findEmployeesByName(String fname, String lname)
			throws EmployeeDbFailure {
		EmployeeDAO emp = new EmployeeDAOImpl();
		List<Employee> findEmployee = emp.findEmployeesByName(fname, lname);
		return findEmployee;
	}

	public int getEmployeeCount() throws EmployeeDbFailure {
		EmployeeDAO emp = new EmployeeDAOImpl();
		int empCount = emp.getEmployeeCount();
		return empCount;
	}
}