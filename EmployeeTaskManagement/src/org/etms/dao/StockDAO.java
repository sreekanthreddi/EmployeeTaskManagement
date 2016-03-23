package org.etms.dao;

import java.util.List;

import org.etms.domain.Employee;
import org.etms.domain.Stock;
import org.etms.exceptions.StockDbFailure;

public interface StockDAO {

	public List<Stock> GetStockEmployees() throws StockDbFailure;
}
