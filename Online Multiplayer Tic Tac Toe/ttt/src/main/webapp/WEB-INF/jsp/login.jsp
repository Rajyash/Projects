<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<form:form modelAttribute="gameUser">
<table border="1">
<tr><td>Username:</td><td><form:input path="username"></form:input></td></tr>
<tr><td>Password:</td><td><form:password path="password"></form:password></td></tr>
<tr><td colspan="2" align="right"><input type="submit" name="login" value="Login"></td></tr>
</table>
</form:form>
<a href="registration.html">User Registration</a><br>
<a href="playAsGuest.html">Play as Guest User</a>
</body>
</html>