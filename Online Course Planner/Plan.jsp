<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Planner</title>
</head>
<body>
<c:if test="${coursesLeft}">
 <p>Here is your course plan:</p>
    <c:if test="${ sessionScope.quarter_subject.size()-2 >= 0}">
<c:forEach begin="0" end="${sessionScope.quarter_subject.size()-2}" var="i">
    
    <c:forEach var="quart_sub" items="${sessionScope.quarter_subject}">
    
    <c:if test="${quart_sub.key == i+1 && quart_sub.value.size() > 0}">
    <u>${quarterPlans[i].quarterName}
    ${quarterPlans[i].year}</u>
    <table border="1">
    <tr>
    <th>Code</th>
    <th>Title</th>
    <th>Prerequisite</th>
    </tr>
    
    <c:forEach var="qt_sub" items="${quart_sub.value}">
    
    <tr>
    <td>${crList[qt_sub].c_Code }</td>
    <td>${crList[qt_sub].c_Name }</td>
    <td>${crList[qt_sub].c_PreStr }</td>
    </tr>
    </c:forEach>
    </table>
    </c:if>
    
</c:forEach>
   
    
 
</c:forEach>
</c:if>
</c:if>
<a href="Done">Done</a>
</body>
</html>