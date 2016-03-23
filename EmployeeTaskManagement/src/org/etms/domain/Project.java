package org.etms.domain;

public class Project {
	private int projectId;
	private String name;
	private int managerId;
	private int teamMember;
	private String description;
	private Employee manager;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the teamMember
	 */
	public int getTeamMember() {
		return teamMember;
	}

	/**
	 * @param teamMember
	 *            the teamMember to set
	 */
	public void setTeamMember(int teamMember) {
		this.teamMember = teamMember;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
}
