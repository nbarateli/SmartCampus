<%@ page import="java.util.Random" %>
<%@ page import="model.rooms.manager.RoomManager" %>
<%@ page import="static misc.WebConstants.*" %>
<%@ page import="model.rooms.RoomSearchQuery" %>
<%@ page import="java.util.List" %>
<%@ page import="model.rooms.Room" %>
<%@ page import="static misc.Utils.*" %><%--
  Created by IntelliJ IDEA.
  User: Niko
  Date: 09.06.2017
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%!
    private Integer nullIfNoLength(String s) {
        return s == null || s.length() < 1 ? null : (Integer.valueOf(s) < 0 ? 0 : Integer.valueOf(s));

    }

    private void buildQuery(HttpServletRequest request, RoomSearchQuery query) {
        if (request.getParameter("search") == null) return;
        String name = request.getParameter("room_name");
        String floor = request.getParameter("room_floor");
        String roomType = request.getParameter("room_type");
        String seatType = request.getParameter("seat_type");
        boolean canBeBooked = request.getParameter("can_be_booked") != null;
        query.setName(name.length() > 0 ? name : null);
        query.setFloor(nullIfNoLength(floor));
        query.setCapacityFrom(nullIfNoLength(request.getParameter("capacity_from")));
        query.setCapacityTo(nullIfNoLength(request.getParameter("capacity_to")));
        query.setRoomType(roomType.length() > 0 ? toRoomType(roomType) : null);
        query.setSeatType(seatType.length() > 0 ? toSeatType(seatType) : null);
        query.setAvailableForBooking(canBeBooked);
    }

%>
<%
    RoomManager manager = (RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER);
%>
<html>
<head>
    <title>ოთახები</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"
        href="SearchPageStyle.css">
    <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

    <table id="full-table">
        <tr>
            <td id="left-td">

                <form id="search-form" action="index.jsp"
                    method="get" class="form-vertical" accept-charset="UTF-8">
                    <input type="hidden" name="search" value="true" class="form-control">

                    <div class="form-group">
                            <label class="control-label" for="name">ოთახის სახელი</label>
                            <input type="text" name="room_name" class="form-control" id="name"
                                placeholder="შეიყვანეთ ოთახის სახელი">
                        </div>

                        <div class="form-group">
                            <label class="control-label" for="floor">სართული</label>
                            <input type="number" name="room_floor" class="form-control" id="floor"
                                placeholder="შეიყვანეთ სართული">
                        </div>

                        <div class="form-group" id="capacity">
                            <label class="control-label" for="capacity">
                                                                                    ადგილების რაოდენობა</label>
                            <input type="number" name="capacity_from" class="form-control"
                                title="-დან" placeholder="ადგილების რაოდენობა(-დან)">

                            <br>

                            <input type="number" name="capacity_to" class="form-control"
                                title="-მდე" placeholder="ადგილების რაოდენობა(-მდე)">
                        </div>

                        <div class="select">
                            <label class="control-label" for="room_type">ოთახის ტიპი</label>
                            <select name="room_type" class="form-control" id="room_type">
                                    <option value="any">ყველა</option>
                                    <option value="auditorium">აუდიტორია</option>
                                    <option value="utility">სხვა</option>
                            </select>
                        </div>

                        <div class="select">
                            <label class="control-label" for="seat_type">ადგილების ტიპი</label>
                            <select name="seat_type" class="form-control" id="seat_type">
                                <option value="any">ყველანაირი</option>
                                <option value="desks">სკამები და მერხები</option>
                                <option value="wooden_chair">სკამ-მერხები (ხის)</option>
                                <option value="plastic_chair">სკამ-მერხები
                                    (პლასტმასის)</option>
                                <option value="computers">კომპიუტერები</option>
                                <option value="tables">მაგიდები</option>
                            </select>
                        </div>

                        <div class="checkbox">
                            <label class="control-label" for="can_be_booked">
                                <input type="checkbox" name="can_be_booked" id="can_be_booked">
                                                                                     შეიძლება სტუდენტისთვის
                            </label>
                        </div>

                        <div class="checkbox">
                            <label class="control-label" for="problems">
                                <input type="checkbox" name="no_problems" id="problems">
                                                                                      პრობლემების გარეშე
                            </label>
                        </div>

                        <input type="submit" value="ძებნა" class="btn btn-primary">

                </form>


            <td id="right-td">
                <div align="center" id="right-div">

                    <table>

                        <%
                        RoomSearchQuery query = new RoomSearchQuery();
                        buildQuery(request, query);
                        List<Room> rooms = manager.findRooms(query);
                        System.out.println(rooms.size());
                        for (Room room : rooms) {
                            out.println("<tr>");
                            out.println("<td id=\"room-id-td\">" +
                                    "<a href=room.jsp?id=" + room.getRoomID() + ">"
                                    + room.getRoomName() + "<a></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            List<String> images = manager.getAllImagesOf(room);
                            String src = images.size() > 0 ? images.get(0) : NO_IMAGE;
                            out.println("<td><img src=\"" + src + "\" height=\"60%\"></td>");

                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("<table id=\"room-table\">");
                            out.println("<tr id=\"room-info-tr\">");
                            out.println("<td>სართული: " + room.getFloor() + ".</td>");
                            out.println("<td>ოთახის ტიპი: "
                                    + roomTypeToString(room.getRoomType()) + ".</td>");
                            out.println("<td>ადგილები: " + room.getCapacity() + ".</td>");
                            out.println("</tr>");
                            out.println("<tr id=\"room-info-tr\">");
                            out.println("<td>შეიძლება დაჯავშნა: ");
                            out.println(room.isAvailableForStudents()
                                    ? "<span id=\"yes-span\">კი.</span>"
                                    : "<span id=\"no-span\">არა.</span>");
                            out.println("</td> ");
                            out.println("<td>ადგილის ტიპი: "
                                    + seatTypeToString(room.getSeatType()) + ".</td>");
                            out.println("</tr>");
                            out.println("</tr>");
                            out.println("</table>");
                            out.println("</td>");
                            out.println("</tr>");


                        } %>
                    </table>

                </div>
            </td>
        </tr>
    </table>
</body>
</html>
