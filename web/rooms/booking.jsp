<%@ page import="serve.managers.ManagerFactory" %>
<%@ page import="static misc.WebConstants.MANAGER_FACTORY" %>
<%@ page import="static misc.WebConstants.SIGNED_ACCOUNT" %>
<%@ page import="model.accounts.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 03.07.2017
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
  ManagerFactory factory = (ManagerFactory) request.getServletContext().getAttribute(MANAGER_FACTORY);
  User currentUser = (User) request.getSession().getAttribute(SIGNED_ACCOUNT);

  if (currentUser == null || !factory.getAccountManager().
          getAllPermissionsOf(currentUser).contains(User.UserPermission.BOOK_A_ROOM)) {
    response.sendRedirect("/unallowed_operation.html");
  }
%>

<head>
  <title>ოთახის დაჯავშნა</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="BookingPageStyle.css">

  <script
          src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script
          src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>
  <script src="booking.js"></script>
</head>
<body>

<h1 id="header">ოთახის დაჯავშნა</h1>

<br>
<br>

<div align="center" id="main-div">
  <div id="form-div">
    <form id="booking-form" class="form-vertical">

      <div class="form-group">
        <input type="text" name="room_name" class="form-control"
               placeholder="შეიყვანეთ ოთახის დასახელება" width="100px" id="r_name">
      </div>

      <div class="form-group">
        <input type="text" name="description" class="form-control"
               placeholder="შეიყვანეთ დაჯავშნის მიზეზი">
      </div>

      <br>

      <div class="form-group">
        <label class="control-label">ჯავშნის დრო</label>
        <input type="text" name="start_time" class="form-control"
               placeholder="შეიყვანეთ ჯავშნის დაწყების დრო(HH:mm 24-საათიანი ფორმატით)">
      </div>
      <div class="form-group">

        <input type="text" name="end_time" class="form-control"
               placeholder="შეიყვანეთ ჯავშნის დასრულების დრო(HH:mm 24-საათიანი ფორმატით)">
      </div>

      <div class="form-group">
        <label class="control-label">ჯავშნის თარიღი</label>
        <input type="date" name="start_date" class="form-control">
      </div>

      <br>

      <input type="button" value="დამატება"
             class="btn btn-info btn-lg"
             onclick="addBookingFromForm()">
    </form>

  </div>

  <div>
    <br><br><br>
    <a href="/index.jsp" id="home-link">მთავარ გვერდზე დაბრუნება</a>
  </div>

</div>

<%
  String roomName = request.getParameter("room_name");
  if (roomName != null) {
    out.println("<script>");
    out.println("changeName(" + roomName + ")");
    out.println("</script>");
  }
%>

</body>
</html>
