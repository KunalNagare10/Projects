<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
	body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
    nav {
            background-color: #333;
            overflow: hidden;
            position: fixed; /* Fixed position to keep it at the top */
            top: 0; /* Place it at the top */
            width: 100%;
            z-index: 1000; /* Set a high z-index to keep it above other elements */
    }
    nav a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 18px 20px; /* Increased padding for better touch area */
            text-decoration: none;
            transition: background-color 0.3s;
        }
     nav a:hover {
            background-color: #ddd;
            color: black;
        }
</style>
</head>
<body>
<nav>
    <a href="home_page">HOME</a>
    <a href="add_student">ADD STUDENT</a>
    <a href="delete_student">DELETE STUDENT</a>
    <a href="get_students">STUDENT LIST</a>
    <a href="order">ORDER BY AGE </a>
    <a href="edit_student">UPDATE STUDENT</a>
    <a href="search">SEARCH STUDENT</a>
    <a href="log_out">LOG OUT</a>
</nav>
</body>
</html>