package org.etms.dao;

import java.util.List;

import org.etms.domain.Project;
import org.etms.exceptions.ProjectDbFailure;

public interface ProjectDAO {
	public List<Project> findAllProjects() throws ProjectDbFailure;

	public int insertProject(Project newProject) throws ProjectDbFailure;

	public boolean updateProjectById(Project project) throws ProjectDbFailure;

	public boolean removeProjectById(int projectId) throws ProjectDbFailure;

	public Project findProjectByProjectId(int projectId)
			throws ProjectDbFailure;

	public int getProjectCount() throws ProjectDbFailure;

	public Project findProjectByName(String name, boolean checkNotForSamePrj,
			int projectId) throws ProjectDbFailure;

	public List<Project> findAllProjectsContainsName(String projectName,
			String sortBy) throws ProjectDbFailure;
}
