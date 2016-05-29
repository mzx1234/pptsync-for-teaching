<%--
  Created by IntelliJ IDEA.
  User: zison
  Date: 2016/5/9
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>课堂ppt同步播放</title>

  <link rel="stylesheet" type="text/css" href="/resources/css/pptshow.css" />
  <script src="/resources/js/jquery-2.1.1.min.js"></script>
  <script src="/resources/js/pptshow.js"></script>


</head>
<body>
<div class="top">
  <div class="top_spans">
    <span id="broacast">[广播IP]</span>
    <span>|</span>
    <span id="choose">[选择文件]</span>
  </div>
</div>
<div class="content">
  <div class="pptimg">
    <span class="fullscreen" style=""></span>
    <img id="slide" src="/resources/img/image-1.jpg">
  </div>
</div>

<div class="left">
  <ul class="left_ul">

  </ul>
</div>

<!--隐藏模块-->
<div class="chooseFile">
  <div class="uploadFile">
    <form action="/upload" method="post" enctype="multipart/form-data" accept-charset="utf-8">
      <input class="pptfile" type="file" name="file" />
      <br><br><br><br><br><br>
      <input type="submit" name="Submit"/>
      <button type="button" class="cancle">取消</button>
    </form>
  </div>
</div>

<!--隐藏模块-->
<div class="broacastIP">
  <div class="sendIP">
    <span>选择IP:</span>
    <select id="ips"></select>
    <br><br><br><br><br><br>

    <button class="confirm">确定</button>
    <button class="cancle">取消</button>
  </div>
</div>
</div>
</body>
</html>

