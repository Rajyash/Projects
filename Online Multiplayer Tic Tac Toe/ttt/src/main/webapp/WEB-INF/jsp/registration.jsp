<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration</title>
</head>
<body>

<form:form modelAttribute="gameUser">
<table border="1">
<tr><td>Username:</td><td><form:input path="username"></form:input></td></tr>
<tr><td>Password:</td><td><form:password path="password"></form:password></td></tr>
<tr><td>Email:</td><td><form:input path="email"></form:input></td></tr>
<tr><td colspan="2" align="right"><input type="submit" name="register" value="Register" /></td></tr>
</table>
</form:form>

</body>
</html>