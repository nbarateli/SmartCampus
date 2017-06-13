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
</head>
<meta name="google-signin-client_id"
      content="752594653432-dcqce0b92nbtce0d0ahpq91jfis07092.apps.googleusercontent.com">
<script src="https://apis.google.com/js/api.js">gapi.load('auth2', function () {
    gapi.auth2.init();
});
</script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script>
    function onSignIn(googleUser) {
        var profile = googleUser.getBasicProfile();
        console.log(profile.getEmail());
    }
</script>
<body>
<div align="center">
  <table>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td></td>
      <td>რა გაგჭირვებია?</td>
      <td></td>
    </tr>
    <tr></tr>
    <tr>
      <table>
        <tr>
          <td></td>
          <td><a href="rooms/index.jsp">ოთახის მოძებნა მინდა</a></td>
          <td></td>

          <td><a href="index.jsp">დავკარგე რაღაც</a></td>

        </tr>
        <tr>
          <td></td>
          <td></td>
          <td><a href="index.jsp">პრობლემა მინდა შეგატყობინოთ</a></td>
          <td></td>
        </tr>
      </table>
    </tr>
    <tr></tr>
    <tr>
      <td>
        <div class="g-signin2" data-onsuccess="onSignIn"></div>
      </td>
    </tr>
    <tr><td><a href="#" onclick="signOut();">Sign out</a></td></tr>
    <script>
        function signOut() {
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
                console.log('User signed out.');
            });
        }
    </script>
  </table>
</div>
</body>
</html>
