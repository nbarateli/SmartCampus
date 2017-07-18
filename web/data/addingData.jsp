<%@ page import="model.accounts.User" %>
<%@ page import="serve.managers.ManagerFactory" %>
<%@ page import="static misc.WebConstants.SIGNED_ACCOUNT" %>
<%@ page import="static misc.WebConstants.MANAGER_FACTORY" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%
  ManagerFactory factory = (ManagerFactory) request.getServletContext().getAttribute(MANAGER_FACTORY);
  User currentUser = (User) request.getSession().getAttribute(SIGNED_ACCOUNT);

  if (currentUser == null || !factory.getAccountManager().
          getAllPermissionsOf(currentUser).contains(User.UserPermission.INSERT_DATA)) {
    response.sendRedirect("/unallowed_operation.html");
  }
%>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>მონაცემების შეყვანა</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="css/addingDataStyle.css">
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="icon" href="../img/smallLogo.png">
  <%--<link rel="stylesheet" href="/rooms/css/SearchPageStyle.css">--%>
  <link rel="stylesheet" href="/css/auto-complete.css">
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.min.js"></script>


  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="js/DataUtils.js"></script>


  <link rel="stylesheet" href="/css/bootstrap-datepicker.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>

  <script src="${pageContext.request.contextPath}/scriptlibs/Datepair.js"></script>
  <script src="${pageContext.request.contextPath}/scriptlibs/jquery.datepair.js"></script>
  <script src="${pageContext.request.contextPath}/scriptlibs/wrapper.js"></script>
  <script src="/js/auto-complete.js"></script>
  <script src="/js/utils.js"></script>
  <meta name="google-signin-client_id"
        content="752594653432-dcqce0b92nbtce0d0ahpq91jfis07092.apps.googleusercontent.com">
  <script src="https://apis.google.com/js/api.js">

  </script>
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <script src="/js/auth.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
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

<%--<a id="back-to-main" class="image" href="/" style=" display:inline-block;"></a>--%>

<div align="center" id="main-div">
  <button class="main-button" data-toggle="modal" data-target="#schedule-modal">ლექციების ცხრილის შეყვანა</button>

  <div class="modal fade" id="schedule-modal" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">

        <div class="modal-body">
          <label class="info-label"><b>შეგიძლიათ მონაცემები შეიყვანოთ ხელით (სათითაოდ):</b></label> <br>
          <br>

          <form id="sched-form" class="form-vertical">

            <div class="form-group">
              <input type="email" name="lecturer_email"
                     class="form-control"
                     placeholder="შეიყვანეთ ლექტორის ფოსტის მისამართი">
              <span id="error_lecturer_email" class="errorMessage"></span>
            </div>

            <div class="form-group">
              <input type="text" name="subject_name" class="form-control" id="subj_name_l"
                     placeholder="შეიყვანეთ საგნის სახელი">
              <span id="error_subject_name" class="errorMessage"></span>
            </div>
            <script> subjectNameAutocomplete('subj_name_l')</script>
            <div class="form-group">
              <input type="number" name="num_students" class="form-control" id="num_stud"
                     placeholder="შეიყვანეთ სტუდენტების რაოდენობა" min=1 max=200>
            </div>

            <br>

            <div>
              <input type="number" name="num_weeks" class="form-control" id="num_w"
                     placeholder="კვირების რაოდენობა" min=1 max=16>
              <span id="error_numWeeks" class="errorMessage"></span>
            </div>

            <br>

            <div class="select">
              <select name="repetition" class="form-control" id="rep">
                <option value="1">კვირაში ერთხელ</option>
                <option value="2">ორ კვირაში ერთხელ</option>
                <option value="3">სამ კვირაში ერთხელ</option>
                <option value="4">ოთხ კვირაში ერთხელ</option>
              </select>
            </div>

            <br>

            <div id="datepairExample" class="form-group">
              <div class="form-mid">ლექციის თარიღი<br>
                <input type="text" name="start_date" class="date start" id="start_d"/>
                <span id="error_date" class="errorMessage"></span>
              </div>
              <div class="form-left">დრო (დან) <br>
                <input type="text" name="start_time" class="time start" id="start_t"/>
                <span id="error_start_time" class="errorMessage"></span>
              </div>
              <div class="form-right">დრო (მდე) <br>
                <input type="text" name="end_time" class="time end" id="end_t"/>
                <span id="error_end_time" class="errorMessage"></span>
              </div>
            </div>

            <script>
                // initialize input widgets first
                $('#datepairExample .time').timepicker({
                    'showDuration': true,
                    'timeFormat': 'H:i',
                    'maxTime': '23:59',
                    'minTime': '10:00',
                    'step': 70
                });

                $('#timeForm .end').timepicker('option', 'minTime', '11:00');

                $('#datepairExample .date').datepicker({
                    'format': 'dd.mm.yyyy',
                    'autoclose': true
                });

                // initialize datepair
                $('#datepairExample').datepair();
            </script>

            <br>
            <div class="form-group ui-widget">
              <br>
              <input name="room_name" class="form-control" id="room_n"
                     placeholder="შეიყვანეთ ოთახის დასახელება">
              <span id="error_room_name" class="errorMessage"></span>
            </div>

            <br>
            <br>
            <br>

            <input type="button" value="დამატება"
                   class="btn btn-primary btn-lg"
                   onclick="addLectureFromForm()">
            <br>
            <span id="output"></span>
          </form>

          <br>
          <br>
          <br>
          <label id="info-label2"><b>ან ატვირთოთ Excel-ის ფაილი:</b></label>
          <div id="info-label3">
            ექსელის ფორმატის შაბლონი შეგიძლიათ <a href="../excel_template.xlsx">იხილოთ აქ.</a>
            <br>უცვლელი დატოვეთ <b>პირველი მწკრივი!</b>
          </div>
          <div style="padding: 10px;border: solid 1px #cccccc">
            <div id="drop_zone" style="padding: 5px; border: dashed #cccccc">Drop files here</div>
          </div>
          <output id="list_drag"></output>
          <script src="js/FileDragDrop.js"></script>
          <br>
          <br>
          <div class="form-vertical" id="lect-file">
            <input type="file" name="pic" accept=".xls,.xlsx">
            <img src="../../img/tick.png" id="lecture-tick"/>
            <img src="../../img/w8.gif" id="lecture-w8gif"/>
            <img src="../../img/fail.png" id="lecture-fail"/><br>
            <output id="lect-list"></output>
          </div>
          <br>
          <br>

        </div>
      </div>

    </div>

  </div>
  <br> <br> <br>

  <button class="main-button" data-toggle="modal" data-target="#add-room-modal">ოთახის დამატება</button>
  <div class="modal fade" id="add-room-modal" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">

        <div class="modal-body">
          <br>
          <br>
          <br>
          <label class="info-label"><b>შეგიძლიათ მონაცემები შეიყვანოთ ხელით (სათითაოდ):</b></label>
          <br>
          <br>

          <form id="add-room-form" class="form-vertical">

            <div class="form-group">
              <label class="control-label">ოთახის სახელი</label> <input
                    type="text" name="room_name" class="form-control"
                    placeholder="შეიყვანეთ ოთახის სახელი">
              <span id="error_room" class="errorMessage"></span>
            </div>

            <div class="form-group">
              <label class="control-label">სართული</label> <input id="add-room-floor-number"
                                                                  type="number" name="room_floor" class="form-control"
                                                                  placeholder="შეიყვანეთ სართული" min=0 max=4>
              <span id="error_floor" class="errorMessage"></span>

            </div>

            <div class="form-group">
              <label class="control-label">ადგილების რაოდენობა</label>
              <input type="number" name="capacity"
                     class="form-control"
                     placeholder="შეიყვანეთ ადგილების რაოდენობა" min=0>
              <span id="error_capacity" class="errorMessage"></span>
            </div>

            <div class="select">
              <label class="control-label">ოთახის ტიპი</label> <select
                    name="room_type" class="form-control">
              <option value="auditorium">აუდიტორია</option>
              <option value="laboratory">ლაბორატორია</option>
              <option value="utility">სხვა</option>
            </select>
            </div>

            <div class="select">
              <label class="control-label">ადგილების ტიპი</label> <select
                    name="seat_type" class="form-control">
              <option value="desks">სკამები და მერხები</option>
              <option value="chairs">სკამ-მერხები</option>
              <option value="computers">კომპიუტერები</option>
              <option value="tables">მაგიდები</option>
            </select>
            </div>

            <div class="checkbox">
              <label class="control-label">
                <input type="checkbox" name="can_be_booked"> შეიძლება სტუდენტისთვის
              </label>
            </div>
            <input type="button" value="დამატება" class="btn btn-primary btn-lg" onclick="addRoomFromForm()">
            <br>
            <span id="message"></span>
          </form>

          <br>
          <br>
          <br>
          <label id="info-label4"><b>ან ატვირთოთExcel-ის ფაილი:</b></label>
          <div id="info-label5">
            ექსელის ფორმატის შაბლონი შეგიძლიათ <a href="../room_excel_template.xlsx">იხილოთ აქ.</a>
            <br>უცვლელი დატოვეთ <b>პირველი მწკრივი!</b>
          </div>
          <br>
          <br>
          <div class="form-vertical" id="rooms-file">
            <img src="../../img/tick.png" id="room-tick"/>
            <img src="../../img/w8.gif" id="room-w8gif"/>
            <img src="../../img/fail.png" id="room-fail"/>
            <input type="file" name="pic" accept=".xls,.xlsx"><br>
            <output id="room-list"></output>
          </div>
          <br>
          <br>
        </div>
      </div>
    </div>
  </div>
  <br> <br> <br>

  <button class="main-button" data-toggle="modal"
          data-target="#remove-room-modal">ოთახის წაშლა
  </button>
  <div class="modal fade" id="remove-room-modal" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">

        <div class="modal-body">
          <br>
          <br>
          <br>
          <form id="remove-room-form" class="form-vertical">

            <div class="form-group ui-widget">
              <input type="text" name="room_name" class="form-control" id='room_n_delete'
                     placeholder="შეიყვანეთ ოთახის სახელი">
            </div>
            <script>roomNameAutocomplete('room_n_delete')</script>
            <input type="button" value="წაშლა" class="btn btn-primary btn-lg"
                   onclick="removeRoomFromForm()">
            <br>
            <span id="remove_room_output"></span>
          </form>
        </div>
      </div>
    </div>
  </div>
  <br> <br> <br>

  <button class="main-button" data-toggle="modal" data-target="#add-subj-modal">საგნის დამატება</button>
  <div class="modal fade" id="add-subj-modal" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">

        <div class="modal-body">
          <br>
          <br>
          <br> <label class="info-label"><b>შეგიძლიათ მონაცემები შეიყვანოთ ხელით (სათითაოდ):</b></label> <br>
          <br>
          <form id="add-subj-form" class="form-vertical">

            <div class="form-group ui-widget">
              <input type="text" name="subj_name" class="form-control ui-widget"
                     placeholder="შეიყვანეთ საგნის სახელი">
            </div>
            <input type="button" value="დამატება"
                   class="btn btn-primary btn-lg"
                   onclick="addSubjectFromForm()">
            <br>
            <span id="add_subject_output"></span>
          </form>

          <br>
          <br>
          <br>
          <label id="info-label6"><b>ან ატვირთოთ Excel-ის ფაილი:</b></label>
          <div id="info-label7">
            ექსელის ფორმატის შაბლონი შეგიძლიათ <a href="../subj_excel_template.xlsx">იხილოთ აქ.</a>
            <br>უცვლელი დატოვეთ <b>პირველი მწკრივი!</b>
          </div>
          <br>
          <br>
          <div class="form-vertical" id="subj-file">
            <input type="file" name="pic" accept=".xls,.xlsx"><br>
            <img src="../../img/tick.png" id="subject-tick"/>
            <img src="../../img/w8.gif" id="subject-w8gif"/>
            <img src="../../img/fail.png" id="subject-fail"/>
            <output id="subj-list"></output>
          </div>
          <br>
          <br>
        </div>
      </div>
    </div>
  </div>
  <br><br><br>

  <button class="main-button" onclick="createDialog()">ლექციების ცხრილის წაშლა</button>
  <br>

  <div>
    <br><br><br>
    <a href="/index.jsp">მთავარ გვერდზე დაბრუნება</a>
  </div>
</div>
<%
  String roomName = request.getParameter("room_name");
  if (roomName != null) {
    out.print("<script>showModalWithName(" + roomName + ")</script>");
  }
%>
</body>
</html>