<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Course</title>
</head>
<body>
<c:if test="${empty sessionScope.user}">
<c:redirect url="Login"></c:redirect>
</c:if>
<form action="EditCourse" method="post">
	<table border="1">
	<tr><td>Code: </td><td><input type="text" name="courseCode" value="${cr.courseCode}"/></td></tr>
	<tr><td>Title: </td><td><input type="text" name="courseName" value="${cr.courseName}"/></td></tr>
	<tr><td valign="top">Prerequisite(s):</td>
	    <td>
			<c:forEach items="${preReqList}" var="edittedPreReqs">
			<c:if test="${cr.courseCode ne edittedPreReqs }">
				<c:if test="${fn:containsIgnoreCase(cr.preReqs, edittedPreReqs)}">
					<ul><li><input type="checkbox" name="e1" value="${edittedPreReqs}" checked/>${edittedPreReqs}</li></ul>
				</c:if>
				<c:if test="${not fn:containsIgnoreCase(cr.preReqs, edittedPreReqs)}">
					<ul><li><input type="checkbox" name="e1" value="${edittedPreReqs}" />${edittedPreReqs}</li></ul>
				</c:if>
			</c:if>
			</c:forEach>
	    </td>
	</tr>
	<input type="hidden" name="id" value="${param.id}" />
	
	<tr><td colspan=2><input type="submit" name="save" value="Save" /> </td></tr>
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