package org.etms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.etms.dao.EmployeeDAO;
import org.etms.database.ETMSDb;
import org.etms.domain.Employee;
import org.etms.exceptions.EmployeeDbFailure;

public class EmployeeDAOImpl  implements EmployeeDAO {

	@Override
	public List<Employee> findAllEmployees() throws EmployeeDbFailure {
		List<Employee> findEmployee = new ArrayList<Employee>();
		ResultSet results;

		String readEmpByIdSql = "SELECT * FROM employee ";

		try (Connection dbConn = ETMSDb.getConnection();
				Statement readEmpByIdStmt = dbConn.createStatement()) {
			results = readEmpByIdStmt.executeQuery(readEmpByIdSql);

			if (results.next()) {
				// read employee data
				findEmployee = EmployeeFactory.constructEmployeeList(results);
			}

			results.close();
			// EMSDataSourceFactory.shutdownDataSource();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return findEmployee;
	}

	@Override
	public Employee findEmployeeById(int employeeId) throws EmployeeDbFailure {
		Employee findEmployee = null;
		ResultSet results;

		String readEmpByIdSql = "SELECT * " + "FROM employee "
				+ "WHERE employeeId = " + employeeId;

		try (Connection dbConn = ETMSDb.getConnection();
				Statement readEmpByIdStmt = dbConn.createStatement()) {
			results = readEmpByIdStmt.executeQuery(readEmpByIdSql);
			if (!results.next()) {
				throw new EmployeeDbFailure(EmployeeDbFailure.BAD_EMP_ID);
			} else {
				// read employee data
				findEmployee = EmployeeFactory.constructEmployee(results);
			}

			results.close();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return findEmployee;
	}

	@Override
	public int insertEmployee(Employee newEmployee) throws EmployeeDbFailure {
		String queryStr = "INSERT INTO employee (firstname,lastname,middlename,email,birthdate,joiningdate,"
				+ "contactno,currentaddress,department,designation,createddate,stock) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		int employeeId = 0;
		try (Connection dbConn = ETMSDb.getConnection();
				PreparedStatement queryStmt = dbConn.prepareStatement(queryStr,
						Statement.RETURN_GENERATED_KEYS)) {
			queryStmt.setString(1, newEmployee.getFirstName());
			queryStmt.setString(2, newEmployee.getLastName());
			queryStmt.setString(3, newEmployee.getMiddleName());
			queryStmt.setString(4, newEmployee.getEmailId());

			java.util.Date date = newEmployee.getBirthDate();
			if (date != null) {
				queryStmt.setDate(5, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(5, null);
			}

			date = newEmployee.getJoiningDate();
			if (date != null) {
				queryStmt.setDate(6, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(6, null);
			}

			queryStmt.setString(7, newEmployee.getContactNo());
			queryStmt.setString(8, newEmployee.getCurrentAddress());
			queryStmt.setString(9, newEmployee.getDepartment());
			queryStmt.setString(10, newEmployee.getDesignation());
			java.util.Date today = new java.util.Date();
			queryStmt.setDate(11, convertJavaDateToSqlDate(today));
			queryStmt.setString(12, newEmployee.getStock());

			int numRow = queryStmt.executeUpdate();

			ResultSet rs = queryStmt.getGeneratedKeys();
			if (rs.next()) {
				employeeId = rs.getInt(1);
			}

			rs.close();
			// EMSDataSourceFactory.shutdownDataSource();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employeeId;
	}

	public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	@Override
	public boolean updateEmployeeById(Employee employee)
			throws EmployeeDbFailure {
		String queryStr = "UPDATE employee set firstname = ?,lastname = ? ,middlename = ? ,email = ?,birthdate = ?,joiningdate = ?,"
				+ "contactno = ? ,currentaddress =?,department = ?,designation = ?,leavingdate = ?, modifieddate = ?,stock = ?  where employeeId = ?";
		int rowsAffected;
		boolean isUpdate = false;
		try (Connection dbConn = ETMSDb.getConnection();
				PreparedStatement queryStmt = dbConn.prepareStatement(queryStr)) {

			// Turn off auto-commit so we can use transactions
			dbConn.setAutoCommit(false);

			queryStmt.setString(1, employee.getFirstName());
			queryStmt.setString(2, employee.getLastName());
			queryStmt.setString(3, employee.getMiddleName());
			queryStmt.setString(4, employee.getEmailId());

			java.util.Date date = employee.getBirthDate();
			if (date != null) {
				queryStmt.setDate(5, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(5, null);
			}

			date = employee.getJoiningDate();
			if (date != null) {
				queryStmt.setDate(6, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(6, null);
			}

			queryStmt.setString(7, employee.getContactNo());
			queryStmt.setString(8, employee.getCurrentAddress());
			queryStmt.setString(9, employee.getDepartment());
			queryStmt.setString(10, employee.getDesignation());

			date = employee.getLeavingDate();
			if (date != null) {
				queryStmt.setDate(11, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(11, null);
			}

			java.util.Date today = new java.util.Date();
			queryStmt.setDate(12, convertJavaDateToSqlDate(today));
			queryStmt.setInt(13, employee.getEmployeeId());
			queryStmt.setString(14, employee.getStock());

			rowsAffected = queryStmt.executeUpdate();

			if (rowsAffected != 1) { /* Exactly one row should have been updated */
				dbConn.rollback();
				if (rowsAffected == 0) {
					throw new EmployeeDbFailure(EmployeeDbFailure.RETRY);
				}

				/* More than one row modified? */
				throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
			}
			isUpdate = true;
			dbConn.commit(); /* Everything went OK */
			// EMSDataSourceFactory.shutdownDataSource();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUpdate;
	}

	@Override
	public boolean removeEmployeeById(int employeeId) throws EmployeeDbFailure {
		String queryStr = "DELETE FROM employee where employeeId = ?";
		int rowsAffected;
		boolean isDelete = false;
		try (Connection dbConn = ETMSDb.getConnection();
				PreparedStatement queryStmt = dbConn.prepareStatement(queryStr)) {

			// Turn off auto-commit so we can use transactions
			dbConn.setAutoCommit(false);
			queryStmt.setInt(1, employeeId);
			rowsAffected = queryStmt.executeUpdate();

			if (rowsAffected != 1) { /* Exactly one row should have been updated */
				dbConn.rollback();
				if (rowsAffected == 0) {
					throw new EmployeeDbFailure(EmployeeDbFailure.RETRY);
				}

				/* More than one row modified? */
				throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
			}
			isDelete = true;
			dbConn.commit(); /* Everything went OK */
			// EMSDataSourceFactory.shutdownDataSource();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isDelete;
	}

	@Override
	public List<Employee> findEmployeeByDepartment(String department)
			throws EmployeeDbFailure {
		List<Employee> findEmployeeList = null;
		ResultSet results;

		String readEmpByIdSql = "SELECT * " + "FROM employee "
				+ "WHERE department = '" + department + "'";

		try (Connection dbConn = ETMSDb.getConnection();
				Statement readEmpByIdStmt = dbConn.createStatement()) {
			results = readEmpByIdStmt.executeQuery(readEmpByIdSql);
			if (results.next()) {
				// read employee data
				findEmployeeList = EmployeeFactory
						.constructEmployeeList(results);
			}

			results.close();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return findEmployeeList;
	}

	@Override
	public int getEmployeeCount() throws EmployeeDbFailure {
		int empCount = 0;
		ResultSet results;
		String readEmpByIdSql = "SELECT count(*) emppcount FROM employee";
		try (Connection dbConn = ETMSDb.getConnection();
				Statement readEmpByIdStmt = dbConn.createStatement()) {
			results = readEmpByIdStmt.executeQuery(readEmpByIdSql);
			if (results.next()) {
				// read employee data
				empCount = results.getInt("emppcount");
			}

			results.close();
			// EMSDataSourceFactory.shutdownDataSource();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return empCount;
	}

	@Override
	public Employee findEmployeeByEmail(String email,
			boolean checkNotForSameEmp, int employeeId)
			throws EmployeeDbFailure {
		Employee findEmployee = null;
		ResultSet results;

		String readEmpByIdSql = "";
		
		if (checkNotForSameEmp) {
			readEmpByIdSql = "SELECT * " + "FROM employee "
					+ "WHERE email = '" + email + "' and employeeId <> " + employeeId;
		} else {

			readEmpByIdSql = "SELECT * " + "FROM employee "
					+ "WHERE email = '" + email + "'";
		}
		try (Connection dbConn = ETMSDb.getConnection();
				Statement readEmpByIdStmt = dbConn.createStatement()) {
			results = readEmpByIdStmt.executeQuery(readEmpByIdSql);
			if (results.next()) {
				// read employee data
				findEmployee = EmployeeFactory.constructEmployee(results);
			}

			results.close();
			// EMSDataSourceFactory.shutdownDataSource();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return findEmployee;

	}

	@Override
	public List<Employee> findEmployeesByName(String fname, String lname)
			throws EmployeeDbFailure {
		List<Employee> findEmployeeList = null;
		ResultSet results;

		String readEmpByIdSql = "SELECT * " + "FROM employee "
				+ "WHERE firstname like '%" + fname + "%' or lastname like '%"
				+ lname + "%'";

		try (Connection dbConn = ETMSDb.getConnection();
				Statement readEmpByIdStmt = dbConn.createStatement()) {
			results = readEmpByIdStmt.executeQuery(readEmpByIdSql);
			if (results.next()) {
				// read employee data
				findEmployeeList = EmployeeFactory
						.constructEmployeeList(results);
			}

			results.close();
		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return findEmployeeList;
	}

	static class EmployeeFactory {

		public static Employee constructEmployee(ResultSet results)
				throws SQLException {
			Employee emp = new Employee();
			emp.setEmployeeId(results.getInt("employeeid"));
			emp.setFirstName(results.getString("firstname"));
			emp.setLastName(results.getString("lastname"));
			emp.setMiddleName(results.getString("middlename"));
			emp.setEmailId(results.getString("email"));
			emp.setBirthDate(results.getDate("birthdate"));
			emp.setJoiningDate(results.getDate("JoiningDate"));
			emp.setLeavingDate(results.getDate("LeavingDate"));
			emp.setContactNo(results.getString("ContactNo"));
			emp.setCurrentAddress(results.getString("CurrentAddress"));
			emp.setDepartment(results.getString("department"));
			emp.setDesignation(results.getString("designation"));
			emp.setStock(results.getString("stock"));
			emp.setCreatedDate(results.getDate("createddate"));
			emp.setModifiedDate(results.getDate("modifieddate"));
			return emp;
		}

		public static List<Employee> constructEmployeeList(ResultSet results)
				throws SQLException {

			List<Employee> findEmployee = new ArrayList<Employee>();
			Employee emp = new Employee();

			while (results.next()) {
				emp = constructEmployee(results);
				findEmployee.add(emp);
			}
			return findEmployee;
		}
	}

}
