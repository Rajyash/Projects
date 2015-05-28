<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Login Page</title>

</head>
<body onload='document.f.j_username.focus();'>
<h3>Login</h3>
<c:if test="${not empty error}">
<div class="errorblock">
	${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
</div>
</c:if>
<form name='f' action="<c:url value='j_spring_security_check' />"
	method='POST'>
	<table>
	<tr>
	<td>Username:</td><td><input type='text' name='j_username' value=''></td>
	</tr>
	<tr>
	<td>Password:</td><td><input type='password' name='j_password' /></td>
	</tr>
	<tr>
	<td colspan='2'><input name="submit" type="submit" value="submit" /></td>
	</tr>
	</table>
</form>
<a href="registration.html">User Registration</a><br>
<a href="playAsGuest.html">Play as Guest User</a>
</body>
</html>