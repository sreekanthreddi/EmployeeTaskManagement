package org.etms.dao;

import java.util.List;

import org.etms.domain.Employee;
import org.etms.exceptions.EmployeeDbFailure;

public interface EmployeeDAO {

	public List<Employee> findAllEmployees() throws EmployeeDbFailure;

	public Employee findEmployeeById(int employeeId) throws EmployeeDbFailure;

	public int insertEmployee(Employee newEmployee) throws EmployeeDbFailure;

	public boolean updateEmployeeById(Employee employee)
			throws EmployeeDbFailure;

	public boolean removeEmployeeById(int employeeId) throws EmployeeDbFailure;

	public List<Employee> findEmployeeByDepartment(String department)
			throws EmployeeDbFailure;

	// public Employee findEmployeeByYearsOfWorking(int years);

	public int getEmployeeCount() throws EmployeeDbFailure;

	//public Employee findEmployeeByEmail(String email) throws EmployeeDbFailure;

	public List<Employee> findEmployeesByName(String fname, String lname)
			throws EmployeeDbFailure;

	Employee findEmployeeByEmail(String email, boolean checkNotForSameEmp,
			int employeeId) throws EmployeeDbFailure;
}
