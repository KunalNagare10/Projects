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
<h1 align="center">Welcome to Student Management</h1>
	<div align="center">
		<form action="sign_up" method="post">
			<table>
				<tr>
					<td>Username</td>
					<td> <input name="username" autofocus="autofocus" class="inp" type="text"  required="required"> </td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input class="inp" type="email" name="email" required="required"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input class="inp" type="password" name="password" required="required"></td>
				</tr>
			</table>
			<input type="submit" value="Sign Up">
		</form>
	</div>
</body>
</html>