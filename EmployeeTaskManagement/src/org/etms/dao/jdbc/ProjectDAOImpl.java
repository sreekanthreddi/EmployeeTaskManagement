package org.etms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.etms.dao.ProjectDAO;
import org.etms.database.ETMSDb;
import org.etms.domain.Project;
import org.etms.exceptions.ProjectDbFailure;

public class ProjectDAOImpl implements ProjectDAO {

	@Override
	public List<Project> findAllProjects() throws ProjectDbFailure {
		List<Project> findProject = new ArrayList<Project>();
		ResultSet results;

		String sqlQuery = "SELECT * FROM project";

		try (Connection dbConn = ETMSDb.getConnection();
				Statement stmt = dbConn.createStatement()) {
			results = stmt.executeQuery(sqlQuery);

			if (results.next()) {
				// read project data
				findProject = ProjectFactory.constructProjectList(results);
			}
			results.close();
		} catch (SQLException ex) {
			throw new ProjectDbFailure(ProjectDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new ProjectDbFailure(ProjectDbFailure.NAMMING_EX);
		}
		return findProject;
	}

	@Override
	public int insertProject(Project newProject) throws ProjectDbFailure {
		String queryStr = "INSERT INTO project (name,description,managerId,teammember) "
				+ "VALUES (?,?,?,?)";

		int projectId = 0;
		try (Connection dbConn = ETMSDb.getConnection();
				PreparedStatement queryStmt = dbConn.prepareStatement(queryStr,
						Statement.RETURN_GENERATED_KEYS)) {
			queryStmt.setString(1, newProject.getName());
			queryStmt.setString(2, newProject.getDescription());
			queryStmt.setInt(3, newProject.getManagerId());
			queryStmt.setInt(4, newProject.getTeamMember());

			int numRow = queryStmt.executeUpdate();

			ResultSet rs = queryStmt.getGeneratedKeys();
			if (rs.next()) {
				projectId = rs.getInt(1);
			}

			rs.close();
		} catch (SQLException ex) {
			throw new ProjectDbFailure(ProjectDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new ProjectDbFailure(ProjectDbFailure.NAMMING_EX);
		}
		return projectId;
	}

	@Override
	public boolean updateProjectById(Project project) throws ProjectDbFailure {
		String queryStr = "UPDATE project set name = ?,description = ? ,managerId = ? ,teammember = ? where projectId = ?";
		int rowsAffected;
		boolean isUpdate = false;
		try (Connection dbConn = ETMSDb.getConnection();
				PreparedStatement queryStmt = dbConn.prepareStatement(queryStr)) {

			// Turn off auto-commit so we can use transactions
			dbConn.setAutoCommit(false);

			queryStmt.setString(1, project.getName());
			queryStmt.setString(2, project.getDescription());
			queryStmt.setInt(3, project.getManagerId());
			queryStmt.setInt(4, project.getTeamMember());
			queryStmt.setInt(5, project.getProjectId());

			rowsAffected = queryStmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new ProjectDbFailure(ProjectDbFailure.RETRY);
			}
			isUpdate = true;
			dbConn.commit(); /* Everything went OK */
		} catch (SQLException ex) {
			throw new ProjectDbFailure(ProjectDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new ProjectDbFailure(ProjectDbFailure.NAMMING_EX);
		}
		return isUpdate;

	}

	@Override
	public boolean removeProjectById(int projectId) throws ProjectDbFailure {
		String queryStr = "DELETE FROM project where projectId = ?";
		int rowsAffected;
		boolean isDelete = false;
		try (Connection dbConn = ETMSDb.getConnection();
				PreparedStatement queryStmt = dbConn.prepareStatement(queryStr)) {

			// Turn off auto-commit so we can use transactions
			dbConn.setAutoCommit(false);
			queryStmt.setInt(1, projectId);
			rowsAffected = queryStmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new ProjectDbFailure(ProjectDbFailure.RETRY);
			}

			isDelete = true;
			dbConn.commit(); /* Everything went OK */
		} catch (SQLException ex) {
			throw new ProjectDbFailure(ProjectDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new ProjectDbFailure(ProjectDbFailure.NAMMING_EX);
		}
		return isDelete;
	}

	@Override
	public Project findProjectByProjectId(int projectId)
			throws ProjectDbFailure {
		Project findProject = null;
		ResultSet results;

		String sqlQuery = "SELECT * FROM project WHERE projectId = "
				+ projectId;

		try (Connection dbConn = ETMSDb.getConnection();
				Statement stmt = dbConn.createStatement()) {
			results = stmt.executeQuery(sqlQuery);
			if (!results.next()) {
				throw new ProjectDbFailure(ProjectDbFailure.BAD_PRJ_ID);
			} else {
				// read project data
				findProject = ProjectFactory.constructProject(results);
			}

			results.close();
		} catch (SQLException ex) {
			throw new ProjectDbFailure(ProjectDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new ProjectDbFailure(ProjectDbFailure.NAMMING_EX);
		}

		return findProject;
	}

	@Override
	public int getProjectCount() throws ProjectDbFailure {
		int prjCount = 0;
		ResultSet results;
		String queryStr = "SELECT count(*) prjcount FROM project";
		try (Connection dbConn = ETMSDb.getConnection();
				Statement stmt = dbConn.createStatement()) {
			results = stmt.executeQuery(queryStr);
			if (results.next()) {
				// read project data
				prjCount = results.getInt("prjcount");
			}

			results.close();
		} catch (SQLException ex) {
			throw new ProjectDbFailure(ProjectDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new ProjectDbFailure(ProjectDbFailure.NAMMING_EX);
		}

		return prjCount;
	}

	@Override
	public Project findProjectByName(String name, boolean checkNotForSamePrj,
			int projectId) throws ProjectDbFailure {
		Project findProject = null;
		ResultSet results;
		String sqlQuery = "";
		if (checkNotForSamePrj) {
			sqlQuery = "SELECT * FROM project WHERE name = '" + name
					+ "' and projectId <> " + projectId;
		} else {
			sqlQuery = "SELECT * FROM project WHERE name = '" + name + "'";
		}
		try (Connection dbConn = ETMSDb.getConnection();
				Statement stmt = dbConn.createStatement()) {
			results = stmt.executeQuery(sqlQuery);
			if (results.next()) {
				// read project data
				findProject = ProjectFactory.constructProject(results);
			}

			results.close();
		} catch (SQLException ex) {
			throw new ProjectDbFailure(ProjectDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new ProjectDbFailure(ProjectDbFailure.NAMMING_EX);
		}

		return findProject;
	}

	static class ProjectFactory {

		public static Project constructProject(ResultSet results)
				throws SQLException {
			Project prj = new Project();
			prj.setProjectId(results.getInt("projectid"));
			prj.setName(results.getString("name"));
			prj.setDescription(results.getString("description"));
			prj.setManagerId(results.getInt("managerId"));
			prj.setTeamMember(results.getInt("teamMember"));
			return prj;
		}

		public static List<Project> constructProjectList(ResultSet results)
				throws SQLException {

			List<Project> findProject = new ArrayList<Project>();
			Project prj = new Project();

			while (results.next()) {
				prj = constructProject(results);
				findProject.add(prj);
			}
			return findProject;
		}
	}

	@Override
	public List<Project> findAllProjectsContainsName(String projectName,
			String sortBy) throws ProjectDbFailure {
		List<Project> findProjectList = null;
		ResultSet results;
		String sqlQuery = "SELECT * FROM project WHERE name like '%"
				+ projectName + "%' order by " + sortBy;
		try (Connection dbConn = ETMSDb.getConnection();
				Statement stmt = dbConn.createStatement()) {
			results = stmt.executeQuery(sqlQuery);
			if (results.next()) {
				// read project data
				findProjectList = ProjectFactory.constructProjectList(results);
			}

			results.close();
		} catch (SQLException ex) {
			throw new ProjectDbFailure(ProjectDbFailure.STMT_FAILED);
		} catch (NamingException e) {
			throw new ProjectDbFailure(ProjectDbFailure.NAMMING_EX);
		}

		return findProjectList;
	}
}
