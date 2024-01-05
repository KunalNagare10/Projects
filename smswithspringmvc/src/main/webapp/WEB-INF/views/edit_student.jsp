<%@page import="com.jspiders.smswithspringmvc.pojo.StudentPOJO"%>
<%@page import="java.util.List"%>
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
            margin: 30px auto;
            width: 300px;
        }

        table {
            width: 50%;
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

        input[type="number"] {
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
	<div align="center">
		<form action="edit_student" method="post">
			<table>
				<tr>
					<td>Enter Id</td>
					<td><input type="number" name="id"></td>
				</tr>
			</table>
			<input type="submit" value="EDIT">
		</form>
	</div>
	<%
	@SuppressWarnings("unchecked")
	List<StudentPOJO> stuList = (List<StudentPOJO>) request.getAttribute("students");
	if (stuList != null && stuList.size() > 0) {
	%>
	<table align="center">
		<tr>
			<th>ID</th>
			<th>NAME</th>
			<th>EMAIL</th>
			<th>CONTACT</th>
			<th>AGE</th>
		</tr>
		<%
		for (StudentPOJO student : stuList) {
		%>
		<tr>
			<td><%=student.getId() %></td>
			<td><%=student.getName() %></td>
			<td><%=student.getEmail() %></td>
			<td><%=student.getMobile() %></td>
			<td><%=student.getAge() %></td>
		</tr>
		<%
		}
		%>
	</table>
	<%
	}else{
	%>
	<h1 align="center"> Students Not Found.</h1>
	<%} %>
	
	<%String message=(String)request.getAttribute("message");
	if(message!=null){
	%>
	<h1 align="center"><%=message %></h1>
	<%} %>
</body>
</html>