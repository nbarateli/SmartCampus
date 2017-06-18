<%@ page import="misc.WebConstants" %>
<%@ page import="static misc.WebConstants.ROOM_MANAGER" %>
<%@ page import="model.lectures.Lecture" %>
<%@ page import="model.rooms.Room" %>
<%@ page import="model.rooms.manager.RoomManager" %>
<%@ page import="java.util.List" %>
<%@ page import="static misc.Utils.roomTypeToString" %>
<%@ page import="static misc.Utils.toSeatType" %>
<%@ page import="static misc.Utils.seatTypeToString" %><%--
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
  <link rel="stylesheet" href="SearchPageStyle.css">
  <%!
    private void printAll(List<Lecture> allLecturesAt, JspWriter out) throws Exception {
      for (Lecture lecture : allLecturesAt) {
        out.println("<tr>");
        out.println("<td>" + lecture.getSubject().getName() + "</td>");
        out.println("<td>" + lecture.getLecturer().getFirstName() + " " + lecture.getLecturer().getLastName() + "</td>");
        out.println("<td>" + lecture.getDay() + " " + lecture.getStartTime() + "</td>");
        out.println("</tr>");
      }
    }

    Room room;
    List<String> images;
    RoomManager manager;
  %>
  <%

    try {
      manager = ((RoomManager) request.getServletContext()
              .getAttribute(ROOM_MANAGER));
      room = manager.getRoomByID(Integer.valueOf(request.getParameter("id")));
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
<div align="center">

  <div class="to-hide">
    <div style="font-size: 25px"><%out.println(room.getRoomName());%></div>
    <div><img src="<%out.print(images.size()>0?images.get(1):WebConstants.NO_IMAGE);%>" style="width: 25%">
    </div>
    <div id="room-info-div">
      <div>სართული: <%out.print(room.getFloor());%>.</div>
      <div>ოთახის ტიპი: <%out.print(roomTypeToString(room.getRoomType()));%>.</div>
      <div>ადგილები: <%out.println(room.getCapacity());%>.</div>
      <div>შეიძლება დაჯავშნა:
        <%
          out.println(room.isAvailableForStudents() ? "<span id=\"yes-span\">კი</span>"
                  : "<span id=\"no-span\">არა</span>");
        %>
      </div>
      <div>ადგილის ტიპი: <%out.print(seatTypeToString(room.getSeatType()));%>.</div>
    </div>
    <div>ლექციები:</div>
    <style>
      table {
        border-collapse: collapse;
      }

      tr, th, td {
        border: 1px solid;
      }</style>
    <div>
      <table>
        <th>სახელი</th>
        <th>ლექტორი</th>
        <th>დაწყების დრო</th>
        <%
          if (room != null && room.getRoomType() != Room.RoomType.UTILITY) {
            try {
              printAll(manager.findAllLecturesAt(room), out);
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
