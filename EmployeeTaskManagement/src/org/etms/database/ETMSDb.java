package org.etms.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ETMSDb {
	private static DataSource etmsDbDataSource = null;

	public static DataSource initDataSource() throws NamingException {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource dataSource = (DataSource) envContext
					.lookup("jdbc/etmsdb");
			return dataSource;
		} finally {
		}
	}

	public static Connection getConnection() throws NamingException,
			SQLException {
		Connection dbConn;

		if (etmsDbDataSource == null) {
			etmsDbDataSource = initDataSource();
		}

		dbConn = etmsDbDataSource.getConnection();
		return dbConn;
	}

	public static long getGeneratedPrimaryKey(PreparedStatement sqlStmt)
			throws SQLException {
		long id = -1;

		try (ResultSet generatedKeys = sqlStmt.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				id = generatedKeys.getLong(1);
			}
		}

		return id;
	}
}