<%@ page import="model.accounts.User" %>
<%@ page import="static misc.WebConstants.MANAGER_FACTORY" %>
<%@ page import="static misc.WebConstants.SIGNED_ACCOUNT" %>
<%@ page import="serve.managers.ManagerFactory" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.HashMap" %>
<%--
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
<link rel="icon" href="../img/smallLogo.png">
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
<a id="back-to-main" class="image" href="/"></a>
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
              <input type="text" name="start_date" class="date start" id = "start_dt"/>
            </label></td>
            <td><label class="control-label">დრო (დან)<br>
              <input type="text" name="start_time" class="time start" id = "start_tm"/>
            </label></td>

          </tr>
          <tr id="toRow">
            <td>
              <label> თარიღი (მდე)
                <input type="text" name="end_date" class="date end" id = "end_dt"/>
              </label>
            </td>
            <td>
              <label class="control-label">დრო<br>
                <input type="text" name="end_time" class="time end" id = "end_tm"/>
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
  Map<String, String> map = new HashMap<String, String>();
  map.put("Jan", "01");
  map.put("Feb", "02");
  map.put("Mar", "03");
  map.put("Apr", "04");
  map.put("May", "05");
  map.put("Jun", "06");
  map.put("Jul", "07");
  map.put("Aug", "08");
  map.put("Sep", "09");
  map.put("Oct", "10");
  map.put("Nov", "11");
  map.put("Dec", "12");
  int i = 0;
  String date1 = request.getParameter("start_date");
  if (date1 != null) {
  StringTokenizer st = new StringTokenizer(date1);
  String date = "";

  while (st.hasMoreTokens()) {
    i++;
    String temp = st.nextToken();
    if(i == 2){
        date = '.' + map.get(temp) + '.';
    }
    if(i == 3){
        date = Integer.parseInt(temp) + date;
    }
    if(i == 6){
      date = date + Integer.parseInt(temp);
    }
  }

%>

<script>
    changeDate(<%="'" + date + "'"%>)
</script>

<%
  }
%>


<%
  String date2 = request.getParameter("end_date");
  if (date2 != null) {
  StringTokenizer st1 = new StringTokenizer(date2);
  String dates = "";
  i = 0;
  while (st1.hasMoreTokens()) {
  i++;
  String temp = st1.nextToken();
  if(i == 2){
  dates = '.' + map.get(temp) + '.';
  }
  if(i == 3){
  dates = Integer.parseInt(temp) + dates;
  }
  if(i == 6){
  dates = dates + Integer.parseInt(temp);
  }
  }

%>

<script>
    changeDateSecond(<%="'" + dates + "'"%>)
</script>

<%
  }
%>


<%
  String startTime = request.getParameter("start_time");
  System.out.println(startTime);
  if (startTime != null) {
%>

<script>
    changeTimeStart(<%="'" + startTime + "'"%>)
</script>

<%
  }
%>

<%
  String endTime = request.getParameter("end_time");
  System.out.println(endTime);
  if (endTime != null) {
%>

<script>
    changeTimeEnd(<%="'" + endTime + "'"%>)
</script>

<%
  }
%>













<%
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