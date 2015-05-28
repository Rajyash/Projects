<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Course</title>
</head>
<body>
<c:if test="${empty sessionScope.user}">
<c:redirect url="Login"></c:redirect>
</c:if>
<form action="AddCourse" method="post">
<table border="1">
	<tr><td>Code: </td><td><input type="text" name="courseCode" /></td></tr>
	<tr><td>Title: </td><td><input type="text" name="courseName" /></td></tr>
	<tr><td valign="top">Prerequisite(s):</td>
	    <td>
			<c:forEach items="${preReqList}" var="preReqList">
			<ul><li><input type="checkbox" name="addedPreReqs" value="${preReqList}"/>${preReqList}</li></ul>
			</c:forEach>
	    </td>
	</tr>
	<tr><td colspan=2><input type="submit" name="add" value="Add" /> </td></tr>
</table>
	<c:choose>
	<c:when test="${not empty sessionScope.user}">
	<p><a href="Logout">Logout</a></p>
	</c:when>
	<c:otherwise>
	<p><a href="Login">Login</a></p>
	</c:otherwise>
	</c:choose>
</form>
</body>
</html>