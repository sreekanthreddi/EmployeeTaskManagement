<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<link href="JQuery/jquery-ui.css" rel="stylesheet" type="text/css" />

<style>
body {
	font-size: 14px;
	margin: 10px;
	style="background-color:#007399"
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
<title>ETMS - View Stock</title>
</head>
<body>
	<%@ include file="/WEB-INF/Views/ETMSHeader.jsp"%>
	<br />
	<br />
	<form method='post' action='/EmployeeTaskManagement/EmployeeViewStockForm' ></form>
		<h3>Stock Details</h3>
		<table class="hovertable">
			<thead>
				
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Stock</th>
				</tr>
			</thead>
			<items="${stockList}" var="curEmployee">
				<tbody>
					<tr onmouseover="this.style.backgroundColor='#A9D0F5';"
						onmouseout="this.style.backgroundColor='#CEF6EC';">
						<td>${curEmployee.getFirstName()}</td>
						<td>${curEmployee.getLastName()}</td>
						<td>${curEmployee.getStock()}</td>
					</tr>
				</tbody>
			</>
		</table>
		</>
</body>
</html>