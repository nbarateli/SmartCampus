<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>ოთახები</title>
</head>
<body>
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
                    
                    <!-- write search results here -->                    
                    
                    </table>

                </div>
            </td>
        </tr>
    </table>
</body>
</html>
