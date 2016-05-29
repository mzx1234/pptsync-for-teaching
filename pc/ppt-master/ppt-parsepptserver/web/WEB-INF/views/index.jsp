<%--
  Created by IntelliJ IDEA.
  User: zison
  Date: 2016/4/19
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="initial-scale = 1.0,maximum-scale = 1.0" />

  <title>课堂ppt同步播放</title>

  <script type="text/javascript" src="/resources/js/jquery-1.11.0.min.js"></script>

  <!-- for Raxus Slider -->
  <link rel="stylesheet" href="/resources/css/raxus.css" media="screen" type="text/css">
  <script type="text/javascript" src="/resources/js/raxus-slider.min.js"></script>
  <!-- for Raxus Slider #end -->

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


  <script  language=javascript>
    function myFunction(fileNme,index)
    {a
        var request = new XMLHttpRequest();
        request.open("GET","http://localhost:8086/switch?index="+index+"&fileName="+fileName);
        request.send();

    }
  </script>


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
