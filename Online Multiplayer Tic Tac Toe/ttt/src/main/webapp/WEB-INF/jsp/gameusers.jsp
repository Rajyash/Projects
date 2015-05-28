<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Game Users</title></head>
<body>
<table>
<tr><th>ID</th><th>Username</th><th>Email</th></tr>
<c:forEach items="${gameusers}" var="game_user">
<tr>
  <td>${game_user.userId}</td>
  <td>${game_user.username}</td>
  <td>${game_user.email}</td>
</tr>
</c:forEach>
</table>
</body>
</html>