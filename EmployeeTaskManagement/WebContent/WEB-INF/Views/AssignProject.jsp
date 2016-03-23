<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.etms.domain.EmployeeTask"%>
<%
	Object obj = request.getAttribute("Error");
	String errorMessage = null;
	if (obj != null) {
		errorMessage = obj.toString();
	}

	Object obj1 = request.getAttribute("isAssign");
	boolean isAssign = false;
	if (obj1 != null) {
		isAssign = Boolean.valueOf(obj1.toString());
	}

	Object obj2 = request.getAttribute("employeeTask");
	EmployeeTask employeeTask = null;
	if (obj2 != null) {
		employeeTask = (EmployeeTask) obj2;
	}
%>
<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<link href="JQuery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="JQuery/external/jquery/jquery.js"></script>
<script src="JQuery/jquery-ui.js"></script>
<script>
	$(function() {
		$("#cancelAssignProject").button();
		$("#assignProject").button();
		$("#assignOk").button();

		$("select[name = status]").selectmenu();
		$("select[name = category]").selectmenu();
	});

	function cancelAction() {
		document.location.href("/EmployeeTaskManagement/home");
	}
</script>
<style>
body {
	font-size: 14px;
	color:white;
	margin:120px;
    background: linear-gradient( #002633,#0099cc) repeat-x;
}
</style>
<title>ETMS - Assign Project</title>
</head>
<body style="background-color:#007399">
	<%@ include file="/WEB-INF/Views/ETMSHeader.jsp"%>

	<%
		if (errorMessage != null) {
	%>

	<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin-right: .3em;"></span> <strong>Error:</strong>
				<%=errorMessage%>
			</p>
		</div>
	</div>
	<%
		}
	%>

	<h2>Project Task Details</h2>
	<%
		if (employeeTask == null && !isAssign) {
	%>
	<form method='post' action='/EmployeeTaskManagement/AssignProject'>
		<table>
			<tr>
				<td style="vertical-align: top">Employee:</td>
				<td><select name="employeeId" size="10">
						<c:forEach items="${employeeList}" var="curEmployee">
							<option value="${curEmployee.getEmployeeId() }">
								${curEmployee.getEmployeeId()} - ${curEmployee.getFirstName()}
								${curEmployee.getLastName()}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Project:</td>
				<td><select name="projectId">
						<c:forEach items="${projectList}" var="curProject">
							<option value="${curProject.getProjectId() }">${ curProject.getName() }
							</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description:</td>
				<td><input type="text" name="description" /></td>
			</tr>
			<tr>
				<td>Start Date:</td>
				<td><input name="startDate" type="date" /></td>
			</tr>
			<tr>
				<td>Due Date:</td>
				<td><input name="dueDate" type="date" /></td>
			</tr>
			<tr>
				<td>Actual Start Date:</td>
				<td><input name="actualStartDate" type="date" /></td>
			</tr>
			<tr>
				<td>Actual End Date:</td>
				<td><input type="date" name="actualEndDate" /></td>
			</tr>
			<tr>
				<td>Status:</td>
				<td><select name="status" required="required">
						<option>New</option>
						<option>Pending</option>
						<option>In Progress</option>
						<option>Close</option>
				</select></td>
			</tr>
			<tr>
				<td>Reason for missing due date:</td>
				<td><input type="text" name="reasonOfMissingDueDate" /></td>
			</tr>
			<tr>
				<td>Category:</td>
				<td><select name="category" required="required">
						<option>Analysis</option>
						<option>Development</option>
						<option>Testing</option>
				</select></td>
			</tr>
			<tr>
				<td>
					<button type="submit" id="assignProject">Assign</button>
				</td>
				<td>
					<button type="button" id="cancelAssignProject"
						onclick="javascript:cancelAction();">Cancel</button>
				</td>
			</tr>
		</table>
	</form>
	<%
		} else if (isAssign && employeeTask != null) {
	%>
	<div id="resultDiv">
		Project
		<%=employeeTask.getProjectId()%>
		has been assign to Employee
		<%=employeeTask.getEmployeeId()%>
		is done successfully. <br />
		<button type="button" id="assignOk"
			onclick="javascript:cancelAction();">OK</button>
	</div>
	<%
		}
	%>
</body>
</html>