<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="nav.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
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
        }

        table td {
            padding: 10px;
        }

        .inp{
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
	<div align="center">
		<form action="add_student" method="post">
			<table>
				<tr>
					<td>Name</td>
					<td><input class="inp" type="text" name="name" required="required"></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input class="inp" type="email" name="email" required="required"></td>
				</tr>
				<tr>
					<td>Mobile</td>
					<td><input class="inp" type="tel" name="mobile" minlength="10" maxlength="10"  pattern="[0-9]*"  title="Enter mobile number without country code" required="required"></td>
				</tr>
				<tr>
					<td>Age</td>
					<td><input class="inp" type="number" name="age" min="1" max="150"></td>
				</tr>
			</table>
			<input type="submit" value="ADD">
		</form>
	</div>
	<%
	String message=(String)request.getAttribute("message");
	if(message != null){
	%>
	<h1><%=message %></h1>
	<%
	}
	%>
</body>
</html>