package org.etms.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.etms.dao.StockDAO;
import org.etms.dao.jdbc.StockDAOImpl;
import org.etms.database.ETMSDb;
import org.etms.domain.Stock;
import org.etms.exceptions.StockDbFailure;;

public class StockDAOImpl  implements StockDAO {

	@Override
	public List<Stock> GetStockEmployees() throws StockDbFailure {
		List<Stock> findStock = new ArrayList<Stock>();
		
		ResultSet results; 

		String StockempIdsql = "SELECT FirstName,lastname,Stock FROM employee "
				+ "where Stock!=''";

		try (Connection dbConn = ETMSDb.getConnection();
				Statement StockempIdStmt = dbConn.createStatement()) {
			results = StockempIdStmt.executeQuery(StockempIdsql);

			if (results.next()) {
				// read employee data
				findStock = StockFactory.constructStockList(results);
			}
			System.out.println(findStock.toString());
			results.close();
			// EMSDataSourceFactory.shutdownDataSource();
		} catch (SQLException ex) {
			throw new StockDbFailure(StockDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return findStock;
	}

static class StockFactory {

	public static Stock constructEmployee(ResultSet results)
			throws SQLException {
		Stock emp = new Stock();
		
		emp.setFirstName(results.getString("firstname"));
		emp.setFirstName(results.getString("lastname"));
		emp.setStock(results.getString("stock"));
		
		return emp;
	}

	public static List<Stock> constructStockList(ResultSet results)
			throws SQLException {

		List<Stock> findStock = new ArrayList<Stock>();
		Stock emp = new Stock();

		while (results.next()) {
			emp = constructEmployee(results);
			findStock.add(emp);
		}
		return findStock;
	}
}

}