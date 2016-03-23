<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<link href="JQuery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="JQuery/external/jquery/jquery.js"></script>
<script src="JQuery/jquery-ui.js"></script>
<script>
	$(function() {
		$("#tabs").tabs();
	});
</script>
<style>
body {
	font-size: 14px;
	color:white;
    background: linear-gradient( #002633,#0099cc) repeat-x;
}
</style>
<title>ETMS - Home Page</title>
</head>
<body style="background-color:#007399">
	<h1 align="center">Welcome to Employee Task Management System</h1>
	<br />
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Employee</a></li>
			<li><a href="#tabs-2">Project</a></li>
			<li><a href="#tabs-3">Task</a></li>
			<li><a href="#tabs-4">EmployeeTraining</a></li>
			<li><a href="#tabs-5">Stocks</a></li>
			<li><a href="#tabs-6">Benefits</a></li>
		</ul>
		<div id="tabs-1"><%@ include
				file="/WEB-INF/Views/EmployeeMenu.jsp"%></div>
		<div id="tabs-2"><%@ include
				file="/WEB-INF/Views/ProjectMenu.jsp"%></div>
		<div id="tabs-3"><%@ include
				file="/WEB-INF/Views/EmployeeTaskMenu.jsp"%></div>
		<div id="tabs-4"><%@ include
				file="/WEB-INF/Views/EmployeeTraining.jsp"%></div>
		<div id="tabs-5"><%@ include
				file="/WEB-INF/Views/EmployeeStocksMenu.jsp"%></div>		
		<div id="tabs-6"><%@ include
				file="/WEB-INF/Views/Benefits.jsp"%></div>
				
	</div>

</body>
</html>