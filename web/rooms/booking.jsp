<%@ page import="model.accounts.User" %>
<%@ page import="static misc.WebConstants.MANAGER_FACTORY" %>
<%@ page import="static misc.WebConstants.SIGNED_ACCOUNT" %>
<%@ page import="serve.managers.ManagerFactory" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 03.07.2017
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/js/auto-complete.js"></script>
<script src="/js/utils.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="icon" href="img/smallLogo.png">
<link rel="stylesheet" href="/css/auto-complete.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>
<%!
  private ManagerFactory factory;
  private User currentUser;
%>

<%
  factory = (ManagerFactory) request.getServletContext().getAttribute(MANAGER_FACTORY);
  currentUser = (User) request.getSession().getAttribute(SIGNED_ACCOUNT);

  if (currentUser == null || !factory.getAccountManager().
          getAllPermissionsOf(currentUser).contains(User.UserPermission.BOOK_A_ROOM)) {
    response.sendRedirect("/unallowed_operation.html");
  }
%>

<%!
  private String getClickFunction() {
    if (!factory.getAccountManager().
            getAllPermissionsOf(currentUser).contains(User.UserPermission.INSERT_DATA)) {
      return "addBookingFromForm()";
    } else {
      return "addLectureFromForm()";
    }
  }
%>

<head>
  <title>ოთახის დაჯავშნა</title>
  <meta charset="utf-8">

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="css/BookingPageStyle.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

  <script src="${pageContext.request.contextPath}/scriptlibs/Datepair.js"></script>
  <script src="${pageContext.request.contextPath}/scriptlibs/jquery.datepair.js"></script>
  <script src="${pageContext.request.contextPath}/scriptlibs/wrapper.js"></script>
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.min.js"></script>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>


  <script
          src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>
  <link rel="stylesheet" href="/css/bootstrap-datepicker.min.css">
  <script src="js/booking.js"></script>
</head>
<body>

<h1 id="header">ოთახის დაჯავშნა</h1>

<br>
<br>

<div align="center" id="main-div">
  <div id="form-div">
    <form id="booking-form" class="form-vertical">

      <div class="form-group">
        <input type="email" name="lecturer_email" id="lect_mail"
               class="form-control"
               placeholder="შეიყვანეთ ლექტორის/დამჯავშნავის ფოსტის მისამართი">
      </div>

      <div class="form-group ui-widget">
        <input type="text" name="room_name" class="form-control"
               placeholder="შეიყვანეთ ოთახის დასახელება" width="100px" id="r_name">
        <script>roomNameAutocomplete('r_name');</script>
      </div>

      <div class="form-group">
        <input type="text" name="subject_name" class="form-control" id="subj_name"
               placeholder="შეიყვანეთ საგნის სახელი (თუ ლექციაა)">
      </div>

      <div class="form-group">
        <input type="text" name="description" class="form-control" id="desc"
               placeholder="შეიყვანეთ დაჯავშნის მიზეზი (თუ ლექცია არ არის)">
      </div>

      <input type="hidden" name="repetition" value="1">
      <input type="hidden" name="num_weeks" value="1">

      <br>

      <label class="control-label" for="datepairExample"> დროით ძებნა</label>
      <div class="form-group">
        <table id="datepairExample" class="form-group">
          <tr id="fromRow">
            <td><label class="control-label">თარიღი <br>
              <input type="text" name="start_date" class="date start"/>
            </label></td>
            <td><label class="control-label">დრო (დან)<br>
              <input type="text" name="start_time" class="time start"/>
            </label></td>

          </tr>
          <tr id="toRow">
            <td>
              <label> თარიღი (მდე)
                <input type="text" name="end_date" class="date end"/>
              </label>
            </td>
            <td>
              <label class="control-label">დრო<br>
                <input type="text" name="end_time" class="time end"/>
              </label>
            </td>
          </tr>
        </table>
      </div>


      <script>
          // initialize input widgets first
          $('#datepairExample .time').timepicker({
              'showDuration': true,
              'timeFormat': 'H:i'
          });

          $('#datepairExample .date').datepicker({
              'format': 'dd.mm.yyyy',
              'autoclose': true
          });

          // initialize datepair
          $('#datepairExample').datepair();
      </script>

      <input type="button" value="დამატება"
             class="btn btn-info btn-lg"
             onclick=<%= getClickFunction()%>>
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
%>

<script>
    changeName(<%=roomName%>)
</script>

<%
  }
  if (!factory.getAccountManager().
          getAllPermissionsOf(currentUser).contains(User.UserPermission.INSERT_DATA)) {
%>

<script>
    hideNeededInputs()
</script>

<%
} else {
%>

<script>
    changeMail(<%="'" + currentUser.geteMail() + "'"%>)
</script>

<%
  }
%>

</body>
</html>
