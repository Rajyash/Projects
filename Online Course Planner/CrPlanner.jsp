<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course Planner</title>
</head>
<body>
<%-- <p>You may take the following courses in</p>
<u>${quarterPlans[param.index+1].quarterName} ${quarterPlans[param.index+1].year}</u>
 --%><form action="CrPlanner" method="post">
<table border="1">

	<c:if test="${empty nextList}">
	<p>Please select the courses you have already taken:</p>
	<tr><th></th><th>Code</th><th>Title</th><th>Prerequisites</th></tr>
	
	<c:forEach items="${crList}" var="cr">
	
	<tr>
	<td><input type="checkbox" name="cp" value="${cr.c_Id}"/> </td>
	<td>${cr.c_Code }</td>
	<td>${cr.c_Name}</td>
	<td>${cr.c_PreStr}</td>
	</tr>
	</c:forEach>
	</c:if>
	<c:if test="${not empty nextList}">
	<p>You may take the following courses in
	<u>${quarterPlans[param.index+1].quarterName} ${quarterPlans[param.index+1].year}</u></p>
	
	<tr><th></th><th>Code</th><th>Title</th><th>Prerequisites</th></tr>
	
	<c:forEach items="${nextList}" var="next">
	
	<tr>
	<td><input type="checkbox" name="cp" value="${next.id}"/> </td>
	<td>${next.courseCode }</td>
	<td>${next.courseName}</td>
	<td>${next.preReqs}</td>
	</tr>
	</c:forEach>
	</c:if>
	
</table><br />
<input type="hidden" name="index" value="${param.index + 1 }"/>
<input type="submit" name="next" value="Next" />
<c:if test="${param.index != -2 }">
<input type="submit" name="finish" value="Finish" />
</c:if>
</form>
</body>
</html>