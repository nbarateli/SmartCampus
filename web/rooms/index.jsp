<%@ page import="misc.Utils" %>
<%@ page import="model.rooms.RoomManager" %>
<%@ page import="static misc.WebConstants.*" %>
<%@ page import="model.rooms.RoomSearchQueryGenerator" %>
<%@ page import="serve.managers.ManagerFactory" %>
<%@ page import="static misc.Utils.*" %>
<%@ page import="model.rooms.Room" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Niko
  Date: 09.06.2017
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%!
  private Integer nullIfNoLength(String s) {
    return s == null || s.length() < 1 ? null : Integer.valueOf(s) < 0 ? 0 : Integer.valueOf(s);
  }

  private void buildQuery(HttpServletRequest request, RoomSearchQueryGenerator query) {
    if (!"true".equals(request.getParameter("search"))) return;
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
  RoomManager manager1 = ((ManagerFactory) request.getServletContext().getAttribute(MANAGER_FACTORY)).getRoomManager();
%>
<html>
<%
  String url = request.getServletContext().getRealPath("/rooms/roomform.html");
  String content = Utils.getContent(url);

  out.println(content);
%>
<head>
  <title>ოთახები</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="Floors.css">
  <link rel="stylesheet" href="SearchPageStyle.css">

  <script
          src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script
          src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>
  <script src="RoomsSearch.js"></script>
  <script src="Floors.js"></script>
</head>
<body>
<table id="full-table">
  <tr>
    <td id="left-td">

      <form id="search-form" action="index.jsp"
            class="form-vertical">

        <div class="form-group">
          <label class="control-label">ოთახის ინფორმაცია</label>
          <input type="text" name="room_name"
                 class="form-control" placeholder="შეიყვანეთ ოთახის სახელი">
        </div>

        <div class="form-group">
          <input type="number" name="room_floor" class="form-control"
                 placeholder="შეიყვანეთ სართული">
        </div>

        <div class="form-group">
          <input type="number"
                 name="capacity_from" class="form-control" title="-დან"
                 placeholder="ადგილების რაოდენობა(-დან)"> <br>

          <input type="number" name="capacity_to" class="form-control"
                 title="-მდე" placeholder="ადგილების რაოდენობა(-მდე)">
        </div>

        <div class="select">
          <label class="control-label smaller-lab">ოთახის ტიპი</label>
          <select name="room_type" class="form-control">
            <option value="any">ყველა</option>
            <option value="auditorium">აუდიტორია</option>
            <option value="utility">სხვა</option>
          </select>
        </div>

        <div class="select">
          <label class="control-label smaller-lab">ადგილების ტიპი</label>
          <select name="seat_type" class="form-control">
            <option value="any">ყველანაირი</option>
            <option value="desks">სკამები და მერხები</option>
            <option value="chairs">სკამ-მერხები (ხის)</option>
            <option value="computers">კომპიუტერები</option>
            <option value="tables">მაგიდები</option>
          </select>
        </div>

        <div class="checkbox">
          <label class="control-label">
            <input type="checkbox" name="can_be_booked"> შეიძლება სტუდენტისთვის
          </label>
        </div>

        <div class="checkbox">
          <label class="control-label">
            <input type="checkbox" name="no_problems"> პრობლემების გარეშე
          </label>
        </div>

        <div class="form-group">
          <label class="control-label">დროით ძებნა</label>
          <input type="date" name="date_interested" class="form-control">
        </div>

        <div class="form-group">
          <input type="text" name="time_interested" class="form-control"
                 placeholder="შეიყვანეთ დრო (HH:MM 24-საათიანი ფორმატით)">
        </div>

        <input type="submit" value="ძებნა" class="btn btn-primary">
        <input type="hidden" name="search" value="true" class="form-control">
      </form>
      <script>

          function get(name) {
              if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
                  return decodeURIComponent(name[1]);
          }

          var array;
          $.getJSON("findrooms", function (result) {
              for (i in array) {
                  array.append(result);
                  console.log(array[i]);
              }
              array = result;

          });


      </script>

    <td id="right-td">
      <div align="center" id="right-div" style="display:none">
        <%
//          RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
//          buildQuery(request, query);
//          List<Room> rooms = new LinkedList<>();//manager1.find(query);
//
//          for (Room room : rooms) {
//            out.println("<div id=\"room-id-div\">" + "<a href=showroom.jsp?id=" + room.getId() + ">"
//                    + room.getRoomName() + "</a></div>");
//            out.println("<button>ვრცლად</button>");
//
//            List<String> images = manager1.getAllImagesOf(room);
//            String src = images.size() > 0 ? images.get(0) : NO_IMAGE;
//            out.println("<div class=\"to-hide\">");
//            out.println("<div><img src=\"" + src + "\" height=\"60%\"></div>");
//
//            out.println("<div id=\"room-info-div\">");
//            out.println("<div>სართული: " + room.getFloor() + ".</div>");
//            out.println("<div>ოთახის ტიპი: " + roomTypeToString(room.getRoomType(), true) + ".</div>");
//            out.println("<div>ადგილები: " + room.getCapacity() + ".</div>");
//            out.println("<div>შეიძლება დაჯავშნა: ");
//            out.println(room.isAvailableForStudents() ? "<span id=\"yes-span\">კი</span>"
//                    : "<span id=\"no-span\">არა</span>");
//            out.println("</div>");
//            out.println("<div>ადგილის ტიპი: " + seatTypeToString(room.getSeatType(), true) + ".</div>");
//            out.println("</div>");
//            out.println("</div>");
//
//          }
        %>
          <%
              RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
              buildQuery(request,query);
              List<Room> rooms = manager1.find(query);
              for (Room room : rooms) {
          %>
              <script>
                  highlightRooms(<%room.getId();%>);
              </script>
          <%
              }
          %>
      </div>
      <h1 id="par">FLOOR</h1>
      <select name="floors" onchange="floorChange()" id="floors">
        <option value="first">პირველი სართული</option>
        <option value="second">მეორე სართული</option>
        <option value="third">მესამე სართული</option>
        <option value="fourth">მეოთხე სართული</option>
      </select>
    <div class="mapdivision">
        <link rel="stylesheet" href="MapStyles.css">
        <svg version="1.1" id="map1" xmlns="http://www.w3.org/2000/svg"
             x="0px" y="0px"
             viewBox="0 0 2500 1100" style="enable-background:new 0 0 2500 1100;" xml:space="preserve">
          <path class="st0" d="M760,952H166.8V681.8h852.7V628h31v-16h-60V148h169v464h-60v16.5v87.8v-34.2h97.5h389.7V634h40.3v-22h-65V148
	h161v465h-65v22h65v74.9v-27.8h40.5h570.3v165.7L759,848v-42L760,952H603.3V717c51.9,0.2,103.8,0.3,155.7,0.5v41"/>
          <rect id="xST1" x="166.8" y="717" class="room" width="60" height="235"/>
          <polygon id="XMLID_4_" class="st1"
                   points="237.8,699.3 303,699.3 303,716.8 603.3,717 603.3,952 228,952 228,717 237.8,716.8 "/>
          <polygon id="xბიბლიოთეკა" class="room" points="396.8,681.8 396.8,431.8 196.8,431.8 196.8,306.8 611.8,306.8
	611.8,626.8 566.8,626.8 566.8,681.8 "/>
          <path id="XMLID_10_" class="st0" d="M781.6,702.1c202.7,96.6,276.5,101.2,297,75c15.3-19.5,9-66.5-13.5-76.5
	c-17.1-7.6-44.1,5.9-66,33"/>
          <rect id="x101ა" x="990.5" y="497" class="room" width="60" height="115"/>
          <rect id="x102ა" x="1099.5" y="497" class="room" width="60" height="115"/>
          <rect id="x103ა" x="1099.5" y="432.3" class="room" width="60" height="65"/>
          <rect id="x105ა" x="1099.5" y="317.5" class="room" width="60" height="115"/>
          <rect id="x104ა" x="990.5" y="317" class="room" width="60" height="115"/>
          <rect id="x106ა" x="1099.5" y="247.5" class="room" width="60" height="70"/>
          <rect id="x107ა" x="1099.5" y="148" class="room" width="60" height="99.5"/>
          <rect id="x108ა" x="990.5" y="196.3" class="room" width="57.5" height="48.8"/>
          <polyline id="xC_pathxE_" class="st2" points="1587.6,682.1 1197,682.1 1197,848 2273.1,848 "/>
          <rect id="x101" x="1196.6" y="788.1" class="room" width="150" height="60"/>
          <rect id="x102" x="1197" y="682.1" class="room" width="150" height="60"/>
          <rect id="x105" x="1409.7" y="682.1" class="room" width="177" height="60"/>
          <rect id="x114" x="1658" y="498" class="room" width="65" height="115"/>
          <rect id="x115" x="1562" y="497" class="room" width="65" height="115"/>
          <polygon id="x116" class="st0" points="1658,428 1723,427 1723,497 1658,498 "/>
          <rect id="x117" x="1658" y="312.5" class="room" width="65" height="115"/>
          <rect id="x119" x="1658" y="245.6" class="room" width="65" height="66.9"/>
          <rect id="x120" x="1658" y="148" class="room" width="65" height="97.5"/>
          <line class="st2" x1="1627" y1="148" x2="1627" y2="168.7"/>
          <polyline class="st0" points="1562,223.3 1627,223.3 1627,194 "/>
          <rect id="x118" x="1562" y="312.5" class="room" width="65" height="115"/>
          <rect id="x108" x="1763.5" y="682.1" class="room" width="150" height="50"/>
          <rect id="x111" x="1978" y="682.1" class="room" width="150" height="50"/>
          <rect id="x103" x="1346.6" y="788.1" class="room" width="63.1" height="60"/>
          <rect id="x104" x="1406.8" y="788.1" class="room" width="179.8" height="60"/>
          <rect id="x106" x="1586.7" y="788.1" class="room" width="111.3" height="60"/>
          <rect id="x107" x="1763.5" y="788.1" class="room" width="150" height="60"/>
          <rect id="x109" x="1913.5" y="788.1" class="room" width="65.5" height="60"/>
          <rect id="x110" x="1978.3" y="788.1" class="room" width="150" height="60"/>
          <rect id="x112" x="2127.7" y="788.1" class="room" width="116.1" height="60"/>
          <rect id="x113" x="2243.8" y="737.8" class="room" width="90" height="110"/>
          <text transform="matrix(1 0 0 1 181.6 827.65)" class="st3 st4 st5">ST1</text>
          <text transform="matrix(1 0 0 1 445 500.95)" class="room st3 st6 st5">ბიბლიოთეკა</text>
          <text transform="matrix(1 0 0 1 1001.3335 558.1667)" class="room st3 st6 st5">101ა</text>
          <text transform="matrix(1 0 0 1 1109.3003 561.3335)" class="st3 st6 st5">102ა</text>
          <text transform="matrix(0.9683 0 0 1 1110.834 469.3867)" class="st3 st6 st7">103ა</text>
          <text transform="matrix(1 0 0 1 1112.3337 388.333)" class="st3 st6 st5">105ა</text>
          <text transform="matrix(1 0 0 1 1001.8337 384.0002)" class="st3 st6 st5">104ა</text>
          <text transform="matrix(1 0 0 1 1110.333 287.6665)" class="st3 st6 st5">106ა</text>
          <text transform="matrix(1 0 0 1 1109.667 207)" class="st3 st6 st5">107ა</text>
          <text transform="matrix(1 0 0 1 1253.9998 814.334)" class="st3 st6 st5">101</text>
          <text transform="matrix(1 0 0 1 1255.6003 716.334)" class="st3 st6 st5">102</text>
          <text transform="matrix(1 0 0 1 1363.6504 818.7671)" class="st3 st6 st5">103</text>
          <text transform="matrix(1 0 0 1 1481.8828 818.7676)" class="st3 st6 st5">104</text>
          <text transform="matrix(1 0 0 1 1478.7 716.2999)" class="st3 st6 st5">105</text>
          <text transform="matrix(1 0 0 1 1628.333 820.7673)" class="st3 st6 st5">106</text>
          <text transform="matrix(1 0 0 1 1819.1667 822.1003)" class="st3 st6 st5">107</text>
          <text transform="matrix(1 0 0 1 1817 713)" class="st3 st6 st5">108</text>
          <text transform="matrix(1 0 0 1 1931.6663 820.7671)" class="st3 st6 st5">109</text>
          <text transform="matrix(1 0 0 1 2035.3337 818.1006)" class="st3 st6 st5">110</text>
          <text transform="matrix(1 0 0 1 2036.6663 709.866)" class="st3 st6 st5">111</text>
          <text transform="matrix(1 0 0 1 2169.717 822.1011)" class="st3 st6 st5">112</text>
          <text transform="matrix(1 0 0 1 2273.4331 781.6663)" class="st3 st6 st5">113</text>
          <text transform="matrix(1 0 0 1 1577.1667 564.0002)" class="st3 st6 st5">115</text>
          <text transform="matrix(1 0 0 1 1676 564.3335)" class="st3 st6 st5">114</text>
          <text transform="matrix(1 0 0 1 1676.5002 472.7197)" class="st3 st6 st5">116</text>
          <text transform="matrix(1 0 0 1 1674.9998 379.9998)" class="st3 st6 st5">117</text>
          <text transform="matrix(1 0 0 1 1577.1663 380)" class="st3 st6 st5">118</text>
          <text transform="matrix(1 0 0 1 1675.833 282.333)" class="st3 st6 st5">119</text>
          <text transform="matrix(1 0 0 1 1676.5 205.6665)" class="st3 st6 st5">120</text>
          <path class="st0" d="M166.8,681.8"/>
</svg>
        <svg version="1.1" id="map2" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"
             viewBox="50 -310 1800 1500" style="enable-background:new 50 -310 1800 1500;" xml:space="preserve">
<polygon class="st2" points="58.7,1145 1578.7,1144.4 1578.7,959.7 1478.7,959.7 1478.7,904.7 1443.7,904.7 1443.7,895.3
	1413.7,895.3 1413.7,906 1363.7,906 1363.7,959.7 878.4,959.7 878.4,1001.8 878.4,908 839.1,908 839.1,889.6 904.1,889.6
	904.1,704.6 929.1,704.6 929.1,589.3 904.1,589.3 904.1,449.3 839.1,449.3 839.1,114 1363.7,114 1629,114 1629,159.7 1699,159.7
	1699,-140.3 1714,-140.3 1714,-250.3 1274,-250.3 1274,79.7 1205.5,79.7 1245.9,79.7 1245.9,-10.5 904.1,-10.5 743.4,-10.5
	743.4,115 808.4,115 808.4,447.4 743.4,447.4 743.4,889.6 808.4,889.6 808.4,909 763.4,909 763.4,960.3 383.4,960.3 58.7,960.3 "/>
          <path id="x200" class="room" d="M58.7,1145h180v-65.3H212c-2.3-0.1-7.8-0.6-8.6-3.3c0-0.1,0-0.2-0.1-0.3c0-1.9,0-3.9,0-5.8v-110H58.7
	V1145L58.7,1145z"/>
          <path id="C_PathE__1_" class="st2" d="M236.4,1145"/>
          <rect id="x204" x="383.4" y="960.3" class="room" width="155" height="65"/>
          <rect id="x203" x="383.4" y="1080" class="room" width="155" height="65"/>
          <rect id="x201" x="238.7" y="1079.7" class="room" width="75" height="65"/>
          <rect id="x202" x="313.7" y="1079.7" class="room" width="69.7" height="65"/>
          <rect id="x205" x="538.4" y="1080" class="room" width="70" height="65"/>
          <rect id="x207" x="608.4" y="960.3" class="room" width="155" height="65"/>
          <rect id="x206" x="608.4" y="1080" class="room" width="155" height="65"/>
          <rect id="x208" x="763.4" y="1080" class="room" width="115" height="65"/>
          <rect id="x209" x="948.4" y="1079.6" class="room" width="40" height="65"/>
          <rect id="x218" x="1478.7" y="1019.4" class="room" width="100" height="125"/>
          <rect id="x217" x="1363.7" y="1079.5" class="room" width="115" height="65.1"/>
          <rect id="x210" x="948.4" y="960.2" class="room" width="40" height="65"/>
          <rect id="x212" x="988.4" y="960.2" class="room" width="115" height="65"/>
          <rect id="x211" x="988.4" y="1079.6" class="room" width="115" height="65"/>
          <rect id="x213" x="1103.4" y="1079.6" class="room" width="75" height="65"/>
          <rect id="x214" x="1178.4" y="1079.6" class="room" width="185.3" height="65"/>
          <rect id="x215" x="1178.4" y="959.7" class="room" width="40" height="65"/>
          <rect id="x216" x="1218.4" y="959.7" class="room" width="145.3" height="65"/>
          <rect id="x220" x="743.4" y="774.6" class="room" width="65" height="115"/>
          <rect id="x219" x="839.1" y="774.6" class="room" width="65" height="115"/>
          <rect id="x221" x="839.1" y="704.6" class="room" width="65" height="70"/>
          <rect id="x222" x="839.1" y="589.3" class="room" width="90" height="115"/>
          <rect id="x224" x="839.1" y="519" class="room" width="65" height="70"/>
          <rect id="x225" x="839.1" y="449.3" class="room" width="65" height="70"/>
          <line class="st2" x1="743.4" y1="519.3" x2="808.4" y2="519.3"/>
          <rect id="x223" x="743.4" y="589" class="room" width="65" height="115"/>
          <path class="st0" d="M2072.4,570.3"/>
          <line class="st2" x1="743.4" y1="704" x2="743.4" y2="774.6"/>
          <rect id="xB1" x="904.1" y="-10.5" class="st2" width="140" height="60"/>
          <rect id="xტ1_x2C_4" x="1044.1" y="-10.5" class="st2" width="140" height="60"/>
          <polygon id="xკულინარიის_x5F_აკადემია" class="st2" points="1274,79.7 1363.7,79.7 1363.7,114
	1629,114 1629,159.7 1699,159.7 1699,89.7 1629,89.7 1699,89.7 1699,-140.3 1714,-140.3 1714,-250.3 1274,-250.3 "/>
          <path class="st2" d="M904.1,449.3"/>
          <text transform="matrix(1 0 0 1 104 1077)" class="st6 st7">200</text>
          <text transform="matrix(1 0 0 1 259.2 1119.1)" class="st6 st7">201</text>
          <text transform="matrix(1 0 0 1 324.2001 1116.6)" class="st6 st7">202</text>
          <text transform="matrix(1 0 0 1 437.9 1117.1)" class="st6 st7">203</text>
          <text transform="matrix(1 0 0 1 440.9 998)" class="st6 st7">204</text>
          <text transform="matrix(1 0 0 1 552 1114)" class="st6 st7">205</text>
          <text transform="matrix(1 0 0 1 661.9 1116.1)" class="st6 st7">206</text>
          <text transform="matrix(1 0 0 1 658.9 998.2)" class="st6 st7">207</text>
          <text transform="matrix(1 0 0 1 800.9 1113.5)" class="st6 st7">208</text>
          <text transform="matrix(2.244830e-010 -1 1 2.244830e-010 975.8965 1126.6016)" class="st6 st7">209</text>
          <text transform="matrix(2.244830e-010 -1 1 2.244830e-010 976.8965 1012.3027)" class="st6 st7">210</text>
          <text transform="matrix(1 0 0 1 1022.9 1001.8)" class="st6 st7">212</text>
          <text transform="matrix(1 0 0 1 1024.9 1116.1)" class="st6 st7">211</text>
          <text transform="matrix(1 0 0 1 1119.2334 1113.5833)" class="st6 st7">213</text>
          <text transform="matrix(1 0 0 1 1245.9166 1111.5333)" class="st6 st7">214</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 1205.498 1008.8691)" class="st6 st7">215</text>
          <text transform="matrix(1 0 0 1 1401.1996 1118.0005)" class="st6 st7">217</text>
          <text transform="matrix(1 0 0 1 1261 1000.6664)" class="st6 st7">216</text>
          <text transform="matrix(1 0 0 1 1499.667 1088.667)" class="st6 st7">218</text>
          <text transform="matrix(1 0 0 1 757.6665 832)" class="st6 st7">220</text>
          <text transform="matrix(1 0 0 1 853 830)" class="st6 st7">219</text>
          <text transform="matrix(1 0 0 1 853.6665 740.667)" class="st6 st7">221</text>
          <text transform="matrix(1 0 0 1 855.7332 649.333)" class="st6 st7">222</text>
          <text transform="matrix(1 0 0 1 754.5668 648)" class="st6 st7">223</text>
          <text transform="matrix(1 0 0 1 854.2001 564.6667)" class="st6 st7">224</text>
          <text transform="matrix(1 0 0 1 852.9333 489.6333)" class="st6 st7">225</text>
          <text transform="matrix(1 0 0 1 962.7664 29.3333)" class="st6 st7">B1</text>
          <text transform="matrix(1 0 0 1 1091 25.3335)" class="st6 st7">ტ1,4</text>
          <text transform="matrix(1 0 0 1 1369.6666 -60.6667)" class="st6 st7">კულინარიის აკადემია</text>
</svg>
        <svg version="1.1" id="map3" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"
             viewBox="0 -360 1700 1600" style="enable-background:new 0 -360 1700 1600;" xml:space="preserve">
   <polyline class="st0" points="1418,875 1418,825 1318,825 1318,770 1288,770 1288,755 1258,755 1258,770 1203,770 1203,770
	1258,770 1258,755 1288,755 1288,770 1318,770 1318,825 1418,825 1418,995 128,995 128,930 88,930 88,765 198,765 198,870 198,825
	258,825 413,825 483,825 633,825 633,765 678,765 678,755 678,754.7 613,754.7 613,330.5 772.8,330.5 772.8,460.7 797.8,460.3
	797.8,575.3 773,575 773,754.7 708,754.7 708,765 748,765 748,825 "/>
          <rect id="x301" x="128" y="930" class="room" width="65" height="65"/>
          <rect id="x302" x="193" y="930" class="room" width="65" height="65"/>
          <rect id="x303" x="258" y="930" class="room" width="155" height="65"/>
          <rect id="x306" x="413" y="930" class="room" width="70" height="65"/>
          <rect id="x307-1" x="483" y="930" class="room" width="75" height="65"/>
          <rect id="x307-2" x="558" y="930" class="room" width="75" height="65"/>
          <rect id="x310" x="633" y="930" class="room" width="115" height="65"/>
          <line id="XMLID_4_" class="st2" x1="748" y1="995" x2="828" y2="995"/>
          <rect id="x311" x="828" y="930" class="room" width="35" height="65"/>
          <rect id="x312" x="828" y="825" class="room" width="35" height="65"/>
          <rect id="x313" x="863" y="930" class="room" width="115" height="65"/>
          <rect id="x315" x="978" y="930" class="room" width="70" height="65"/>
          <rect id="x316" x="1048" y="930" class="room" width="40" height="65"/>
          <rect id="x317" x="1048" y="825" class="room" width="40" height="65"/>
          <rect id="x318" x="1088" y="930" class="room" width="115" height="65"/>
          <rect id="x319" x="1088" y="825" class="room" width="115" height="65"/>
          <rect id="x320" x="1203" y="930" class="room" width="115" height="65"/>
          <rect id="x321" x="1318" y="875" class="room" width="100" height="120"/>
          <rect id="x314" x="863" y="825" class="room" width="115" height="65"/>
          <polyline id="XMLID_5_" class="st0" points="828,825 748,825 748,869 748,825 748,765 708,765 708,755 "/>
          <rect id="x304" x="258" y="825" class="room" width="40" height="65"/>
          <rect id="x305" x="298" y="825" class="room" width="115" height="65"/>
          <rect id="x308" x="483" y="825" class="room" width="40" height="65"/>
          <rect id="x309" x="523" y="825" class="room" width="110" height="65"/>
          <rect id="x323" x="613" y="639.7" class="room" width="65" height="115"/>
          <rect id="x322" x="708" y="639.7" class="room" width="65" height="115"/>
          <rect id="x324" x="708" y="575" class="room" width="65" height="65"/>
          <rect id="x325" x="707.8" y="460.3" class="room" width="90" height="115"/>
          <rect id="x327" x="707.8" y="395.7" class="room" width="65" height="65"/>
          <rect id="x328" x="707.8" y="330.5" class="room" width="65" height="65"/>
          <rect id="XMLID_1_" x="678" y="45.6" class="passage" width="29.8" height="285"/>
          <line id="XMLID_10_" class="st2" x1="613" y1="639.7" x2="613" y2="575"/>
          <rect id="x326" x="613" y="460.3" class="room" width="65" height="115"/>
          <polyline id="XMLID_11_" class="st2" points="613,460.3 613,395.5 678,395.5 "/>
          <polyline id="XMLID_12_" class="st2" points="678,330.6 613,330.5 613,395.5 "/>
          <polyline id="XMLID_13_" class="st2" points="783,-114.4 623,-114.4 623,45.6 783,45.6 "/>
          <rect id="x139" x="782.5" y="-114.4" class="room" width="280" height="65"/>
          <rect id="x88" x="782.5" y="-19.4" class="room" width="280" height="65"/>
          <line id="XMLID_16_" class="st2" x1="782.5" y1="-49.4" x2="782.5" y2="-39.5"/>
          <rect id="XMLID_14_" x="1062.8" y="-19.4" class="st2" width="70" height="65"/>
          <polyline id="XMLID_15_" class="st2" points="1062.5,-114.4 1128,-114.4 1128,-49.4 1153,-49.4 1153,-114.4 "/>
          <polyline id="XMLID_18_" class="st2" points="1132.8,-19.4 1153,-19.4 1153,45.6 1132.8,45.6 "/>
          <path id="xკაფეტერია" class="cafeteria"
                d="M1153,60.7h420V-4h10c0-230,0-230,0-230h20v-70h-85h85v-30h-450V60.7z"/>
          <text transform="matrix(1 0 0 1 141.5 970)" class="st6 st7">301</text>
          <text transform="matrix(1 0 0 1 203.5 969.5)" class="st6 st7">302</text>
          <text transform="matrix(1 0 0 1 313.5 969.5)" class="st6 st7">303</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 287.498 876.502)" class="st6 st7">304</text>
          <text transform="matrix(1 0 0 1 316 862)" class="st6 st7">305</text>
          <text transform="matrix(1 0 0 1 433 967.5)" class="st6 st7">306</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 508.498 875.002)" class="st6 st7">308</text>
          <text transform="matrix(1 0 0 1 492.5 966)" class="st6 st7">307-1</text>
          <text transform="matrix(1 0 0 1 563 965.5)" class="st6 st7">307-2</text>
          <text transform="matrix(1 0 0 1 547 864)" class="st6 st7">309</text>
          <text transform="matrix(1 0 0 1 650 964)" class="st6 st7">310</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 854.998 871.002)" class="st6 st7">312</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 852.498 979.002)" class="st6 st7">311</text>
          <text transform="matrix(1 0 0 1 899 865)" class="st6 st7">314</text>
          <text transform="matrix(1 0 0 1 896 962)" class="st6 st7">313</text>
          <text transform="matrix(1 0 0 1 994 964)" class="st6 st7">315</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 1074.498 878.502)" class="st6 st7">317</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 1078.9961 984.002)" class="st6 st7">316</text>
          <text transform="matrix(1 0 0 1 1125 963)" class="st6 st7">318</text>
          <text transform="matrix(1 0 0 1 1120.5 861)" class="st6 st7">319</text>
          <text transform="matrix(1 0 0 1 1236.5 971.5)" class="st6 st7">320</text>
          <text transform="matrix(1 0 0 1 1353 932)" class="st6 st7">321</text>
          <text transform="matrix(1 0 0 1 630 701)" class="st6 st7">323</text>
          <text transform="matrix(1 0 0 1 731 703.2)" class="st6 st7">322</text>
          <text transform="matrix(1 0 0 1 730.8 519)" class="st6 st7">325</text>
          <text transform="matrix(1 0 0 1 728 616.35)" class="st6 st7">324</text>
          <text transform="matrix(1 0 0 1 632.5 528)" class="st6 st7">326</text>
          <text transform="matrix(1 0 0 1 731 426)" class="st6 st7">327</text>
          <text transform="matrix(1 0 0 1 725.8 368)" class="st6 st7">328</text>
          <text transform="matrix(1 0 0 1 889 -85)" class="st6 st7">139</text>
          <text transform="matrix(1 0 0 1 884 25)" class="st6 st7">88</text>
          <text transform="matrix(1 0 0 1 1280 -121)" class="st6 st7">კაფეტერია</text>
          <polyline class="st2" points="1203,770 1203,825 1048,825 748,825 "/>
</svg>
        <svg version="1.1" id="map4" xmlns="http://www.w3.org/2000/svg"
             x="0px"
             y="0px"
             viewBox="-399 111 1700 880" style="enable-background:new -399 111 1700 880;" xml:space="preserve">
          <rect id="x400" x="-265.4" y="711.8" class="room" width="260" height="190"/>
          <rect id="x401" x="-5.2" y="836.8" class="room" width="70" height="65"/>
          <rect id="x402-1" x="64.8" y="836.8" class="room" width="75" height="65"/>
          <rect id="x402-2" x="139.8" y="836.8" class="room" width="75" height="65"/>
          <rect id="x403-1" x="64.8" y="711.8" class="room" width="110" height="65"/>
          <rect id="x403-2" x="174.8" y="711.8" class="room" width="40" height="65"/>
          <rect id="x404" x="214.8" y="836.8" class="room" width="35" height="65"/>
          <rect id="x405" x="249.8" y="836.8" class="room" width="35" height="65"/>
          <rect id="x406" x="284.8" y="836.8" class="room" width="160" height="65"/>
          <rect id="x407-1" x="284.8" y="711.8" class="room" width="80" height="65"/>
          <rect id="x407-2" x="364.8" y="711.8" class="room" width="80" height="65"/>
          <rect id="x408" x="444.8" y="836.8" class="room" width="115" height="65"/>
          <rect id="xtoilet" x="559.8" y="836.8" class="st0" width="80" height="65"/>
          <rect id="x409" x="639.8" y="836.8" class="room" width="155" height="65"/>
          <rect id="x410" x="639.8" y="711.8" class="room" width="155" height="65"/>
          <rect id="x411" x="794.8" y="836.8" class="room" width="75" height="65"/>
          <rect id="x412-1" x="869.8" y="836.8" class="room" width="75" height="65"/>
          <rect id="x412-2" x="944.8" y="836.8" class="room" width="75" height="65"/>
          <rect id="x413-2" x="869.8" y="711.8" class="room" width="45" height="65"/>
          <rect id="x413-1" x="914.8" y="711.8" class="room" width="105" height="65"/>
          <rect id="x414" x="1019.8" y="836.8" class="room" width="115" height="65"/>
          <rect id="x415" x="1134.8" y="781.8" class="room" width="100" height="120"/>
          <polyline id="XMLID_2_" class="st0"
                    points="1234.8,781.8 1234.8,711.8 1134.8,711.8 1134.8,656.7 1019.8,656.7 1019.8,711.8 "/>
          <line id="XMLID_3_" class="st1" x1="869.8" y1="711.8" x2="794.8" y2="711.8"/>
          <line id="XMLID_4_" class="st1" x1="214.8" y1="711.8" x2="284.8" y2="711.8"/>
          <line id="XMLID_5_" class="st1" x1="64.8" y1="711.8" x2="-5.4" y2="711.8"/>
          <polyline id="XMLID_6_" class="st0"
                    points="639.8,711.8 559.8,711.8 559.8,757 559.8,657 519.8,657 519.8,642 "/>
          <polyline id="XMLID_1_" class="st0" points="444.8,711.8 444.8,657 495,657 495,642 "/>
          <rect id="x416" x="519.8" y="527" class="room" width="65" height="115"/>
          <rect id="x417" x="430" y="527" class="room" width="65" height="115"/>
          <rect id="x418" x="519.8" y="492.5" class="room" width="65" height="35"/>
          <rect id="x419" x="519.8" y="459" class="room" width="65" height="35"/>
          <rect id="x420" x="519.8" y="349" class="room" width="90" height="110"/>
          <rect id="x421" x="430" y="349" class="room" width="65" height="110"/>
          <line id="XMLID_9_" class="st1" x1="430" y1="458" x2="430" y2="527"/>
          <rect id="x422" x="519.8" y="284.5" class="room" width="65" height="65"/>
          <rect id="x423" x="519.8" y="219.5" class="room" width="65" height="65"/>
          <polyline id="XMLID_7_" class="st0" points="519.8,219.5 430,219.5 430,349 "/>
          <polyline id="XMLID_8_" class="st0" points="430,284.3 495,284.3 495,234 "/>
          <text transform="matrix(1 0 0 1 -162.4 814.8)" class="st6 st7">400</text>
          <text transform="matrix(1 0 0 1 14 874.3)" class="st6 st7">401</text>
          <text transform="matrix(1 0 0 1 79.3 872)" class="st6 st7">402-1</text>
          <text transform="matrix(1 0 0 1 145.3 870.3)" class="st6 st7">402-2</text>
          <text transform="matrix(1 0 0 1 90 740)" class="st6 st7">403-1</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 204.8448 774.0488)" class="st6 st7">403-2</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 239.4979 883.8027)" class="st6 st7">404</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 274.4979 882.8027)" class="st6 st7">405</text>
          <text transform="matrix(1 0 0 1 354.8 879)" class="st6 st7">406</text>
          <text transform="matrix(1 0 0 1 296 747)" class="st6 st7">407-1</text>
          <text transform="matrix(1 0 0 1 378 747)" class="st6 st7">407-2</text>
          <text transform="matrix(1 0 0 1 472 880.3)" class="st6 st7">408</text>
          <text transform="matrix(1 0 0 1 698.3 867)" class="st6 st7">409</text>
          <text transform="matrix(1 0 0 1 701 747)" class="st6 st7">410</text>
          <text transform="matrix(1 0 0 1 819 870)" class="st6 st7">411</text>
          <text transform="matrix(1 0 0 1 877 871)" class="st6 st7">412-1</text>
          <text transform="matrix(1 0 0 1 954 872)" class="st6 st7">412-2</text>
          <text transform="matrix(4.489659e-011 -1 1 4.489659e-011 898.645 773.3496)" class="st6 st7">413-2</text>
          <text transform="matrix(1 0 0 1 940.3 752)" class="st6 st7">413-2</text>
          <text transform="matrix(1 0 0 1 1052 867)" class="st6 st7">414</text>
          <text transform="matrix(1 0 0 1 1165 848)" class="st6 st7">415</text>
          <text transform="matrix(1 0 0 1 543 599)" class="st6 st7">416</text>
          <text transform="matrix(1 0 0 1 449.5 595.5)" class="st6 st7">417</text>
          <text transform="matrix(1 0 0 1 535.8 517)" class="st6 st7">418</text>
          <text transform="matrix(1 0 0 1 537.3 479)" class="st6 st7">419</text>
          <text transform="matrix(1 0 0 1 545.8 409)" class="st6 st7">420</text>
          <text transform="matrix(1 0 0 1 442.5 408)" class="st6 st7">421</text>
          <text transform="matrix(1 0 0 1 543 318)" class="st6 st7">422</text>
          <text transform="matrix(1 0 0 1 545.8 261.15)" class="st6 st7">423</text>
</svg>
      </div>
    </td>
  </tr>
</table>
<div id="myModal" class="modal">
  <div class="modal-content" id="mod-cont">
    <span id="closeModal" class="close">&times;</span>
    <div id="roomForm"></div>
  </div>
</div>
</body>
</html>