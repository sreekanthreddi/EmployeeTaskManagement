<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="org.etms.domain.Project"%>
<%
	Object obj = request.getAttribute("project");
	Project project = null;
	if (obj != null) {
		project = (Project) obj;
	}

	Object obj2 = request.getAttribute("isUpdate");
	boolean isUpdate = false;
	if (obj2 != null) {
		isUpdate = Boolean.valueOf(obj2.toString());
	}

	Object obj3 = request.getAttribute("Error");
	String errorMessage = null;
	if (obj3 != null) {
		errorMessage = obj3.toString();
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
		$("#cancelUpdateProject").button();
		$("#updateProject").button();
		$("#updateOk").button();
	});

	function cancelAction() {
		document.location.href("/EmployeeTaskManagement/ViewProject");
	}
</script>
<style>
body {
	font-size: 14px;
	margin: 10px;
}
</style>
<title>ETMS - Employee Update Page</title>
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

	<h2>Project Details</h2>
	<%
		if (project != null && !isUpdate) {
	%>
	<form method='post'
		action='/EmployeeTaskManagement/ProjectProcess?Service=updatePrj'>
		<table>
			<tr>
				<td>Project Id:</td>
				<td><input type="text" name="projectId"
					value="<%=project.getProjectId()%>" readonly="readonly"></td>
			</tr>
			<tr>
				<td>Project Name:</td>
				<td><input type="text" name="projectName" required="required"
					value="<%=project.getName()%>"></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><input type="text" name="prjDescription"
					required="required" value="<%=project.getDescription()%>"></td>
			</tr>
			<tr>
				<td style="vertical-align: top;">Manager Name:</td>
				<td><c:set value="<%=project.getManagerId()%>"
						var="paramManager" /> <select id="managerList" name="managerList"
					size="15">
						<c:forEach items="${managerList}" var="manager">
							<option value="${manager.getEmployeeId()}"
								${manager.getEmployeeId() == paramManager ? 'selected' : ''}>${manager.getEmployeeId()}
								- ${manager.getFirstName()} ${manager.getLastName()}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Team Size:</td>
				<td><input type="text" name="teamMember"
					value="<%=project.getTeamMember() %>" readonly="readonly"></td>
			</tr>
			<tr>
				<td>
					<button type="submit" id="updateProject">Update</button>
				</td>
				<td>
					<button type="button" id="cancelUpdateProject"
						onclick="javascript:cancelAction();">Cancel</button>
				</td>
			</tr>
		</table>
	</form>

	<%
		} else if (isUpdate) {
	%>
	<div id="resultDiv">
		Project details updated successfully. <br />
		<button type="button" id="updateOk"
			onclick="javascript:cancelAction();">OK</button>
	</div>
	<%
		}
	%>
</body>
</html>