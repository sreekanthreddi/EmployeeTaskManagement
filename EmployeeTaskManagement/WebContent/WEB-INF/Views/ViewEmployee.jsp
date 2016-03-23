<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<link href="JQuery/jquery-ui.css" rel="stylesheet" type="text/css" />

<script src="JQuery/external/jquery/jquery.js"></script>
<script src="JQuery/jquery-ui.js"></script>
<script>
	$(function() {
		$("#button").button();
		$("#radioset").buttonset();
		$("#button").click(function() {
			var isSubmit = validateSearchForm();
			//alert(isSubmit);
			return isSubmit;
		});

		var searchByStr = 'empId';

		$("input[name=searchBy]").click(function() {
			$("#empNameDiv").css("display", "none");
			$("#empIdDiv").css("display", "none");
			$("#departmentDiv").css("display", "none");
			$("#emailDiv").css("display", "none");
			searchByStr = $(this).val();
			if ($(this).val() === 'empId') {
				$("#empIdDiv").css("display", "block");
			} else if ($(this).val() === 'empName') {
				$("#empNameDiv").css("display", "block");
			} else if ($(this).val() === 'empDept') {
				$("#departmentDiv").css("display", "block");
			} else if ($(this).val() === 'empEmail') {
				$("#emailDiv").css("display", "block");
			} else if ($(this).val() === 'allEmp') {
				$("#allEmpDiv").css("display", "block");
			}
		});

		function validateSearchForm() {
			var returnVal = true;
			//var searchByStr = $("input[name=searchBy]").val();
			//alert(searchByStr);
			if (searchByStr === 'empId') {
				var idStr = $("#employeeId").val();
				if (idStr === '') {
					returnVal = false;
					$("#empIdErr").html("*");
				}
			} else if (searchByStr === 'empName') {

			}

			return returnVal;
		}
	});
</script>
<style>
body {
	font-size: 14px;
	color:white;
	margin:230px;
    background: linear-gradient( #002633,#0099cc) repeat-x;
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
<title>ETMS - Employee Search Page</title>
</head>
<body style="background-color:#007399">
	<%@ include file="/WEB-INF/Views/ETMSHeader.jsp"%>
	<br />
	<br />
	<form method='post' action='/EmployeeTaskManagement/ViewEmployee'>
		<div id="radioset">
			<c:choose>
				<c:when test='${(searchBy == "empId") || (searchBy == null)}'>
					<input type="radio" id="empId" name="searchBy" value="empId"
						checked="checked">
					<label for="empId">Employee Id</label>
					<c:set var="divEmpIdVisibility" value="display:block" />
				</c:when>
				<c:otherwise>
					<input type="radio" id="empId" name="searchBy" value="empId">
					<label for="empId">Employee Id</label>
					<c:set var="divEmpIdVisibility" value="display:none" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test='${searchBy == "empName"}'>
					<input type="radio" id="empName" name="searchBy" value="empName"
						checked="checked">
					<label for="empName">Name</label>
					<c:set var="divEmpNameVisibility" value="display:block" />
				</c:when>
				<c:otherwise>
					<input type="radio" id="empName" name="searchBy" value="empName">
					<label for="empName">Name</label>
					<c:set var="divEmpNameVisibility" value="display:none" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test='${searchBy == "empDept"}'>
					<input type="radio" id="empDept" value="empDept" name="searchBy"
						checked="checked">
					<label for="empDept">Department</label>
					<c:set var="divEmpDeptVisibility" value="display:block" />
				</c:when>
				<c:otherwise>
					<input type="radio" id="empDept" value="empDept" name="searchBy">
					<label for="empDept">Department</label>
					<c:set var="divEmpDeptVisibility" value="display:none" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test='${searchBy == "empEmail"}'>
					<input type="radio" id="empEmail" name="searchBy" value="empEmail"
						checked="checked">
					<label for="empEmail">Email</label>
					<c:set var="divEmpEmailVisibility" value="display:block" />
				</c:when>
				<c:otherwise>
					<input type="radio" id="empEmail" name="searchBy" value="empEmail">
					<label for="empEmail">Email</label>
					<c:set var="divEmpEmailVisibility" value="display:none" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test='${searchBy == "allEmp"}'>
					<input type="radio" id="allEmp" name="searchBy" value="allEmp"
						checked="checked">
					<label for="allEmp">View All</label>
					<c:set var="divEmpAlllVisibility" value="display:block" />
				</c:when>
				<c:otherwise>
					<input type="radio" id="allEmp" name="searchBy" value="allEmp">
					<label for="allEmp">View All</label>
					<c:set var="divEmpAlllVisibility" value="display:none" />
				</c:otherwise>
			</c:choose>
		</div>
		<br />
		<div id="empIdDiv" style="${divEmpIdVisibility}">
			Employee Id: &nbsp;<input type="text" name="employeeId"
				id="employeeId" /> <span id="empIdErr" style="color: red"></span>
		</div>
		<div id="empNameDiv" style="${divEmpNameVisibility}">
			First Name:&nbsp;<input type="text" name="employeeFName"
				id="employeeFName" /> <span id="firstNameErr" style="color: red"></span>
			<br /> Last Name:&nbsp;<input type="text" name="employeeLName"
				id="employeeLName" /><span id="lastNameErr" style="color: red"></span>
		</div>
		<div id="departmentDiv" style="${divEmpDeptVisibility}">
			Department:&nbsp;<input type="text" name="departmentName"
				id="departmentName" /> <span id="departmentErr" style="color: red"></span>
		</div>
		<div id="emailDiv" style="${divEmpEmailVisibility}">
			Email:&nbsp;<input type="email" name="emailId" id="emailId" /> <span
				id="emailIdErr" style="color: red"></span>
		</div>
		<div id="allEmpDiv" style="${divEmpAlllVisibility}">Click on
			search button to view all employees.</div>
		<br />
		<button name="btnSearch" id="button" class="demoHeaders">Search</button>
	</form>
	<br />
	<c:if test="${error != null }">
		<div class="ui-widget">
			<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin-right: .3em;"></span> <strong>Error:</strong>
					${error }
				</p>
			</div>
		</div>
	</c:if>
	<br />
	<c:if test="${employeeList != null}">
		<table class="hovertable">
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Middle Name</th>
					<th>Email</th>
					<th>Birth Date</th>
					<th>Joining Date</th>
					<th>Contact No</th>
					<th>Current Address</th>
					<th>Department</th>
					<th>Designation</th>
					<th>Stock</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${employeeList}" var="curEmployee">
				<tbody>
					<tr onmouseover="this.style.backgroundColor='#A9D0F5';"
						onmouseout="this.style.backgroundColor='#CEF6EC';">
						<td>${curEmployee.getFirstName()}</td>
						<td>${curEmployee.getLastName()}</td>
						<td>${curEmployee.getMiddleName()}</td>
						<td>${curEmployee.getEmailId()}</td>
						<td>${curEmployee.getBirthDate()}</td>
						<td>${curEmployee.getJoiningDate()}</td>
						<td>${curEmployee.getContactNo()}</td>
						<td>${curEmployee.getCurrentAddress()}</td>
						<td>${curEmployee.getDepartment()}</td>
						<td>${curEmployee.getDesignation()}</td>
						<td>${curEmployee.getStock()}</td>
						<td><a
							href="/EmployeeTaskManagement/EmployeeProcess?Service=updateEmp&EmpId=${curEmployee.getEmployeeId()}">Update</a></td>
						<td><a
							href="/EmployeeTaskManagement/EmployeeProcess?Service=removeEmp&EmpId=${curEmployee.getEmployeeId()}">Remove</a></td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>