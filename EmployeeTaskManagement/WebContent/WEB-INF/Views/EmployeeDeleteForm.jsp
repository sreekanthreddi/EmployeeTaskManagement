<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	Object obj = request.getAttribute("employeeId");
	int employeeId = 0;
	if (obj != null) {
		employeeId = Integer.parseInt(obj.toString());
	}

	Object obj2 = request.getAttribute("isDelete");
	boolean isDelete = false;
	if (obj2 != null) {
		isDelete = Boolean.valueOf(obj2.toString());
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
<title>ETMS - Employee Remove Page</title>
<script>
	$(function() {
		$("#btnYes").button();
		$("#btnNo").button();
		$("#tryAgain").button();
		$("#deleteOk").button();
	});

	function cancelAction() {
		document.location.href("/EmployeeTaskManagement/ViewEmployee");
	}
</script>
</head>
<body style="background-color:#007399">


	<%@ include file="/WEB-INF/Views/ETMSHeader.jsp"%>
	<br />
	<br />

	<%
		if (errorMessage != null) {
	%>

	<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin-right: .3em;"></span> <strong>Error:</strong>
				Error occurred while deleting employee.
				<%=errorMessage%>
			</p>
		</div>
	</div>
	<button type="button" id="tryAgain"
		onclick="javascript:cancelAction();">Try Again!</button>
	<%
		} else if (isDelete) {
	%>

	<div id="resultDiv">
		Employee removed successfully. <br />
		<button type="button" id="deleteOk"
			onclick="javascript:cancelAction();">OK</button>
	</div>
	<%
		} else {
	%>
	<form method='post'
		action='/EmployeeTaskManagement/EmployeeProcess?Service=removeEmp&EmpId=
		<%=employeeId%>'>
		<div>
			Are you sure you want to remove employee with id
			<%=employeeId%>
			? <br />
			<button id="btnYes" type="submit">Yes</button>
			<button id="btnNo" type="button" onclick="javascript:cancelAction();">No</button>
		</div>
	</form>
	<%
		}
	%>
</body>
</html>