<%@ page import="misc.WebConstants" %>
<%@ page import="model.accounts.AccountManager" %>
<%@ page import="model.accounts.User" %>
<%@ page import="model.bookings.Booking" %>
<%@ page import="model.lectures.CampusSubject" %>
<%@ page import="static misc.Utils.roomTypeToString" %>
<%@ page import="static misc.Utils.toSeatType" %>
<%@ page import="static misc.Utils.toGeorgian" %>
<%@ page import="static misc.Utils.seatTypeToString" %>
<%@ page import="static misc.WebConstants.MANAGER_FACTORY" %>
<%@ page import="model.rooms.Room" %>
<%@ page import="model.rooms.RoomManager" %>
<%@ page import="static misc.Utils.*" %>
<%@ page import="serve.managers.ManagerFactory" %>
<%@ page import="static misc.WebConstants.SIGNED_ACCOUNT" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
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
  <%--<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">--%>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

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


    private void printAll(List<Booking> bookings, JspWriter out) throws Exception {
      Time start = new Time(0, 0, 0);
      Time end = new Time(23, 59, 59);
      int x = 0;
      Booking last = null;
      if(bookings.size() == 0){
        out.println("<td>" + " Free " + "</td>");
        out.println("<td>" + " empty " + "</td>");
        out.println("<td>"+  start + "</td>");
        out.println("<td>"+  end + "</td>");
        out.println("<td><input type=\"submit\" value=\"დაჯავშნა\"></td>");
      }
      for (int i = 0; i < bookings.size(); i++){
        Booking booking = bookings.get(i);

        if(i != 0){
          SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
          Date date1 = format.parse(last.getEndTime().toString());
          Date date2 = format.parse(booking.getStartTime().toString());
          long difference = date2.getTime() - date1.getTime();
          if(difference/1000 > 1800){
            out.println("<td>" + " Free " + "</td>");
            out.println("<td>" + " empty " + "</td>");
            out.println("<td>"+  last.getEndTime() + "</td>");
            out.println("<td>"+  booking.getStartTime() + "</td>");
            out.println("<td><input type=\"submit\" value=\"დაჯავშნა\"></td>");
          }
        }

        if(i == 0 && !start.equals(booking.getStartTime())){
          out.println("<td>" + " Free " + "</td>");
          out.println("<td>" + " empty " + "</td>");
          out.println("<td>"+  start + "</td>");
          out.println("<td>"+  booking.getStartTime() + "</td>");
          out.println("<td><input type=\"submit\" value=\"დაჯავშნა\"></td>");
        }
        CampusSubject subject = booking.getSubject();
        out.println("<tr><form action=\"/lectures/removelecture\" method=\"post\">");
        out.println("<input type=\"hidden\" value=\"" + booking.getId() + "\" name=\"lecture_id\">");
        out.println("<td>" + (subject == null ? "Student Booking" : "Lecture: " + subject.getName()) + "</td>");
        out.println("<td>" + booking.getBooker().getFirstName() + " "
                + booking.getBooker().getLastName() + "</td>");
        out.println("<td>"+  booking.getStartTime() + "</td>");
        out.println("<td>"+  booking.getEndTime() + "</td>");
        //out.println("<td>" + exactDateToString(booking.getStartDate()) + "</td>");
        out.println("<td><input type=\"submit\" value=\"წაშლა\"></td>");
        out.println("</tr>");
        if(i == bookings.size() - 1 && !start.equals(booking.getEndTime())){
          out.println("<td>" + " Free " + "</td>");
          out.println("<td>" + " empty " + "</td>");
          out.println("<td>"+  booking.getEndTime() + "</td>");
          out.println("<td>"+  end + "</td>");
          out.println("<td><input type=\"submit\" value=\"დაჯავშნა\"></td>");
        }


        last = booking;
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
  <title><%
    out.println(room == null ? "Error" : room.getRoomName());
  %>
  </title>
</head>
<body style="background-color: #bbbbbb">
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
    <div><img src="<%out.print(images.size()>0?images.get(0):WebConstants.NO_IMAGE);%>" style="width: 25%">
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
    <div>ლექციები:</div>

    <div>
      <table>
        <tr>
          <th>  Lecture/Student booking  </th>
          <th>  Booker  </th>
          <th>  Start time  </th>
          <th>  end time  </th>
        </tr>
        <%
          if (room != null && room.getRoomType() != Room.RoomType.UTILITY) {
            try {
              printAll(manager.findAllBookingsAt(room), out);
            } catch (Exception e) {

            }
          }
        %>
      </table>
    </div>
  </div>

</div>


</body>
</html>
