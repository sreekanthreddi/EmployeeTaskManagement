<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<link href="JQuery/jquery-ui.css" rel="stylesheet" type="text/css" />
<title>ETMS - Project Search Page</title>
<script src="JQuery/external/jquery/jquery.js"></script>
<script src="JQuery/jquery-ui.js"></script>
<script>
	$(function() {
		$("#searchProject").button();
	});
</script>
<style>
body {
	font-size: 14px;
	margin: 10px;
}

table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}

table.hovertable th {
	background-color: #FAAC58;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable tr {
	background-color: #CEF6EC;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
</style>

</head>
<body style="background-color:#007399">
	<%@ include file="/WEB-INF/Views/ETMSHeader.jsp"%>
	<br />
	<br />
	<form method='post' action='/EmployeeTaskManagement/ViewProject'>
		Project Name:&nbsp;<input type="text" name="projectName" /> (not
		exact match) <br /> Sort By: <input type="radio" id="prjName"
			name="sortBy" value="prjName" checked="checked"> <label
			for="prjName">Project Name</label> <input type="radio" id="teamSize"
			name="sortBy" value="teamSize"> <label for="teamSize">Team
			Size</label> <br /> <br />
		<button type="submit" id="searchProject">Search</button>
	</form>
	<br />
	<c:if test="${projectList != null}">
		<table class="hovertable">
			<thead>
				<tr>
					<th>Project Name</th>
					<th>Description</th>
					<th>Manager</th>
					<th>Team Member</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${projectList}" var="curProject">
				<tbody>
					<tr onmouseover="this.style.backgroundColor='#A9D0F5';"
						onmouseout="this.style.backgroundColor='#CEF6EC';">
						<td>${curProject.getName()}</td>
						<td>${curProject.getDescription()}</td>
						<td><c:set value="${curProject.getManager()}"
								var="prjManager" /> ${prjManager.getFirstName()} &nbsp;
							${prjManager.getLastName()}</td>
						<td>${curProject.getTeamMember()}</td>
						<td><a
							href="/EmployeeTaskManagement/ProjectProcess?Service=updatePrj&PrjId=${curProject.getProjectId()}">Update</a></td>
						<td><a
							href="/EmployeeTaskManagement/ProjectProcess?Service=removePrj&PrjId=${curProject.getProjectId()}">Remove</a></td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>