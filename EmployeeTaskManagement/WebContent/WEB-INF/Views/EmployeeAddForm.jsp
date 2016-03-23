<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	Object obj = request.getAttribute("employeeId");
	int employeeId = 0;
	if (obj != null) {
		employeeId = Integer.parseInt(obj.toString());
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
		$("#cancelAddEmployee").button();
		$("#addEmployee").button();
		$("select[name = department]").selectmenu();
		$("select[name = designation]").selectmenu();
		$("select[name = stock]").selectmenu();
	});

	function cancelAction() {
		document.location.href("/EmployeeTaskManagement/home");
	}
</script>
<style>
body {
	font-size: 14px;
	color:white;
	margin:130px;
    background: linear-gradient( #002633,#0099cc) repeat-x;
}
</style>
<title>ETMS - Employee Add Page</title>
</head>
<body>

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

	<h2>Employee Details</h2>
	<%
		if (employeeId == 0) {
	%>
	<form method='post'
		action='/EmployeeTaskManagement/EmployeeProcess?Service=addEmp'>

		<c:set var="designationList"
			value="Trainee Engineer,Software Engineer,Hardware Engineer,Senior Software Engineer,Senior Hardware Engineer,Team Leader,Department Head"
			scope="application" />
		<table>
			<tr>
				<td>First Name:</td>
				<td><input type="text" name="firstName" required="required"></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type="text" name="lastName" required="required"></td>
			</tr>
			<tr>
				<td>Middle Name:</td>
				<td><input type="text" name="middleName"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="email" name="email" required="required"></td>
			</tr>
			<tr>
				<td>Birth Date:</td>
				<td><input type="date" name="birthDate" required="required"></td>
			</tr>
			<tr>
				<td>Joining Date:</td>
				<td><input type="date" name="joiningDate" required="required"></td>
			</tr>
			<tr>
				<td>Contact No:</td>
				<td><input type="tel" name="contactNo" required="required"></td>
			</tr>
			<tr>
				<td>Current Address:</td>
				<td><input type="text" name="currentAdd" multiple="multiple"
					required="required" height="25px"></td>
			</tr>
			<tr>
				<td>Department:</td>
				<td><select name="department" required="required">
						<c:forEach items="${departmentList}" var="curDepartment">
							<option value="${curDepartment.getDepartmentName()}">${curDepartment.getDepartmentId()} - 
								${curDepartment.getDepartmentName()}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Designation:</td>
				<td><select name="designation" required="required">
						<option selected="selected">Trainee Engineer</option>
						<option>Software Engineer</option>
						<option>Hardware Engineer</option>
						<option>Senior Software Engineer</option>
						<option>Senior Hardware Engineer</option>
						<option>Team Leader</option>
						<option>Manager</option>
						<option>Department Head</option>
				</select></td>
			</tr>
			<tr>
			<td>Stock:</td>
				<td><select name="stock" required="required">
						<option selected="selected">Select...</option>
						<option>Google $200</option>
						<option>Apple $320</option>												
				</select></td>
			</tr>
			<tr>
				<td>
					<button type="submit" id="addEmployee">Add</button>
				</td>
				<td>
					<button type="button" id="cancelAddEmployee"
						onclick="javascript:cancelAction();">Cancel</button>
				</td>
			</tr>
		</table>
	</form>

	<%
		} else {
	%>
	<div id="resultDiv">
		New Employee with employee Id <b> <%=employeeId%></b> has been added.
		<br /> Do you want to add more employee? <a id="yesLink"
			href="/EmployeeTaskManagement/EmployeeProcess?Service=addEmp">Yes</a>
		<a id="noLink" href="/EmployeeTaskManagement/home">No</a>
	</div>
	<%
		}
	%>
</body>
</html>