package org.etms.services;

import java.util.List;

import org.etms.dao.EmployeeDAO;
import org.etms.dao.StockDAO;
import org.etms.dao.jdbc.EmployeeDAOImpl;
import org.etms.dao.jdbc.StockDAOImpl;
import org.etms.domain.Employee;
import org.etms.domain.Stock;
import org.etms.exceptions.EmployeeDbFailure;
import org.etms.exceptions.StockDbFailure;

public class StockService {
	
	public List<Stock> GetStockEmployees() throws StockDbFailure {
		StockDAO stock = new StockDAOImpl();
		List<Stock> findEmployee = stock.GetStockEmployees();
		return findEmployee;
	}

	public List<Employee> findEmployeesByName(String fname, String lname)
			throws EmployeeDbFailure {
		EmployeeDAO emp = new EmployeeDAOImpl();
		List<Employee> findEmployee = emp.findEmployeesByName(fname, lname);
		return findEmployee;
	}
}