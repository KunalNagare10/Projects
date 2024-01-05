<%@page import="com.jspiders.smswithspringmvc.pojo.StudentPOJO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="nav.jsp"></jsp:include>
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
            padding: 20px;
        }

        div {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin: 50px auto;
            width: 300px;
        }

        table {
            width: 100%;
            margin-bottom: 15px;
            border-collapse: collapse;
        }

        th {
            background-color: #4caf50; /* Background color for th */
            color: white;
            padding: 10px;
            text-align: left;
        }

        td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        input[type="number"],
        input[type="text"],
        input[type="email"],
        input[type="tel"] {
            width: 100%;
            padding: 8px;
            font-size: 16px;
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
	<%
	StudentPOJO student = (StudentPOJO) request.getAttribute("student");
	if (student != null) {
	%>
	<div align="center">
		<form action="update_student" method="post">
			<table>
				<tr>
				<td>Id</td>
                <td><input type="number" value="<%=student.getId()%>" readonly="true" name="id"></td>
				</tr>
				<tr>
					<td>Name</td>
					<td><input class="inp" type="text" value="<%=student.getName()%>" name="name" required="required"></td>
				</tr>
				<tr>
					<td>Email</td>
					<td> <input type="email" value="<%=student.getEmail()%>" name="email"> </td>
				</tr>
				<tr>
					<td>Mobile</td>
					<td><input class="inp" type="tel" value="<%=student.getMobile()%>" name="mobile" minlength="10" maxlength="10"  pattern="[0-9]*"  title="Enter mobile number without country code" required="required"></td>
				</tr>
				<tr>
					<td>Age</td>
					<td><input class="inp" type="number" value="<%=student.getAge()%>" name="age" min="1" max="150"></td>
				</tr>
			</table>
			<input type="submit" value="UPDATE">
		</form>
	</div>
	<%
	} else {
	%>
	<h1 align="center">Student Not Found.</h1>
	<%
	}
	%>
</body>
</html>