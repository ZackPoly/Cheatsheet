<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="title"/></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-blue-grey.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
html, body, h1, h2, h3, h4, h5 {font-family: "Open Sans", sans-serif}
</style>
</head>
<body class="w3-theme-l5">

<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
  <a href="${pageContext.request.contextPath}/" class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i><spring:message code="title"/></a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-globe"></i></a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Account Settings"><i class="fa fa-user"></i></a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Messages"><i class="fa fa-envelope"></i></a>
 </div>
</div>

<!-- Navbar on small screens -->
<div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 1</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 2</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 3</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">My Profile</a>
</div>

<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
  <!-- The Grid -->
  <div class="w3-row">
    <!-- Left Column -->
    <div class="w3-col m3">

      <!-- Profile -->
      <div class="w3-card w3-round w3-white">
        <div class="w3-container">
            <h4 class="w3-center"><spring:message code="home.upload"/></h4>
            <hr>
            <form action="./upload" method="post" enctype="multipart/form-data" enctype="multipart/form-data">
                <p><input id="uploader" type="file" name="file"/></p>
                <p><input type="submit" value="Upload File" /></p>
            </form>
        </div>
      </div>
      <br>

      <div class="w3-card w3-round w3-white">
        <div class="w3-container">
          <h4><spring:message code="home.topics"/></h5>
          <br>
          <c:forEach var="topic" items="${topics}" varStatus="loop">
          <p style="line-height: 1.2;"><strong>${topic.title}</strong></p>
          <p><spring:message code="question.requests"/>${topic.requestsNum}</p>
          <br>
          </c:forEach>
        </div>
      </div>
      <br>

    <!-- End Left Column -->
    </div>

    <!-- Middle Column -->
    <div class="w3-col m7">

      <div class="w3-row-padding">
        <div class="w3-col m12">
          <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
              <form role="form" method="GET" action="./search">
                <h6 class="w3-opacity"><spring:message code="home.question"/></h6>
                  <p>
                  <input style="width:80%" contenteditable="true" class="w3-border w3-padding" type="text" id="question" name="question" placeholder="<spring:message code="home.placeholder"/>">
                  </p>
                <button type="submit" class="w3-button w3-theme"><i class="fa fa-pencil"></i>  <spring:message code="navbar.search"/></button>
              </form>
            </div>
          </div>
        </div>
      </div>

      <c:if test="${not empty question}">
        <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
          <!--<img src="resources/img/huh.gif" alt="huh" class="w3-left w3-circle w3-margin-right" style="width:60px">-->
          <span class="w3-right w3-opacity"><spring:message code="question.requests"/>${question.requestsNum}</span>
          <h4>${question.body}</h4><br>
          <hr class="w3-clear">
          <p><strong>${question.answer}</p>
        </div>
      </c:if>

      <!--
      <c:if test="${empty question}">
        <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
          <img src="resources/img/gojo-backshots.gif">
        </div>
      </c:if>
      -->

    <!-- End Middle Column -->
    </div>

    <!-- Right Column -->
    <div class="w3-col m2">
      <!--
      <div class="w3-card w3-round w3-white w3-center">
        <div class="w3-container">
          <h4><spring:message code="home.topics"/></h5>
          <br>
          <c:forEach var="topic" items="${topics}" varStatus="loop">
            <p>${topic.title}</p>
          </c:forEach>
        </div>
      </div>
      <br>
      -->

      <div class="w3-card w3-round w3-white w3-padding-32 w3-center">
        <spring:message code="home.eraser"/>
        <p><a href="${pageContext.request.contextPath}/reset"> <img src="resources/img/erazer.jpg" alt="ERAZER" style="width:50%;"/> </a></p>
      </div>

    <!-- End Right Column -->
    </div>

  <!-- End Grid -->
  </div>

<!-- End Page Container -->
</div>
<br>

<!-- Footer -->
<footer class="w3-container w3-theme-d3 w3-padding-16">
  <h5>Footer</h5>
</footer>

<footer class="w3-container w3-theme-d5">
  <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
</footer>

<script>
// Accordion
function myFunction(id) {
  var x = document.getElementById(id);
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
    x.previousElementSibling.className += " w3-theme-d1";
  } else {
    x.className = x.className.replace("w3-show", "");
    x.previousElementSibling.className =
    x.previousElementSibling.className.replace(" w3-theme-d1", "");
  }
}

// Used to toggle the menu on smaller screens when clicking on the menu button
function openNav() {
  var x = document.getElementById("navDemo");
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
  } else {
    x.className = x.className.replace(" w3-show", "");
  }
}
</script>

</body>
</html>
