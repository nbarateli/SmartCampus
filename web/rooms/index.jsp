<%@ page import="java.util.Random" %><%--
  Created by IntelliJ IDEA.
  User: Niko
  Date: 09.06.2017
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!Random r = new Random(System.currentTimeMillis());%>
<html>
<head>
<title>ოთახები</title>
</head>
<body>
    <%!String randomString(int l) {
        char[] s = new char[l];
        for (int i = 0; i < l; i++) {
            s[i] = (char) ('a' + (char) (Math.abs(r.nextInt()) % 26));
        }
        return new String(s);
    }%>
    <table style="height: 100%; width: 100%;">
        <tr>
            <td style="width: 25%">

                <form style="width: 100%" action="searchRoomServlet" method="post">
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
                                        (ხის)</option>
                                    <option value="plastic_chair">სკამ-მერხები
                                        (პლასტმასის)</option>
                                    <option value="computers">კომპიუტერები</option>
                                    <option value="tables">მაგიდები</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td>შეიძლება სტუდენტისთვის</td>
                            <td><input type="checkbox"></td>
                        </tr>
                        <tr>
                            <td>შეიძლება სტუდენტისთვის</td>
                            <td><input type="checkbox"></td>
                        </tr>
                        <tr>
                            <td>პრობლემების გარეშე</td>
                            <td><input type="checkbox"></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="search"></td>
                        </tr>
                    </table>

                </form>

            </td>
            <td style="width: 85%">
                <div style="overflow-y: scroll; height: 100%;">
                    <table>
                        <%
                        for (int i = 0; i < 40; i++) {
                            out.println("<tr><td>" + randomString(10) + "</td></tr>");
                        }
                        %>

                    </table>

                </div>
            </td>
        </tr>
    </table>
</body>
</html>
