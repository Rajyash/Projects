<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Two Player Menu</title>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
$(function(){
	
});
</script>
</head>
<body>
<sec:authorize access="hasRole('ROLE_USER')">
<ul>

<li><a href="HostGame.html">Host a Game</a></li>
<li><a href="JoinGame.html">Join a Game</a></li>
</ul>
</sec:authorize>
</body>
</html>