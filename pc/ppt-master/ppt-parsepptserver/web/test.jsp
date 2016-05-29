<%--
  Created by IntelliJ IDEA.
  User: zison
  Date: 2016/4/22
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>jsp test</title>
</head>

  <%!
    public int count=0;
    public String show() {
      return "mozhixun";
    }
  %>
<body>
  <%
    out.println(count++);
    out.flush();
  %>
<br>
  <%
    out.println(show());
    out.flush();
  %>

  <%
    for (int i=0;i<5;i++) {
  %>
  <h1>nihaolihao</h1>
  <%
    }
  %>



  </c:forEach>



</body>
</html>
