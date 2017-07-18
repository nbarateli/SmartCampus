<%@ page import="model.accounts.User" %>
<%@ page import="static misc.WebConstants.SIGNED_ACCOUNT" %><%--
  Created by IntelliJ IDEA.
  User: Niko
  Date: 06.06.2017
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>SmartCampus</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link rel="icon" href="img/smallLogo.png">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="css/styles.css">
</head>
<%!private User currentUser;%>
<%currentUser = (User) request.getSession().getAttribute(SIGNED_ACCOUNT);%>
<meta name="google-signin-client_id"
      content="752594653432-dcqce0b92nbtce0d0ahpq91jfis07092.apps.googleusercontent.com">
<script src="https://apis.google.com/js/api.js">
</script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script src="js/auth.js"></script>

<body>

<nav class="navbar navbar-inverse" role="navigation">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/"><img src="/img/bigLogo.png" class="mainPageButton"
                                            style="height: 40px; width:40px"></a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <%
        if (currentUser != null) {
          out.print("<li><a><img src=\"" + currentUser.getImageURL() + "\" class=\"navbar-pic\"></a></li>\n" +
                  "      <li><a> " + currentUser.getFirstName() + " " + currentUser.getLastName() + "</a></li>\n" +
                  "      <li>\n" +
                  "        <a class=\"sign-out\">\n" +
                  "          <div class='btn btn-warning' onclick=\"signOut();\"> Sign out</div>\n" +
                  "        </a>\n" +
                  "      </li>");
        }
      %>
    </ul>

    <!-- /.navbar-collapse -->
  </div>
  <!-- /.container -->
</nav>
<div class="main-container">
  <div class="main-container_inner">
    <div class="title">რა გაგჭირვებია?</div>
    <div class="find-room"><a href="rooms/index.jsp"><i class="fa fa-search" aria-hidden="true"></i> ოთახის მოძებნა
      მინდა</a></div>
    <%--<div class="lost-and-found"><a href="index.jsp">დავკარგე რაღაც</a></div>--%>
    <%--<div class="report-problem"><a href="index.jsp">პრობლემა მინდა შეგატყობინოთ</a></div>--%>
    <div class="input-data"><a href="data/addingData.jsp"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>
      მონაცემების შეყვანა</a></div>
    <div class="book-room"><a href="rooms/booking.jsp"><i class="fa fa-bookmark" aria-hidden="true"></i> ოთახის დაჯავშნა</a>
    </div>

    <%
      if (currentUser == null) {
        out.print("    <div class=\"g-signin2 sign-in\" data-onsuccess=\"onSignIn\"></div>\n");
      } else {
        out.print("<div class=\"sign-out\">\n" +
                "      <div class='btn btn-warning' onclick=\"signOut();\"> Sign out</div>\n" +
                "    </div>");
      }
    %>
  </div>
</div>


</body>
</html>

