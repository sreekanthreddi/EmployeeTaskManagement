package org.etms.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.etms.domain.Employee;
import org.etms.exceptions.EmployeeDbFailure;
import org.etms.services.EmployeeService;

@WebServlet({ "/ViewEmployee" })
public class ViewEmployeeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String redirectURL;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext context = getServletContext();
		String searchBy = request.getParameter("searchBy");
		List<Employee> employeeList = new ArrayList<Employee>();
		Employee employee = new Employee();
		switch (searchBy) {
		case "empId":
			try {
				employee = searchEmployeeById(request);
				employeeList.add(employee);
			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				request.setAttribute("Error", e.getReasonStr());
			}
			
			break;
		case "empName":
			// search by name
			try {
				employeeList = searchEmployeeByName(request);
			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				request.setAttribute("Error", e.getReasonStr());
			}
			break;
		case "empEmail":
			// search by email
			employee = searchEmployeeByEmail(request);
			employeeList.add(employee);
			break;
		case "empDept":
			// search by department
			try {
				employeeList = searchEmployeeByDepartment(request);
			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				request.setAttribute("Error", e.getReasonStr());
			}
			break;
		default:
			// view all employees
			try {
				employeeList = searchAllEmployees();
			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				request.setAttribute("Error", e.getReasonStr());
			}
			break;
		}

		request.setAttribute("searchBy", searchBy);
		request.setAttribute("employeeList", employeeList);
		redirectURL = "/WEB-INF/Views/ViewEmployee.jsp";
		RequestDispatcher dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}

	private Employee searchEmployeeById(HttpServletRequest request)
			throws EmployeeDbFailure {
		String employeeId = request.getParameter("employeeId");

		EmployeeService empService = new EmployeeService();
		Employee employee = new Employee();
		try {
			int empId = Integer.parseInt(employeeId);
			employee = empService.findEmployeeById(empId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return employee;
	}

	private List<Employee> searchEmployeeByName(HttpServletRequest request)
			throws EmployeeDbFailure {
		String fname = request.getParameter("employeeFName");
		String lname = request.getParameter("employeeLName");

		EmployeeService empService = new EmployeeService();
		List<Employee> employeeList = null;

		employeeList = empService.findEmployeesByName(fname, lname);

		return employeeList;
	}

	private Employee searchEmployeeByEmail(HttpServletRequest request) {
		String email = request.getParameter("emailId");

		EmployeeService empService = new EmployeeService();
		Employee employee = new Employee();
		try {
			employee = empService.findEmployeeByEmail(email);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EmployeeDbFailure e) {
			e.printStackTrace();
		}
		return employee;
	}

	private List<Employee> searchEmployeeByDepartment(HttpServletRequest request)
			throws EmployeeDbFailure {
		String department = request.getParameter("departmentName");

		EmployeeService empService = new EmployeeService();
		List<Employee> employeeList = null;

		employeeList = empService.findEmployeeByDepartment(department);

		return employeeList;
	}

	private List<Employee> searchAllEmployees() throws EmployeeDbFailure {
		EmployeeService empService = new EmployeeService();
		List<Employee> employeeList = null;

		employeeList = empService.findAllEmployees();

		return employeeList;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatch;

		redirectURL = "/WEB-INF/Views/ViewEmployee.jsp";

		dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}
}