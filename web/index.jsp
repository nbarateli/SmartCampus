<%--
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
</head>
<meta name="google-signin-client_id"
      content="752594653432-dcqce0b92nbtce0d0ahpq91jfis07092.apps.googleusercontent.com">
<script src="https://apis.google.com/js/api.js">gapi.load('auth2', function () {
    gapi.auth2.init();
});
</script>
<link rel="stylesheet" href="styles.css">
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script src="js/auth.js"></script>

<body>


<div class="main-container">
    <div class="main-container_inner">
        <div class="title">რა გაგჭირვებია?</div>
        <div class="find-room"><a href="rooms/index.jsp"><i class="fa fa-search" aria-hidden="true"></i>   ოთახის მოძებნა მინდა</a></div>
        <%--<div class="lost-and-found"><a href="index.jsp">დავკარგე რაღაც</a></div>--%>
        <%--<div class="report-problem"><a href="index.jsp">პრობლემა მინდა შეგატყობინოთ</a></div>--%>
        <div class="input-data"><a href="data/addingData.jsp"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>  მონაცემების შეყვანა</a></div>
        <div class="book-room"><a href="rooms/booking.jsp"><i class="fa fa-bookmark" aria-hidden="true"></i>  ოთახის დაჯავშნა</a></div>
        <div class="g-signin2 sign-in" data-onsuccess="onSignIn"></div>
        <div class="sign-out"><a href="#" onclick="signOut();"><i class="fa fa-sign-out" aria-hidden="true"></i>  Sign out</a></div>
    </div>
</div>


</body>
</html>

