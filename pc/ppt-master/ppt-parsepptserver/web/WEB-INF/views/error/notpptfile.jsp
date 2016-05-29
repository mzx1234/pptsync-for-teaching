<%--
  Created by IntelliJ IDEA.
  User: zison
  Date: 2016/4/19
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
    <meta charset="utf-8">
  </head>
  <body>
    <form action="/upload" method="post" enctype="multipart/form-data" accept-charset="utf-8">
      <input type="file" name="file" />
      <h6 >你选择的不是ppt文件</h6>
      <input type="submit" name="Submit"/>
    </form>

  </body>
</html>
