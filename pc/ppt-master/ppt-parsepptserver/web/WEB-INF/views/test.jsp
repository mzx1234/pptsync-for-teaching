<%--
  Created by IntelliJ IDEA.
  User: zison
  Date: 2016/4/22
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>pptshow</title>
</head>
<body>
<c:forEach var="i" begin="1" end="5">
 Item <c:out value="${i}"/><p>
    </c:forEach>
</body>
</html>
