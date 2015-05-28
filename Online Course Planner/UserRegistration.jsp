<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration</title>
</head>
<body>

<c:if test="${not empty errors}">
<c:forEach items="${errors}" var="err">
<p>${err}</p>
</c:forEach>
</c:if>
<form action="UserRegistration" method="post">

	<table border="1">
	<tr><td>UserName: </td><td><input type="text" name="username"></td></tr>
	<tr><td>Password: </td><td><input type="password" name="password"></td></tr>
	<tr><td>Re-type Password: </td><td><input type="password" name="reTypePassword"></td></tr>
	<tr><td>First Name (Optional): </td><td><input type="text" name="firstName"></td></tr>
	<tr><td>Last Name (Optional): </td><td><input type="text" name="lastName"></td></tr>
	</table>
	<br /><input type="submit" name="register" value="Register" />
</form>
</body>
</html>