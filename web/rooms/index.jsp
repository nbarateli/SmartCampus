<%@ page import="java.util.Random" %>
<%@ page import="model.rooms.manager.RoomManager" %>
<%@ page import="static misc.WebConstants.*" %>
<%@ page import="model.rooms.RoomSearchQuery" %>
<%@ page import="java.util.List" %>
<%@ page import="model.rooms.Room" %><%--
  Created by IntelliJ IDEA.
  User: Niko
  Date: 09.06.2017
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!private RoomManager manager;%>
<%
    manager = (RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER);
%>
<html>
<head>
    <title>ოთახები</title>
</head>
<body>

<table style="height: 100%; width: 100%;">
    <tr>
        <td style="width: 25%">

            <form style="width: 100%" action="index.jsp" method="get">
                <table>
                    <tr>
                        <td>ოთახის სახელი</td>
                        <td><input name="room_name"></td>
                    </tr>
                    <tr>
                        <td>სართული</td>
                        <td><input type="number"
                                   name="room_floor"></td>
                    </tr>
                    <tr>
                        <td>ადგილების რაოდენობა:</td>
                    </tr>
                    <tr>
                        <td><label
                                style="text-align: left; float: left">
                            <input type="number">-დან
                        </label></td>

                        <td><label
                                style="text-align: left; float: left">
                            <input type="number">-მდე
                        </label></td>
                    </tr>
                    <tr>
                        <td>ოთახის ტიპი</td>
                        <td><select name="room_type">
                            <option value="any">ყველა</option>
                            <option value="auditorium">აუდიტორია</option>
                            <option value="utility">სხვა</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>ადგილების ტიპი</td>
                        <td><select name="seat_type">
                            <option value="any">ყველანაირი</option>
                            <option value="desks">სკამები და მერხები</option>
                            <option value="wooden_chair">სკამ-მერხები
                                (ხის)
                            </option>
                            <option value="plastic_chair">სკამ-მერხები
                                (პლასტმასის)
                            </option>
                            <option value="computers">კომპიუტერები</option>
                            <option value="tables">მაგიდები</option>
                        </select></td>
                    </tr>
                    <tr>

                        <td><label>
                            <input type="checkbox" name="can_be_booked">
                        </label>შეიძლება სტუდენტისთვის
                        </td>
                        <td></td>
                    </tr>

                    <tr>

                        <td><label>
                            <input type="checkbox" name="no_problems">
                        </label>პრობლემების გარეშე
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><input type="submit" name="search" value="true"></td>
                    </tr>
                </table>
                <input type="hidden" value="search">
            </form>

        </td>
        <td style="width: 85%">
            <div style="overflow-y: scroll; height: 100%;">
                <table>
                    <%
                        for (int i = 0; i < 40; i++) {
                            out.println("<tr><td>" + "ajjgjgjgjfwafasf" + "</td></tr>");
                        }
                    %>

                </table>

            </div>
        </td>
    </tr>
</table>
</body>
</html>
