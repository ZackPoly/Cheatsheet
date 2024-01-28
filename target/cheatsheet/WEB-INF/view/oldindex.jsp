<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="resources/img/gojo-backshots.gif">
    <title><spring:message code="title"/></title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
    <link href='http://fonts.googleapis.com/css?family=Jura&subset=latin,greek' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Comfortaa&subset=latin,greek' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Load CSS -->
    <link rel="stylesheet" href="resources/css/main.css"/>

    <!-- jQueryUI Calendar-->
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<div class="container-fluid" style="width: 95%;">
<!--
<div>
    <div class="img-responsive">
        <img width="100%" src="resources/img/gojo-backshots.gif">
    </div>
</div>
-->
<div class="row">
    <h3>File Upload:</h3>
    <form action="./upload" method = "post" enctype = "multipart/form-data" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <br />
        <input type = "submit" value = "Upload File" />
    </form>
</div>
<div class="row" id="wrapper">
<div class="col-md-4" id="div1">
<div class="jumbotron" style="padding: 30px 30px 30px 30px;">
    <form role="form" method="GET" action="./search">
        <div class="table-responsive">
            <table width="100%">
                <tr>
                    <td width="100%">
                        <div class="form-group">
                            <!-- <label><spring:message code="home.question"/></label> -->
                            <input type="text" id="question" name="question" class="form-control" placeholder="<spring:message code="home.placeholder"/>">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td style="text-align: right;">
                        <button type="submit" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> &nbsp;<spring:message code="navbar.search"/></button>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div style="width: 100px; float:left; height:100px; background:yellow; margin:10px">
    <table>
        <c:if test="${empty question}">
            <tr>No question was found..</tr>
        </c:if>
        <c:if test="${not empty question}">
            <tr>
                <td>Question: </td>
                <td>${question.body}</td>
            </tr>
            <tr>
                <td>Answer: </td>
                <td>${question.answer}</td>
            </tr>
            <tr>
                <td>Times requested: </td>
                <td>${question.requestsNum}</td>
            </tr>
        </c:if>
    </table>
</div>
</div>
</div>
<div class="col-md-5">
    <table id="recentTab" class="table table-striped table-bordered" width="100%">
        <thead>
            <tr>
                <th style="width:60%;"><spring:message code="home.topics"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="topic" items="${topics}" varStatus="loop">
                <tr>
                    <td>${topic.title}</td>
                    <td>${topic.requestsNum}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<br />
<div class="col-md-9 text-left">
    <p><a href="${pageContext.request.contextPath}/reset"> <img src="resources/img/erazer.jpg" alt="ERAZER" style="width:20%;"/> </a></p>
</div>
</div>
</body>
</html>
