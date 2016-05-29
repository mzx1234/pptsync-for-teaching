<%--
  Created by IntelliJ IDEA.
  User: zison
  Date: 2016/5/6
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="initial-scale = 1.0,maximum-scale = 1.0" />

  <title>功能强大的HTML5滑块幻灯片 - 站长素材</title>

  <script type="text/javascript" src="/resources/js/jquery-1.11.0.min.js"></script>

  <!-- for Raxus Slider -->
  <link rel="stylesheet" href="/resources/css/raxus.css" media="screen" type="text/css">
  <script type="text/javascript" src="/resources/js/raxus-slider.min.js"></script>
  <!-- for Raxus Slider #end -->


  <!-- for documentation: you don't need them -->
  <link rel="stylesheet" href="/resources/css/bootstrap.min.css" media="screen" type="text/css">
  <link rel="stylesheet" href="/resources/documentation/css/documentation.css" media="screen" type="text/css">
  <script type="text/javascript" src="/resources/documentation/js/document.js"></script>
  <script src="/resources/documentation/js/highlight.pack.js"></script>
  <script>hljs.initHighlightingOnLoad();</script>
  <!-- for documentation #end: you don't need them -->

  <style>
    #mySlider {
      width: 80%;
      height: 450px;
      margin: auto; /* for center alignment */
    }
    #mySlider .mini-images li {
      width: 100px;
      height: 50px;
    }

    /* for tablet */
    @media screen and (max-width: 980px) {
      #mySlider {
        width: 100%;
        height: 450px;
      }
    }
    /* for mobile */
    @media screen and (max-width: 640px) {
      #mySlider {
        width: 100%;
        height: 250px;
      }
    }

    .top {
      height: 5%;
      width: 100%;
      background-color: #adadad;
      position: absolute;
    }
  </style>

</head>
<body>
<div class="top">


  <section class="welcome">
    <div class="container">

      <div class="row">
        <div class="col-md-12 a">

          <div id="mySlider" class="raxus-slider" data-autoplay="3000" data-arrows="show" data-fullscreen="show" data-dots="show" data-keypress="true" data-thumbnail="bottom">
            <ul class="slider-relative" id="relative">
              <c:forEach var="i" begin="0" end="${pageNum - 1}">

                <li class="slide" >
                  <img src="/pptpage?index=${i}&fileName=${fileName}"  alt=""  onclick=myFunction(${fileName},${i})/>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>
</body>
</html>

