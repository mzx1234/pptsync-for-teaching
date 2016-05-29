<%--
  Created by IntelliJ IDEA.
  User: zison
  Date: 2016/5/6
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>jQuery幻灯片插件Slippry - 站长素材</title>

  <link rel="stylesheet" type="text/css" href="/resources/css/default.css">
  <link rel="stylesheet" href="/resources/css/slippry.css">
  <style type="text/css">
    *{padding: 0;margin:0;}
    ul{list-style: none;}
    .thumb-box {
      padding: 1.4em 0 1em;
      margin-left: -1%;
      width: 102%;}
    .thumb-box .thumbs {
      overflow: hidden;
      *zoom: 1;
    }
    .thumb-box .thumbs li {
      float: left;
      width: 25%;
      text-align: center;
      padding: 0 1%;}
    .thumb-box .thumbs li img {
      width: 100%;
      opacity: .8;
      -moz-transition: opacity 0.32s;
      -o-transition: opacity 0.32s;
      -webkit-transition: opacity 0.32s;
      transition: opacity 0.32s;
      border-bottom: 4px solid transparent;
    }
    .thumb-box .thumbs li img.active {
      border-color: #31ACE2;
      opacity: 1;
    }
    .thumb-box .thumbs li:hover img {
      opacity: 1;
      -moz-transition: opacity 0.2s;
      -o-transition: opacity 0.2s;
      -webkit-transition: opacity 0.2s;
      transition: opacity 0.2s;
    }
  </style>

</head>
<body>
<div class="htmleaf-content">
  <ul id="thumbnails">

    <c:forEach var="i" begin="0" end="${pageNum - 1}">

      <li class="#slide${i}" >
        <img src="/pptpage?index=${i}&fileName=${fileName}"  alt=""  onclick=myFunction(${fileName},${i})>
      </li>
    </c:forEach>

  </ul>
  <div class="thumb-box">
    <ul class="thumbs">

      <c:forEach var="i" begin="0" end="${pageNum - 1}">

        <li href="#${i}" data-slide="${i}">
          <a>
            <img src="/pptpage?index=${i}&fileName=${fileName}"  alt=""  onclick=myFunction(${fileName},${i})>
          </a>
        </li>
      </c:forEach>



    </ul>
  </div>
</div>

<script src="/resources/js/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="/resources/js/slippry.min.js"></script>
<script>
  var thumbs = jQuery('#thumbnails').slippry({
    // general elements & wrapper
    slippryWrapper: '<div class="slippry_box thumbnails" />',
    // options
    transition: 'horizontal',
    pager: false,
    auto: false,
    onSlideBefore: function (el, index_old, index_new) {
      jQuery('.thumbs a img').removeClass('active');
      jQuery('img', jQuery('.thumbs a')[index_new]).addClass('active');
    }
  });

  jQuery('.thumbs a').click(function () {
    thumbs.goToSlide($(this).data('slide'));
    return false;
  });
</script>

</body>
</html>
