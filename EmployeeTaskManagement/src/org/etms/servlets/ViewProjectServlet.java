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

import org.etms.domain.Project;
import org.etms.exceptions.EmployeeDbFailure;
import org.etms.exceptions.ProjectDbFailure;
import org.etms.services.ProjectService;

@WebServlet({ "/ViewProject" })
public class ViewProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String redirectURL;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String sortBy = request.getParameter("sortBy");
		String name = request.getParameter("projectName");
		String paramSortBy = "";
		if (sortBy.equalsIgnoreCase("prjName")) {
			paramSortBy = "name";
		} else if (sortBy.equalsIgnoreCase("teamSize")) {
			paramSortBy = "teammember";
		}
		List<Project> projectList = null;
		try {
			projectList = searchProject(name, paramSortBy);
		} catch (ProjectDbFailure e) {
			request.setAttribute("Error", e.getReasonStr());
		} catch (EmployeeDbFailure e) {
			request.setAttribute("Error", e.getReasonStr());
		}
		request.setAttribute("projectList", projectList);
		redirectURL = "/WEB-INF/Views/ViewProject.jsp";
		RequestDispatcher dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}

	private List<Project> searchProject(String projectName, String sortBy)
			throws ProjectDbFailure, EmployeeDbFailure {
		ProjectService prjService = new ProjectService();
		List<Project> projectList = prjService.findAllProjectsContainsName(
				projectName, sortBy);
		return projectList;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatch;

		redirectURL = "/WEB-INF/Views/ViewProject.jsp";

		dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}
}
