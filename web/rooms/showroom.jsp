<%@ page import="misc.WebConstants" %>
<%@ page import="model.accounts.AccountManager" %>
<%@ page import="model.accounts.User" %>
<%@ page import="model.rooms.Room" %>
<%@ page import="model.rooms.RoomManager" %>
<%@ page import="static misc.Utils.roomTypeToString" %>
<%@ page import="static misc.Utils.toSeatType" %>
<%@ page import="static misc.Utils.toGeorgian" %>
<%@ page import="static misc.Utils.seatTypeToString" %>
<%@ page import="static misc.WebConstants.MANAGER_FACTORY" %>
<%@ page import="serve.managers.ManagerFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="static misc.Utils.*" %>
<%@ page import="static misc.WebConstants.SIGNED_ACCOUNT" %>
<%--
  Created by IntelliJ IDEA.
  User: Niko
  Date: 11.06.2017
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--
    the room will be displayed based on its id.
    for example: .../showroom.jsp?id=1 must display a room with id 1.
    if such exists.
    if no parameters is passed
--%>
<head>

  <link rel="stylesheet" href="css/ShowRoomStyle.css">
  <%--<link rel="stylesheet" href="/css/bootstrap.css">--%>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="/data/css/addingDataStyle.css">
  <link rel="stylesheet" href="css/ShowRoomStyle.css">

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
  <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">--%>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="css/style.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>

  <%!
    private boolean canDisplay(HttpServletRequest request) {
      User user = (User) request.getSession().getAttribute(SIGNED_ACCOUNT);
      AccountManager manager = ((ManagerFactory) request.getServletContext().getAttribute(MANAGER_FACTORY)).
              getAccountManager();
      try {
        return user != null && manager.getAllPermissionsOf(user).contains(User.UserPermission.INSERT_DATA);
      } catch (Exception e) {
        return false;
      }
    }


    Room room;
    List<String> images;
    RoomManager manager;
  %>
  <%

    try {
      manager = ((ManagerFactory) request.getServletContext()
              .getAttribute(MANAGER_FACTORY)).getRoomManager();
      room = manager.getRoomById(Integer.valueOf(request.getParameter("id")));
      images = manager.getAllImagesOf(room);
    } catch (Exception e) {

    }
  %>
  <title>
    <%
      out.println(room == null ? "Error" : room.getRoomName());
    %>
  </title>
  <link rel="icon" href="/img/smallLogo.png">

</head>
<body style="background-color: #bbbbbb">

<a id="back-to-main" class="image" href="/"></a>

<div class="modal fade" id="add-room-modal" role="dialog">
  <div class="modal-dialog">

    <div class="modal-content">

      <div class="modal-body">
        <br>
        <br>
        <br>
        <label class="info-label"><b>აირჩიეთ სურათ(ებ)ი:</b></label>
        <br>
        <br>

        <br>
        <br>
        <br>

        <br>
        <br>
        <div class="form-vertical" id="rooms-file">
          <input type="file" id='imageup' name="pic" multiple accept="image/*" style=";display:none">
          <div style="padding: 10px;border: solid 1px #cccccc">
            <div id="drop_zone" style="padding: 5px; border: dashed #cccccc">Drop files
              here
            </div>
          </div>
          <br>
          <output id="image-list"></output>
        </div>
        <br>
        <br>
      </div>
    </div>
  </div>
</div>
<%
  if (canDisplay(request)) {
    out.print("<button class=\"main-button\" data-toggle=\"modal\" data-target=\"#add-room-modal\">სურათის ატვირთვა\n" +
            "</button>");
  }
%>
<script src="js/addImages.js"></script>
<div align="center">

  <div class="to-hide">
    <div style="font-size: 25px"><%out.println(room.getRoomName());%></div>
    <div>
      <br>
      <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
          <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
          <%
            for (int i = 1; i < images.size(); i++) {
              out.println("<li data-target=\"#myCarousel\" data-slide-to=\"" + i + "\"></li>");
            }
          %>

        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner roomimages">
          <div class="item active">
            <img src="<%out.print(images.size()>0?images.get(0):WebConstants.NO_IMAGE);%>" class="room-image">
          </div>

          <%
            for (int i = 1; i < images.size(); i++) {
              out.println(
                      "          <div class=\"item\">\n" +
                              "            <img class=\"room-image\" src=\"" + images.get(i) + "\" >\n" +
                              "          </div>\n"
              );
            }
          %>

        </div>
      </div>
    </div>
    <div id="room-info-div">
      <div>სართული: <%out.print(room.getFloor());%>.</div>
      <div>ოთახის ტიპი: <%out.print(roomTypeToString(room.getRoomType(), true));%>.</div>
      <div>ადგილები: <%out.println(room.getCapacity());%>.</div>
      <div>შეიძლება დაჯავშნა:
        <%
          out.println(room.isAvailableForStudents() ? "<span id=\"yes-span\">კი</span>"
                  : "<span id=\"no-span\">არა</span>");
        %>
      </div>
      <div>ადგილის ტიპი: <%out.print(seatTypeToString(room.getSeatType(), true));%>.</div>
    </div>

    <script type="mustache/x-tmpl" id="bookingtemplate">
      <tr>
        <td><img class="user-picture" src={{booker_pic}} alt=""></td>
        <td>{{subject_name}}</td>
        <td>{{booker}}</td>
        <td>{{start_time}}</td>
      </tr>
    </script>
    <script src="/js/utils.js"></script>
    <script src="/js/weekpicker.js"></script>
    <script src="js/scheduletemplate.js"></script>
    <div class="table-users">
      <div class="header">ლექციები/ჯავშნები</div>
      <div>
        <div class="week-picker"></div>
      </div>
      <div class=schedule>
        <td style="overflow-x: scroll">
          <table>
            <tr>
              <th>ორშაბათი</th>
              <th>სამშაბათი</th>
              <th>ოთხშაბათი</th>
              <th>ხუთშაბათი</th>
              <th>პარასკევი</th>
              <th>შაბათი</th>
              <th>კვირა</th>
            </tr>
            <tr>
              <td>
                <table id="monday">

                </table>
              </td>
              <td>
                <table id="tuesday">
                  <tr>

                  </tr>
                </table>
              </td>
              <td>
                <table id="wednesday">

                </table>
              </td>
              <td>
                <table id="thursday">

                </table>
              </td>
              <td>
                <table id="friday">

                </table>
              </td>
              <td>
                <table id="saturday">

                </table>
              </td>
              <td>
                <table id="sunday">

                </table>
              </td>
            </tr>
          </table>
        </td>
      </div>
    </div>
  </div>

</div>


</body>
</html>
