<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
}

#main {
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	padding: 20px;
	margin: 50px auto;
	width: 300px;
}

#child {
	margin-top:10px;
}

table {
	width: 100%;
	margin-bottom: 15px;
}

table td {
	padding: 10px;
}

.inp {
	width: 100%;
	padding: 8px;
	box-sizing: border-box;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

input[type="submit"] {
	background-color: #4caf50;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #45a049;
}

h1 {
	color: #4caf50;
	text-align: center;
	margin-top: 20px;
}
</style>
</head>
<body>
	<div id="main" align="center">
		<form action="log_in" method="post">
			<table>
				<tr>
					<td>Email</td>
					<td><input class="inp" type="text" name="email"
						required="required"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input class="inp" type="text" name="password"
						required="required"></td>
				</tr>
			</table>
			<input type="submit" value="LOG IN">
		</form>
		<div id="child" align="center">
			<a href="sign_up"> New user? Create account</a>
		</div>
	</div>

	<%
	String message = (String) request.getAttribute("message");
	if (message != null) {
	%>
	<div align="center">
		<h1><%=message%></h1>
	</div>
	<%
	}
	%>

</body>
</html>