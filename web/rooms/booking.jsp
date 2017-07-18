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

<%!
  private ManagerFactory factory;
  private User currentUser;
%>

<%
  factory = (ManagerFactory) request.getServletContext().getAttribute(MANAGER_FACTORY);
  currentUser = (User) request.getSession().getAttribute(SIGNED_ACCOUNT);

  if (currentUser == null || !factory.getAccountManager().
          getAllPermissionsOf(currentUser).contains(User.UserPermission.BOOK_A_ROOM)) {
    response.sendRedirect("/unallowed_operation.jsp");
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
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="css/BookingPageStyle.css">
  <link rel="stylesheet" href="/css/auto-complete.css">
  <link rel="stylesheet" href="/css/bootstrap-datepicker.min.css">
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.min.css">
  <%--<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">--%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="/js/auto-complete.js"></script>
  <script src="/js/utils.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="icon" href="../img/smallLogo.png">

  <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">--%>
  <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>--%>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


  <link href="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/css/bootstrap-timepicker.min.css" rel="stylesheet"/>

  <script src="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/js/bootstrap-timepicker.min.js"></script>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>
  <script src="${pageContext.request.contextPath}/scriptlibs/jquery.datepair.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>
  <script src="js/booking.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.min.js"></script>
  <script src="${pageContext.request.contextPath}/scriptlibs/Datepair.js"></script>
  <script src="${pageContext.request.contextPath}/scriptlibs/wrapper.js"></script>

  <meta name="google-signin-client_id"
        content="752594653432-dcqce0b92nbtce0d0ahpq91jfis07092.apps.googleusercontent.com">
  <script src="https://apis.google.com/js/api.js">

  </script>
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <script src="/js/auth.js"></script>

</head>
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
                                            style="height: 55px; width:65px"></a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <%
        if (currentUser != null) {
          out.print("<li><a><img src=\"" + currentUser.getImageURL() + "\" class=\"navbar-pic\"></a></li>\n" +
                  "      <li><a> " + currentUser.getFirstName() + " " + currentUser.getLastName() + "</a></li>\n" +
                  "      <li>\n" +
                  "        <a class=\"sign-out\">\n" +
                  "      <div class='btn btn-info' onclick=\"signOut();\"><span class='glyphicon glyphicon-log-out'></span> Sign out</div>\n" +
                  "        </a>\n" +
                  "      </li>");
        } else {
          out.print("\n" +
                  "      <li>\n" +
                  "        <a class=\"g-signin2 sign-in\" data-onsuccess=\"onSignIn\"></a>\n" +
                  "      </li>");
        }
      %>
    </ul>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

    </div>
    <!-- /.navbar-collapse -->
  </div>
  <!-- /.container -->
</nav>
<%--<a id="back-to-main" class="image" href="/"></a>--%>
<h1 id="header">ოთახის დაჯავშნა</h1>

<br>
<br>

<div align="center" id="main-div">
  <div id="form-div">
    <form id="booking-form" class="form-vertical">

      <div class="form-group ui-widget">
        <input type="text" name="room_name" class="form-control"
               placeholder="შეიყვანეთ ოთახის დასახელება" width="100px" id="r_name">
        <script>roomNameAutocomplete('r_name');</script>
      </div>


      <div class="form-group">
        <input type="text" name="description" class="form-control" id="desc"
               placeholder="შეიყვანეთ დაჯავშნის მიზეზი">
      </div>

      <input type="hidden" name="repetition" value="1">
      <input type="hidden" name="num_weeks" value="1">

      <br>

      <label class="control-label" for="timeForm"> დაჯავშნის დრო</label>
      <div id="timeForm" class="form-group">

        <div class="form-center">თარიღი<br>
          <input type="text" name="booking_date" class="date start" id="end_d"/>
        </div>
        <div class="form-left  ">დრო (დან) <br>
          <input type="text" name="start_time" class="time start" id="start_t"/>
        </div>

        <div class="form-right ">დრო (მდე) <br>
          <input type="text" name="end_time" class="time end" id="end_t"/>
        </div>
      </div>

      <%--<div class="form-group">--%>
      <%--<table id="datepairExample" class="form-group">--%>
      <%--<tr id="fromRow">--%>
      <%--<td><label class="control-label">თარიღი <br>--%>
      <%--<input type="text" name="start_date" class="date start"/>--%>
      <%--</label></td>--%>
      <%--<td><label class="control-label">დრო (დან)<br>--%>
      <%--<input type="text" name="start_time" class="time start"/>--%>
      <%--</label></td>--%>

      <%--</tr>--%>
      <%--<tr id="toRow">--%>
      <%--<td>--%>
      <%--<label> თარიღი (მდე)--%>
      <%--<input type="text" name="end_date" class="date end"/>--%>
      <%--</label>--%>
      <%--</td>--%>
      <%--<td>--%>
      <%--<label class="control-label">დრო<br>--%>
      <%--<input type="text" name="end_time" class="time end"/>--%>
      <%--</label>--%>
      <%--</td>--%>
      <%--</tr>--%>
      <%--</table>--%>
      <%--</div>--%>


      <script>
          // initialize input widgets first
          $('#timeForm .time').timepicker({
              'showDuration': true,
              'timeFormat': 'H:i',
              'maxTime': '23:59',
              'minTime': '10:00',
              'step': 70
          });

          $('#timeForm .end').timepicker('option', 'minTime', '11:00');


          $('#timeForm .date').datepicker({
              'format': 'dd.mm.yyyy',
              'autoclose': true
          });

          // initialize datepair
          var timeForm = document.getElementById('timeForm');
          let datePair = new Datepair(timeForm);
      </script>

      <input type="button" value="დამატება" style="margin-top: 100px" ;
             class="btn btn-info btn-lg"
             onclick="addBookingFromForm()">
      <br><br>
      <span id="responseID"></span>
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

<%
  }
%>

</body>
</html>
