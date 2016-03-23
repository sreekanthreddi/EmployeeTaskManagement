package org.etms.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.etms.domain.Department;
import org.etms.domain.Employee;
import org.etms.exceptions.EmployeeDbFailure;
import org.etms.services.EmployeeService;
import org.etms.webservicereader.JsonDepartmentListReader;

@WebServlet({ "/EmployeeProcess" })
public class EmployeeProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String redirectURL;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String service = request.getParameter("Service");

		switch (service) {
		case "addEmp":
			int newEmpId = 0;
			try {
				newEmpId = addEmployee(request);
			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				request.setAttribute("Error", e.getReasonStr());
			}
			ArrayList<Department> deptListForAdd = getDeptList();
			request.setAttribute("departmentList", deptListForAdd);
			request.setAttribute("employeeId", newEmpId);
			redirectURL = "/WEB-INF/Views/EmployeeAddForm.jsp";
			break;
		case "updateEmp":
			boolean isUpdate = false;
			try {
				isUpdate = updateEmployee(request);
			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				request.setAttribute("Error", e.getReasonStr());
			}
			ArrayList<Department> deptListForUpdate = getDeptList();
			request.setAttribute("departmentList", deptListForUpdate);
			request.setAttribute("isUpdate", isUpdate);
			redirectURL = "/WEB-INF/Views/EmployeeUpdateForm.jsp";
			break;
		case "removeEmp":
			boolean isDelete = false;
			try {
				isDelete = removeEmployee(request);
			} catch (EmployeeDbFailure e) {
				request.setAttribute("Error", e.getReasonStr());
			}
			request.setAttribute("isDelete", isDelete);
			redirectURL = "/WEB-INF/Views/EmployeeDeleteForm.jsp";
			break;
		}

		RequestDispatcher dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}

	private int addEmployee(HttpServletRequest request)
			throws EmployeeDbFailure {
		EmployeeService empService = new EmployeeService();
		Employee newEmployee = constructEmployeeFromRequest(request);
		int newEmpId = 0;
		newEmpId = empService.insertEmployee(newEmployee);
		return newEmpId;
	}

	private boolean updateEmployee(HttpServletRequest request)
			throws EmployeeDbFailure {
		EmployeeService empService = new EmployeeService();
		Employee updateEmployee = constructEmployeeFromRequest(request);
		request.setAttribute("employee", updateEmployee);
		String empIdStr = request.getParameter("employeeId");
		int empId = Integer.parseInt(empIdStr);
		updateEmployee.setEmployeeId(empId);
		boolean isUpdate = false;
		isUpdate = empService.updateEmployeeById(updateEmployee);
		return isUpdate;
	}

	private boolean removeEmployee(HttpServletRequest request)
			throws EmployeeDbFailure {
		String empIdStr = request.getParameter("EmpId");
		int empId = Integer.parseInt(empIdStr);
		EmployeeService empService = new EmployeeService();
		boolean isRemoved = empService.removeEmployeeById(empId);
		return isRemoved;
	}

	private Employee constructEmployeeFromRequest(HttpServletRequest request) {
		Employee employee = new Employee();

		employee.setFirstName(request.getParameter("firstName"));
		employee.setLastName(request.getParameter("lastName"));
		employee.setMiddleName(request.getParameter("middleName"));

		DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
		Date birthDate = null;
		Date joiningDate = null;

		try {
			birthDate = formatter.parse(request.getParameter("birthDate")
					.toString());
			joiningDate = formatter.parse(request.getParameter("joiningDate")
					.toString());
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		employee.setBirthDate(birthDate);
		employee.setJoiningDate(joiningDate);
		employee.setEmailId(request.getParameter("email"));
		employee.setContactNo(request.getParameter("contactNo"));
		employee.setCurrentAddress(request.getParameter("currentAdd"));
		employee.setDepartment(request.getParameter("department"));
		employee.setDesignation(request.getParameter("designation"));
		employee.setStock(request.getParameter("stock"));
		return employee;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatch;
		String service = request.getParameter("Service");
		switch (service) {
		case "addEmp":
			ArrayList<Department> deptListForAdd = getDeptList();
			request.setAttribute("departmentList", deptListForAdd);

			redirectURL = "/WEB-INF/Views/EmployeeAddForm.jsp";
			break;
		case "updateEmp":
			String empIdStr = request.getParameter("EmpId");
			int empId = Integer.parseInt(empIdStr);
			// redirect to page and show employee details based on empId
			ArrayList<Department> deptListForUpdate = getDeptList();
			request.setAttribute("departmentList", deptListForUpdate);
			Employee employee = getEmployeeDetails(empId);
			request.setAttribute("employee", employee);
			request.setAttribute("Service", "updateEmp");
			redirectURL = "/WEB-INF/Views/EmployeeUpdateForm.jsp";
			break;
		case "removeEmp":
			String empIdRmv = request.getParameter("EmpId");
			int employeeId = Integer.parseInt(empIdRmv);
			request.setAttribute("employeeId", employeeId);
			redirectURL = "/WEB-INF/Views/EmployeeDeleteForm.jsp";
			break;
		}

		dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}

	private Employee getEmployeeDetails(int empId) {
		EmployeeService empService = new EmployeeService();
		Employee employee = new Employee();
		try {
			employee = empService.findEmployeeById(empId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EmployeeDbFailure e) {
			e.printStackTrace();
		}
		return employee;
	}

	private ArrayList<Department> getDeptList() {
		JsonDepartmentListReader jsonReader = new JsonDepartmentListReader();
		ArrayList<Department> deptList = jsonReader.getDeptListFromWebService();
		return deptList;
	}
}