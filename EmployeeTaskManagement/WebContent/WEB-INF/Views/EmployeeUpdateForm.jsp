<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="org.etms.domain.Employee"%>
<%
	Object obj = request.getAttribute("employee");
	Employee employee = null;
	if (obj != null) {
		employee = (Employee) obj;
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
		$("#cancelAddEmployee").button();
		$("#updateEmployee").button();
		$("#updateOk").button();
		$("select[name = department]").selectmenu();
		$("select[name = designation]").selectmenu();
		$("select[name = stock]").selectmenu();
	});

	function cancelAction() {
		document.location.href("/EmployeeTaskManagement/ViewEmployee");
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


	<h2>Employee Details</h2>
	<%
		if (employee != null && !isUpdate) {
	%>
	<form method='post'
		action='/EmployeeTaskManagement/EmployeeProcess?Service=updateEmp'>

		<c:set var="designationList"
			value="Trainee Engineer,Software Engineer,Hardware Engineer,Senior Software Engineer,Senior Hardware Engineer,Team Leader,Department Head"
			scope="application" />
		<c:set var="stockList"
			value="Google,Apple"
			scope="application" />
		<table>
			<tr>
				<td>Employee Id:</td>
				<td><input type="text" name="employeeId"
					value="<%=employee.getEmployeeId()%>" readonly="readonly"></td>
			</tr>
			<tr>
				<td>First Name:</td>
				<td><input type="text" name="firstName"
					value="<%=employee.getFirstName()%>" required="required"></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type="text" name="lastName"
					value="<%=employee.getLastName()%>" required="required"></td>
			</tr>
			<tr>
				<td>Middle Name:</td>
				<td><input type="text" name="middleName"
					value="<%=employee.getMiddleName()%>"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="email" name="email"
					value="<%=employee.getEmailId()%>" required="required"></td>
			</tr>
			<tr>
				<td>Birth Date:</td>
				<td><fmt:formatDate value="<%=employee.getBirthDate()%>"
						pattern="MM/dd/yyyy" var="formatedBirthDate" /> <input
					type="date" name="birthDate" value="${formatedBirthDate}"
					required="required"></td>
			</tr>
			<tr>
				<td>Joining Date:</td>
				<td><fmt:formatDate value="<%=employee.getJoiningDate()%>"
						pattern="MM/dd/yyyy" var="formatedJoiningDate" /> <input
					type="date" name="joiningDate" value="${formatedJoiningDate}"
					required="required"></td>
			</tr>
			<tr>
				<td>Contact No:</td>
				<td><input type="tel" name="contactNo"
					value="<%=employee.getContactNo()%>" required="required"></td>
			</tr>
			<tr>
				<td>Current Address:</td>
				<td><input type="text" name="currentAdd" multiple="multiple"
					required="required" value="<%=employee.getCurrentAddress()%>"></td>
			</tr>
			<tr>
				<td>Department:</td>
				<td><c:set value="<%=employee.getDepartment()%>"
						var="paramDeptName" /> <select id="department" name="department">
						<c:forEach items="${departmentList}" var="curDepartment">
							<option value="${curDepartment.getDepartmentName()}"
								${curDepartment.getDepartmentName() == paramDeptName ? 'selected' : ''}>${curDepartment.getDepartmentId()}
								- ${curDepartment.getDepartmentName()}</option>
						</c:forEach>

				</select></td>
			</tr>
			<tr>
				<td>Designation:</td>
				<td><c:set value="<%=employee.getDesignation()%>"
						var="paramDesignation" /> <select id="designation"
					name="designation">
						<c:forEach items="${fn:split(designationList, ',')}"
							var="designationName">
							<option value="${designationName}"
								${designationName == paramDesignation ? 'selected' : ''}>${designationName}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>stock:</td>
				<td><c:set value="<%=employee.getStock()%>"
						var="paramStock" /> <select id="stock"
					name="stock">
						<c:forEach items="${fn:split(stockList, ',')}"
							var="stockName">
							<option value="${stockName}"
								${stocknName == paramStock ? 'selected' : ''}>${stockName}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>
					<button type="submit" id="updateEmployee">Update</button>
				</td>
				<td>
					<button type="button" id="cancelAddEmployee"
						onclick="javascript:cancelAction();">Cancel</button>
				</td>
			</tr>
		</table>
	</form>

	<%
		} else if (isUpdate) {
	%>
	<div id="resultDiv">
		Employee details updated successfully. <br />
		<button type="button" id="updateOk"
			onclick="javascript:cancelAction();">OK</button>
	</div>
	<%
		}
	%>
</body>
</html>