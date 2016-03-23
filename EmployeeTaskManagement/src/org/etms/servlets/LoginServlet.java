package org.etms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({ "/Login.html" })
public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String redirectURL;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		response.setContentType("text/html");

		if (username.equals("etmsadmin") && password.equals("testpass")) {
			// create session
			HttpSession session = request.getSession(true);
			
			session.setAttribute("userName", "Admin");
			session.setAttribute("userRole", "Resource Manager");
			// forward to ETMS Home jsp
			redirectURL = "/WEB-INF/Views/ETMSHome.jsp";
		} else {
			String message = "OOps!!! Invalid Username/Password";
			request.setAttribute("Error", message);
			redirectURL = "/LoginForm.jsp";
		}

		RequestDispatcher dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}
}
