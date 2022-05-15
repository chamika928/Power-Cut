<%@ page import="com.PowerCut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
	<html>
		<head>
		<meta charset="ISO-8859-1">
		<title>Power Cut Management</title>
		
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery.min.js"></script>
		<script src="Components/PowerCuts.js"></script>
		
		</head>
	<body>
	
	<center>
	<h1>Power Cut Management</h1>
	</center>
	<div class="container">
		<div class="row">
			<div class="col">
				<form id="formPowerCut" name="formPowerCut" method="post" action="PowerCuts.jsp">
					Province:
					<input id="PowerCutProvince" name="PowerCutProvince" type="text"
					class="form-control form-control-sm">
					<br> City:
					<input id="PowerCutCity" name="PowerCutCity" type="text"
					class="form-control form-control-sm">
					<br> Date:
					<input id="PowerCutDate" name="PowerCutDate" type="text"
					class="form-control form-control-sm">
					<br> Time:
					<input id="PowerCutTime" name="PowerCutTime" type="text"
					class="form-control form-control-sm">
					<br>
					<input id="btnSave" name="btnSave" type="button" value="Save"
					class="btn btn-primary btn-lg">
					
					<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
					<br>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
		<br>
		<div id="divItemsGrid">
			 <%
				PowerCut powercutObj = new PowerCut(); 
				 out.print(powercutObj.readPowerCuts()); 
			 %>
		</div>
			</div>
		</div>
	</div>

	</body>
	</html>