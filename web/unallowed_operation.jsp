<%@ page import="static misc.WebConstants.SIGNED_ACCOUNT" %><%--
  Created by IntelliJ IDEA.
  User: Niko
  Date: 18.07.2017
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>შეცდომა</title>
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <script src="js/auth.js"></script>

</head>
<meta name="google-signin-client_id"
      content="752594653432-dcqce0b92nbtce0d0ahpq91jfis07092.apps.googleusercontent.com">
<script src="https://apis.google.com/js/api.js">gapi.load('auth2', function () {
    gapi.auth2.init();
});
</script>

<link rel="stylesheet" href="css/styles.css">
<body>
<div class="box" align="center">
  <div class=center_message>სამწუხაროდ, თქვენ არ გაქვთ ამ ოპერაციის განხორციელების უფლება.</div>
  <div><a href="index.jsp">დაბრუნება მთავარ გვერდზე</a></div>
  <%
    if (session.getAttribute(SIGNED_ACCOUNT) == null)
      out.print("<div class=\"g-signin2 sign-in\" data-onsuccess=\"onSignIn\"></div>");
  %>
</div>
</body>
</html>