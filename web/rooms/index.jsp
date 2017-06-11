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
        return s == null || s.length() < 1 ? null : Integer.valueOf(s);

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

    private RoomManager manager;%>
<%
    manager = (RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER);
%>
<html>
<head>
    <title>ოთახები</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

    <table style="height: 100%; width: 100%;">
        <tr>
            <td style="width: 30%; left-margin: 10px;">

                <form style="width: 100%" action="index.jsp"
                    method="post" class="form-vertical">

                        <div class="form-group">
                            <label class="control-label" for="name">ოთახის სახელი</label>
                            <input name="room_name" class="form-control"
                                placeholder="შეიყვანეთ ოთახის სახელი">
                        </div>
                        
                        <div class="form-group">    
                            <label class="control-label" for="floor">სართული</label>
                            <input type="number" name="room_floor" class="form-control"
                                placeholder="შეიყვანეთ სართული">
                        </div>
                        
                        <div class="form-group">
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
                            <select name="room_type" class="form-control">
                                    <option value="any">ყველა</option>
                                    <option value="auditorium">აუდიტორია</option>
                                    <option value="utility">სხვა</option>
                            </select>
                        </div>        
                        
                        <div class="select">
                            <label class="control-label" for="seat_type">ადგილების ტიპი</label>
                            <select name="seat_type" class="form-control">
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
                            <label class="control-label" for="available"> 
                                <input type="checkbox" name="can_be_booked">
                                                                                     შეიძლება სტუდენტისთვის
                            </label>
                        </div>
                        
                        <div class="checkbox">
                            <label class="control-label" for="problems">
                                <input type="checkbox" name="no_problems">
                                                                                      პრობლემების გარეშე
                            </label>
                        </div>
    
                        <input type="submit" value="ძებნა" class="btn btn-default">
                        
                        <input type="hidden" name="search" value="true" class="form-control">
                </form>


            <td style="width: 85%">
                <div align="center"
                    style="overflow-y: scroll; height: 100%;">
                    <style>
                        td, tr {
                            text-align: center;
                            align-items: center;
                        }
                    </style>

                    <table>

                        <%
                        RoomSearchQuery query = new RoomSearchQuery();
                        buildQuery(request, query);
                        List<Room> rooms = manager.findRooms(query);

                        for (Room room : rooms) {
                            out.println("<tr>");
                            out.println("                        <td style=\"font-size: 25px\">" +
                                    "<a href=room.jsp?id="+room.getRoomID()+">" + room.getRoomName() + "<a></td>");
                            out.println("                    </tr>");
                            out.println("                    <tr>");
                            List<String> images = manager.getAllImagesOf(room);
                            String src = images.size() > 0 ? images.get(0) : NO_IMAGE;
                            out.println("                        <td><img src=\"" + src + "\" height=\"60%\"></td>");

                            out.println("                    </tr>");
                            out.println("                    <tr>");
                            out.println("                        <td>");
                            out.println("                            <table style=\"font-size: 17\">");
                            out.println("                                <tr style=\"align-items: center\">");
                            out.println("                                    <td>სართული: " + room.getFloor() + ".</td>");
                            out.println("                                    <td>ოთახის ტიპი: "
                                    + roomTypeToString(room.getRoomType()) + ".</td>");
                            out.println("                                    <td>ადგილები: " + room.getCapacity() + ".</td>");
                            out.println("                                </tr>");
                            out.println("                                <tr style=\"align-text: center\">");
                            out.println("                                    <td>შეიძლება დაჯავშნა: ");
                            out.println(room.isAvailableForStudents() ? "<span style=\"color: #009900\">კი</span>"
                                    : "<span style=\"color: #cc0000\">არა</span>");
                            out.println("</td> ");
                            out.println("                                    <td>ადგილის ტიპი: "
                                    + seatTypeToString(room.getSeatType()) + ".</td>");
                            out.println("                                </tr>");
                            out.println("                                </tr>");
                            out.println("                            </table>");
                            out.println("                        </td>");
                            out.println("                    </tr>");
                            out.println("                    ");

                        } %>
                    </table>

                </div>
            </td>
        </tr>
    </table>
</body>
</html>
