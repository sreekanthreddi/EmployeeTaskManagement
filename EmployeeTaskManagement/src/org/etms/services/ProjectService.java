package org.etms.services;

import java.util.List;

import org.etms.dao.ProjectDAO;
import org.etms.dao.jdbc.ProjectDAOImpl;
import org.etms.domain.Employee;
import org.etms.domain.Project;
import org.etms.exceptions.EmployeeDbFailure;
import org.etms.exceptions.ProjectDbFailure;

public class ProjectService {

	ProjectDAO projectDAO = null;

	public ProjectService() {
		projectDAO = new ProjectDAOImpl();
	}

	public List<Project> findAllProjects() throws ProjectDbFailure,
			EmployeeDbFailure {
		List<Project> findProjectList = projectDAO.findAllProjects();
		for (Project prj : findProjectList) {
			Employee manager = getProjectManager(prj.getManagerId());
			prj.setManager(manager);
		}
		return findProjectList;
	}

	public int insertProject(Project newProject) throws ProjectDbFailure,
			EmployeeDbFailure {
		// check whether project name is already exist or not.
		Project project = findProjectByName(newProject.getName(), false, 0);
		if (project != null) {
			throw new ProjectDbFailure(ProjectDbFailure.UNIQUE_PROJECT_NAME);
		}

		// check manager id exist in employee table or not
		EmployeeService empService = new EmployeeService();
		Employee emp = empService.findEmployeeById(newProject.getManagerId());
		if (emp == null) {
			throw new ProjectDbFailure(ProjectDbFailure.BAD_MNGR_ID);
		}

		int newProjectId = projectDAO.insertProject(newProject);
		return newProjectId;
	}

	public Project findProjectByName(String name, boolean checkNotForSamePrj,
			int projectId) throws ProjectDbFailure {
		Project findProject = projectDAO.findProjectByName(name,
				checkNotForSamePrj, projectId);
		return findProject;
	}

	public Project findProjectByProjectId(int projectId)
			throws ProjectDbFailure, EmployeeDbFailure {
		Project findProject = projectDAO.findProjectByProjectId(projectId);
		Employee manager = getProjectManager(findProject.getManagerId());
		findProject.setManager(manager);
		return findProject;
	}

	public Employee getProjectManager(int managerId) throws EmployeeDbFailure {
		EmployeeService empService = new EmployeeService();
		Employee manager = empService.findEmployeeById(managerId);
		return manager;
	}

	public boolean updateProjectById(Project project) throws ProjectDbFailure,
			EmployeeDbFailure {

		// check whether project name is already exist or not.
		Project finProject = findProjectByName(project.getName(), true,
				project.getProjectId());
		if (finProject != null) {
			throw new ProjectDbFailure(ProjectDbFailure.UNIQUE_PROJECT_NAME);
		}

		// check manager id exist in employee table or not
		EmployeeService empService = new EmployeeService();
		Employee emp = empService.findEmployeeById(project.getManagerId());
		if (emp == null) {
			throw new ProjectDbFailure(ProjectDbFailure.BAD_MNGR_ID);
		}

		boolean isUpdated = projectDAO.updateProjectById(project);
		return isUpdated;
	}

	public boolean removeProjectById(int projectId) throws ProjectDbFailure {
		boolean isDeleted = projectDAO.removeProjectById(projectId);
		return isDeleted;
	}

	public int getProjectCount() throws ProjectDbFailure {
		int prjCount = projectDAO.getProjectCount();
		return prjCount;
	}

	public List<Project> findAllProjectsContainsName(String projectName,
			String sortBy) throws ProjectDbFailure, EmployeeDbFailure {
		List<Project> findProjectList = projectDAO.findAllProjectsContainsName(
				projectName, sortBy);
		for (Project prj : findProjectList) {
			Employee manager = getProjectManager(prj.getManagerId());
			prj.setManager(manager);
		}
		return findProjectList;
	}
}