<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	Object obj = request.getAttribute("projectId");
	int projectId = 0;
	if (obj != null) {
		projectId = Integer.parseInt(obj.toString());
	}

	Object obj2 = request.getAttribute("Error");
	String errorMessage = null;
	if (obj2 != null) {
		errorMessage = obj2.toString();
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
		$("#addProject").button();
		$("#cancelAddProject").button();
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
<title>ETMS - Project Add Page</title>
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
		if (projectId == 0) {
	%>
	<form method='post'
		action='/EmployeeTaskManagement/ProjectProcess?Service=addPrj'>
		<table>
			<tr>
				<td>Project Name:</td>
				<td><input type="text" name="projectName" required="required"></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><input type="text" name="prjDescription"
					required="required"></td>
			</tr>
			<tr>
				<td style="vertical-align: top;">Manager Name:</td>
				<td><select id="managerList" name="managerList" size="15">
						<c:forEach items="${managerList}" var="manager">
							<option value="${manager.getEmployeeId()}">${manager.getEmployeeId()}
								- ${manager.getFirstName()} ${manager.getLastName()}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Team Size:</td>
				<td><input type="text" readonly="readonly" name="teamMember"
					value="1" /></td>
			</tr>
			<tr>
				<td>
					<button type="submit" id="addProject">Add</button>
				</td>
				<td>
					<button type="button" id="cancelAddProject"
						onclick="javascript:cancelAction();">Cancel</button>
				</td>
			</tr>

		</table>
	</form>
	<%
		} else {
	%>
	<div id="resultDiv">
		New Project with project Id <b> <%=projectId%></b> has been added. <br />
		Do you want to add more project? <a id="yesLink"
			href="/EmployeeTaskManagement/ProjectProcess?Service=addPrj">Yes</a>
		<a id="noLink" href="/EmployeeTaskManagement/home">No</a>
	</div>
	<%
		}
	%>
</body>
</html>