package org.etms.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.etms.domain.Employee;
import org.etms.domain.EmployeeTask;
import org.etms.domain.Project;
import org.etms.exceptions.EmployeeDbFailure;
import org.etms.exceptions.ProjectDbFailure;
import org.etms.services.EmployeeService;
import org.etms.services.ProjectService;
import org.etms.services.TaskService;

@WebServlet({ "/AssignProject" })
public class AssignProjectServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String redirectURL;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context;

		context = getServletContext();

		// get employee list
		List<Employee> employeeList = null;
		try {
			employeeList = getAllEmployeeList();
		} catch (EmployeeDbFailure e) {

		}
		request.setAttribute("employeeList", employeeList);

		// get project list
		List<Project> projectList = null;
		try {
			projectList = getAllProjectList();
		} catch (ProjectDbFailure e) {
			// set error message in attribute and redirect to error page
			request.setAttribute("Error", e.getReasonStr());
		} catch (EmployeeDbFailure e) {
			// set error message in attribute and redirect to error page
			request.setAttribute("Error", e.getReasonStr());
		}
		request.setAttribute("projectList", projectList);

		redirectURL = "/WEB-INF/Views/AssignProject.jsp";

		RequestDispatcher dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}

	private List<Project> getAllProjectList() throws ProjectDbFailure,
			EmployeeDbFailure {
		ProjectService prjService = new ProjectService();
		List<Project> projectList = null;
		projectList = prjService.findAllProjects();
		return projectList;
	}

	private List<Employee> getAllEmployeeList() throws EmployeeDbFailure {
		EmployeeService empService = new EmployeeService();
		List<Employee> employeeList = null;
		employeeList = empService.findAllEmployees();
		return employeeList;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext context = getServletContext();

		// get employee list
		List<Employee> employeeList = null;
		try {
			employeeList = getAllEmployeeList();
		} catch (EmployeeDbFailure e) {

		}
		request.setAttribute("employeeList", employeeList);

		// get project list
		List<Project> projectList = null;
		try {
			projectList = getAllProjectList();
		} catch (ProjectDbFailure e) {
			// set error message in attribute and redirect to error page
			request.setAttribute("Error", e.getReasonStr());
		} catch (EmployeeDbFailure e) {
			// set error message in attribute and redirect to error page
			request.setAttribute("Error", e.getReasonStr());
		}
		request.setAttribute("projectList", projectList);

		EmployeeTask employeeTask = constructEmployeeTaskFromRequest(request);
		TaskService taskService = new TaskService();

		boolean isAssign;
		try {
			isAssign = taskService.assignProjectToEmployee(employeeTask);
			request.setAttribute("isAssign", isAssign);
			request.setAttribute("employeeTask", employeeTask);
		} catch (EmployeeDbFailure e) {
			request.setAttribute("Error", e.getReasonStr());
		}
	
		redirectURL = "/WEB-INF/Views/AssignProject.jsp";
		RequestDispatcher dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}

	private EmployeeTask constructEmployeeTaskFromRequest(
			HttpServletRequest request) {
		EmployeeTask employeeTask = new EmployeeTask();

		String empStr = request.getParameter("employeeId");
		int empId = Integer.parseInt(empStr);

		employeeTask.setEmployeeId(empId);

		String prjStr = request.getParameter("projectId");
		int projectId = Integer.parseInt(prjStr);
		employeeTask.setProjectId(projectId);
		employeeTask.setDescription(request.getParameter("description"));

		DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
		Date startDate = null;
		Date dueDate = null;
		Date actualStartDate = null;
		Date actualEndDate = null;

		try {
			startDate = formatter.parse(request.getParameter("startDate")
					.toString());
			dueDate = formatter.parse(request.getParameter("dueDate")
					.toString());
			String date1 = request.getParameter("actualStartDate").toString();
			if (date1 != "") {
				actualStartDate = formatter.parse(date1);
			}
			String date2 = request.getParameter("actualEndDate").toString();
			if (date2 != "") {
				actualEndDate = formatter.parse(date2);
			}
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		employeeTask.setStartDate(startDate);
		employeeTask.setEndDate(dueDate);
		employeeTask.setActualStartDate(actualStartDate);
		employeeTask.setActualEndDate(actualEndDate);
		employeeTask.setStatus(request.getParameter("status"));
		employeeTask.setCategory(request.getParameter("category"));
		employeeTask.setReasonOfMissingDueDate(request
				.getParameter("reasonOfMissingDueDate"));
		return employeeTask;
	}
}