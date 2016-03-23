package org.etms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import org.etms.dao.EmployeeTaskDAO;
import org.etms.database.ETMSDb;
import org.etms.domain.EmployeeTask;
import org.etms.exceptions.EmployeeDbFailure;

public class EmployeeTaskDAOImpl implements EmployeeTaskDAO {

	@Override
	public boolean assignProjectToEmployee(EmployeeTask empTask)
			throws EmployeeDbFailure {
		String queryAssignPrj = "INSERT INTO emptask (employeeid,projectid,description,startdate,duedate,actualstartdate,"
				+ "actualenddate,status,reasonformissingduedate,category,createddate) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		boolean isAssign = false;

		try (Connection dbConn = ETMSDb.getConnection();
				PreparedStatement queryStmt = dbConn
						.prepareStatement(queryAssignPrj)) {
			//ResultSet rs = null;
			
			// Turn off auto-commit so we can use transactions
			dbConn.setAutoCommit(false);

			queryStmt.setInt(1, empTask.getEmployeeId());
			queryStmt.setInt(2, empTask.getProjectId());
			queryStmt.setString(3, empTask.getDescription());

			java.util.Date date = empTask.getStartDate();
			if (date != null) {
				queryStmt.setDate(4, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(4, null);
			}

			date = empTask.getEndDate();
			if (date != null) {
				queryStmt.setDate(5, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(5, null);
			}

			date = empTask.getActualStartDate();
			if (date != null) {
				queryStmt.setDate(6, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(6, null);
			}

			date = empTask.getActualEndDate();
			if (date != null) {
				queryStmt.setDate(7, convertJavaDateToSqlDate(date));
			} else {
				queryStmt.setDate(7, null);
			}

			queryStmt.setString(8, empTask.getStatus());
			queryStmt.setString(9, empTask.getReasonOfMissingDueDate());
			queryStmt.setString(10, empTask.getCategory());
			java.util.Date today = new java.util.Date();
			queryStmt.setDate(11, convertJavaDateToSqlDate(today));

			// insert record
			int numRow = queryStmt.executeUpdate();

			if (numRow != 1) {
				throw new SQLException(
						"Error occurred during assigning project.");
			}

			/**** Once insert is success, process with project table **/
			
			// get latest team size from database
			int teamMember = getProjectDetails(empTask.getProjectId(), dbConn);

			// update team size count in project table
			teamMember = teamMember + 1;
			int rowUpdated = updateProjectTeamSize(teamMember,
					empTask.getProjectId(), dbConn);

			if (rowUpdated != 0) {
				isAssign = true;
				dbConn.commit();
			}

		} catch (SQLException ex) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new EmployeeDbFailure(EmployeeDbFailure.STMT_FAILED);
		}
		return isAssign;
	}

	private int getProjectDetails(int projectId, Connection dbConn)
			throws SQLException {

		// get project details
		String queryGetPrj = "SELECT * FROM project where projectid = "
				+ projectId;
		int teamMember = 0;

		try (Statement st = dbConn.createStatement()) {

			ResultSet rs = st.executeQuery(queryGetPrj);

			if (rs.next()) {
				teamMember = rs.getInt("teammember");
				// teamMember = teamMember + 1;
			} else {
				throw new SQLException("No such project exist.");
			}
			rs.close();
			return teamMember;

		} catch (SQLException ex) {
			throw new SQLException(
					"Error occurred during getting ptoject info.");
		}

	}

	private int updateProjectTeamSize(int teamMember, int projectId,
			Connection dbConn) throws SQLException {
		// update project by new team member count
		String queryUpdatePrj = "UPDATE PROJECT SET teammember = " + teamMember
				+ " WHERE Projectid =" + projectId;

		try (Statement st2 = dbConn.createStatement()) {
			int rowsAffected = st2.executeUpdate(queryUpdatePrj);

			if (rowsAffected == 0) {
				throw new SQLException("Team  member count not updated.");
			}
			return rowsAffected;

		} catch (SQLException ex) {
			throw new SQLException(
					"Error occurred during updating team member count for ptoject.");
		}
	}

	public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
}
