package org.etms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.etms.domain.Employee;
import org.etms.domain.Project;
import org.etms.exceptions.EmployeeDbFailure;
import org.etms.exceptions.ProjectDbFailure;
import org.etms.services.EmployeeService;
import org.etms.services.ProjectService;

@WebServlet({ "/ProjectProcess" })
public class ProjectProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String redirectURL;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String service = request.getParameter("Service");
		int projectId = 0;

		switch (service) {
		case "addPrj":
			boolean isErrorInAdd = false;
			try {
				projectId = addProject(request);
			} catch (ProjectDbFailure e) {
				// set error message in attribute and redirect to error page
				isErrorInAdd = true;
				request.setAttribute("Error", e.getReasonStr());
			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				isErrorInAdd = true;
				request.setAttribute("Error", e.getReasonStr());
			}

			if (isErrorInAdd) {
				List<Employee> employeeList = getAllEmployeeList();
				request.setAttribute("managerList", employeeList);
			}
			request.setAttribute("projectId", projectId);
			redirectURL = "/WEB-INF/Views/ProjectAddForm.jsp";
			break;
		case "updatePrj":
			boolean isUpdate = false;
			boolean isErrorInUpdate = false;
			try {
				isUpdate = updateProject(request);

			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				isErrorInUpdate = true;
				request.setAttribute("Error", e.getReasonStr());
			} catch (ProjectDbFailure e) {
				// set error message in attribute and redirect to error page
				isErrorInUpdate = true;
				request.setAttribute("Error", e.getReasonStr());
			}

			if (isErrorInUpdate) {
				List<Employee> employeeList = getAllEmployeeList();
				request.setAttribute("managerList", employeeList);
			}

			request.setAttribute("isUpdate", isUpdate);
			redirectURL = "/WEB-INF/Views/ProjectUpdateForm.jsp";
			break;
		case "removePrj":
			boolean isDelete = false;
			try {
				isDelete = removeProject(request);
			} catch (ProjectDbFailure e) {
				request.setAttribute("Error", e.getReasonStr());
			}
			request.setAttribute("isDelete", isDelete);
			redirectURL = "/WEB-INF/Views/ProjectDeleteForm.jsp";
			break;
		}
		RequestDispatcher dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);

	}

	private int addProject(HttpServletRequest request) throws ProjectDbFailure,
			EmployeeDbFailure {
		ProjectService prjService = new ProjectService();
		Project newProject = constructProjectFromRequest(request);
		int newPrjId = 0;
		newPrjId = prjService.insertProject(newProject);
		return newPrjId;
	}

	private boolean updateProject(HttpServletRequest request)
			throws ProjectDbFailure, EmployeeDbFailure {
		ProjectService prjService = new ProjectService();
		Project updateProject = constructProjectFromRequest(request);
		request.setAttribute("project", updateProject);
		String prjIdStr = request.getParameter("projectId");
		int prjId = Integer.parseInt(prjIdStr);
		updateProject.setProjectId(prjId);
		boolean isUpdate = false;
		isUpdate = prjService.updateProjectById(updateProject);
		return isUpdate;
	}

	private boolean removeProject(HttpServletRequest request)
			throws ProjectDbFailure {
		String prjIdStr = request.getParameter("PrjId");
		int prjId = Integer.parseInt(prjIdStr);
		ProjectService prjService = new ProjectService();
		boolean isRemoved = prjService.removeProjectById(prjId);
		return isRemoved;
	}

	private Project constructProjectFromRequest(HttpServletRequest request) {
		Project project = new Project();

		project.setName(request.getParameter("projectName"));
		project.setDescription(request.getParameter("prjDescription"));
		String managerIdStr = request.getParameter("managerList");
		int mngrId = Integer.parseInt(managerIdStr);
		project.setManagerId(mngrId);
		String teamSizeStr = request.getParameter("teamMember");
		int teamSize = Integer.parseInt(teamSizeStr);
		project.setTeamMember(teamSize);
		return project;
	}

	private List<Employee> getAllEmployeeList() {
		EmployeeService empService = new EmployeeService();
		List<Employee> employeeList = null;
		try {
			employeeList = empService.findAllEmployees();
		} catch (EmployeeDbFailure e) {
			// e.printStackTrace();
		}
		return employeeList;

	}

	private Project getProjectDetails(int prjId) throws ProjectDbFailure, EmployeeDbFailure {
		ProjectService prjService = new ProjectService();
		Project project = new Project();
		project = prjService.findProjectByProjectId(prjId);
		return project;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatch;
		String service = request.getParameter("Service");

		List<Employee> employeeList = getAllEmployeeList();
		request.setAttribute("managerList", employeeList);

		switch (service) {
		case "addPrj":
			redirectURL = "/WEB-INF/Views/ProjectAddForm.jsp";
			break;
		case "updatePrj":
			String prjIdStr = request.getParameter("PrjId");
			int prjId = Integer.parseInt(prjIdStr);
			
			// redirect to page and show employee details based on empId
			Project project = null;
			try {
				project = getProjectDetails(prjId);
			} catch (ProjectDbFailure e) {
				// set error message in attribute and redirect to error page
				request.setAttribute("Error", e.getReasonStr());
			} catch (EmployeeDbFailure e) {
				// set error message in attribute and redirect to error page
				request.setAttribute("Error", e.getReasonStr());
			}
			request.setAttribute("project", project);
			request.setAttribute("Service", "updatePrj");
			redirectURL = "/WEB-INF/Views/ProjectUpdateForm.jsp";
			break;
		case "removePrj":
			String prjIdRmv = request.getParameter("PrjId");
			int projectId = Integer.parseInt(prjIdRmv);
			request.setAttribute("projectId", projectId);
			redirectURL = "/WEB-INF/Views/ProjectDeleteForm.jsp";
			break;
		}

		dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}

}
