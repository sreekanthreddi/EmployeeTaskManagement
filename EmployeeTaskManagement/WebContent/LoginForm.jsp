<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	Object obj2 = request.getAttribute("Error");
	String errorMessage = null;
	if (obj2 != null) {
		errorMessage = obj2.toString();
	}

	Object obj = request.getParameter("Service");
	String serviceName = null;
	if (obj != null) {
		serviceName = obj.toString();
	}
%>
<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<title>ETMS - Login Page</title>
<link href="JQuery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="JQuery/external/jquery/jquery.js"></script>
<script src="JQuery/jquery-ui.js"></script>
<script>
	$(function() {
		$("#loginButton").button();
	});

	function cancelAction() {
		document.location.href("ETMSHome.jsp");
	}
</script>
<style>
body {
	font-size: 14px;
	margin:220px;
    background: linear-gradient( #002633,#0099cc) repeat-x;
}
table{
	width:43%;
	padding: 30px;
    text-align: left;
}
</style>
</head>
<body>
	<div align="center">
		<h2><font color="white">Employee Task Management - Login Page</font></h2>
		<form method='post' action='/EmployeeTaskManagement/Login.html'>
			<table  style="background-color:#004466">
				<tr>
					<td><font color="white" size="5">User Name:</font></td>
					<td><font color="black" size="5"><input type="text" name="userName"></font></td>
				</tr>
				<tr>
					<td><font color="white" size="5">Password:</font></td>
					<td><font color="black" size="5"><input type="password" name="password"></font></td>
				</tr>
				<tr><td></td>
					<td><button type="submit" id="loginButton">Login</button></td>
				</tr>
			</table>
		</form>
		<br />
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

		<%
			if (serviceName !=null && serviceName.equals("sessionTimeOut")) {
		%>
		<div class="ui-widget">
			<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin-right: .3em;"></span> Your Session
					timed out. Please login again!!!

				</p>
			</div>
		</div>
		<%
			}
		%>
	</div>
</body>
</html>