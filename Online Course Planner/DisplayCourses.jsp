<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display Courses</title>
</head>
<body>
<table border="1">
	<th>Code</th><th>Title</th><th>Prerequisites</th><th>Operation</th>
	<c:forEach items="${courseObjList}" var="courses">
	<tr>
	<td>${courses.courseCode }</td>
	<td>${courses.courseName}</td>
	<td>${courses.preReqs}</td>
	<c:choose>
	<c:when test="${not empty sessionScope.user}">
	<td><a href="EditCourse?id=${courses.id}">Edit</a></td>
	</c:when>
	<c:otherwise>
	<td><a href="Login">Edit</a></td>
	</c:otherwise>
	</c:choose>
	</tr>
	</c:forEach>
</table>
<c:choose>
<c:when test="${not empty sessionScope.user}">
	<p><a href="AddCourse">Add Course</a></p>
	<p><a href="Logout">Logout</a></p>
</c:when>
<c:otherwise>
	<p><a href="Login">Add Course</a></p>
	<p><a href="Login">Login</a></p>
</c:otherwise>
</c:choose>
<p><a href="CrPlanner">Course Planner</a></p>
	
</body>
</html>