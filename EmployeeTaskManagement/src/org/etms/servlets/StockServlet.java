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
import org.etms.domain.Stock;
import org.etms.exceptions.StockDbFailure;
import org.etms.services.StockService;


@WebServlet({ "/EmployeeViewStockForm" })
public class StockServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String redirectURL;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext context = getServletContext();
		String searchBy = request.getParameter("searchBy");
		List<Stock> Stocklist;
		Stock stock = new Stock();

		StockService stockService = new StockService();
		try {
			Stocklist = stockService.GetStockEmployees();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (StockDbFailure e) {
			e.printStackTrace();
		}
	}


		protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatch;

		redirectURL = "/WEB-INF/Views/EmployeeViewStockForm.jsp";
		

		dispatch = context.getRequestDispatcher(redirectURL);
		dispatch.forward(request, response);
	}
}