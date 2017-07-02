<%@ page import="misc.Utils" %>
<%@ page import="model.rooms.RoomManager" %>
<%@ page import="static misc.WebConstants.*" %>
<%@ page import="model.rooms.RoomSearchQueryGenerator" %>
<%@ page import="serve.managers.ManagerFactory" %>
<%@ page import="static misc.Utils.*" %>
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
  <link rel="stylesheet" href="SearchPageStyle.css">
  <link rel="stylesheet" href="Floors.css">
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
          <label class="control-label">ოთახის სახელი</label>
          <input type="text" name="room_name"
                 class="form-control" placeholder="შეიყვანეთ ოთახის სახელი">
        </div>

        <div class="form-group">
          <label class="control-label">სართული</label>
          <input type="number" name="room_floor" class="form-control"
                 placeholder="შეიყვანეთ სართული">
        </div>

        <div class="form-group">
          <label class="control-label">ადგილების რაოდენობა</label>
          <input type="number"
                 name="capacity_from" class="form-control" title="-დან"
                 placeholder="ადგილების რაოდენობა(-დან)"> <br>

          <input type="number" name="capacity_to" class="form-control"
                 title="-მდე" placeholder="ადგილების რაოდენობა(-მდე)">
        </div>

        <div class="select">
          <label class="control-label">ოთახის ტიპი</label>
          <select name="room_type" class="form-control">
            <option value="any">ყველა</option>
            <option value="auditorium">აუდიტორია</option>
            <option value="utility">სხვა</option>
          </select>
        </div>

        <div class="select">
          <label class="control-label">ადგილების ტიპი</label>
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
      </div>
      <h1 id="par">FLOOR</h1>
      <select name="floors" onchange="floorChange()" id="floors">
        <option value="first">პირველი სართული</option>
        <option value="second">მეორე სართული</option>
        <option value="third">მესამე სართული</option>
        <option value="fourth">მეოთხე სართული</option>
      </select>
      <div>
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
          <text transform="matrix(1 0 0 1 445 500.95)" class="st3 st6 st5">ბიბლიოთეკა</text>
          <text transform="matrix(1 0 0 1 1001.3335 558.1667)" class="st3 st6 st5">101ა</text>
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

        <img src="map2-3.jpg" width="640" height="390" alt="map2" usemap="#map2" style="display:none" id="imgmap2"/>
        <map name="map2" id="map2">
          <area id="200" shape="rect" coords="2,293,75,384"/>
          <area id="201" shape="rect" coords="75,352,107,384"/>
          <area id="202" shape="rect" coords="108,353,139,385"/>
          <area id="203" shape="rect" coords="140,352,207,384"/>
          <area id="204" shape="rect" coords="139,293,207,328"/>
          <area id="205" shape="rect" coords="204,352,237,384"/>
          <area id="206" shape="rect" coords="237,352,304,384"/>
          <area id="207" shape="rect" coords="238,292,303,330"/>
          <area id="208" shape="rect" coords="304,352,354,384"/>
          <area id="209" shape="rect" coords="384,352,402,387"/>
          <area id="210" shape="rect" coords="383,292,401,332"/>
          <area id="211" shape="rect" coords="400,352,450,386"/>
          <area id="212" shape="rect" coords="401,295,450,330"/>
          <area id="213" shape="rect" coords="452,352,483,384"/>
          <area id="214" shape="rect" coords="482,351,550,388"/>
          <area id="215" shape="rect" coords="483,293,498,333"/>
          <area id="216" shape="rect" coords="499,294,550,333"/>
          <area id="217" shape="rect" coords="550,353,598,385"/>
          <area id="218" shape="rect" coords="599,322,638,388"/>
          <area id="219" shape="rect" coords="336,195,367,254"/>
          <area id="220" shape="rect" coords="292,191,320,256"/>
          <area id="221" shape="rect" coords="336,152,366,192"/>
          <area id="222" shape="rect" coords="336,85,379,149"/>
          <area id="223" shape="rect" coords="292,86,322,148"/>
          <area id="224" shape="rect" coords="336,43,366,84"/>
          <area id="225" shape="rect" coords="335,3,366,42"/>
        </map>
        <img src="map3-2.jpg" width="640" height="390" alt="map3" usemap="#map3" style="display:none" id="imgmap3"/>
        <map name="map3" id="map3">
          <area id="301" shape="rect" coords="32,351,67,386"/>
          <area id="302" shape="rect" coords="67,351,102,387"/>
          <area id="303" shape="rect" coords="102,352,173,386"/>
          <area id="304" shape="rect" coords="101,295,118,332"/>
          <area id="305" shape="rect" coords="119,296,172,331"/>
          <area id="306" shape="rect" coords="173,352,207,386"/>
          <area id="307-1" shape="rect" coords="206,352,243,386"/>
          <area id="307-2" shape="rect" coords="243,352,278,387"/>
          <area id="308" shape="rect" coords="205,297,224,330"/>
          <area id="309" shape="rect" coords="225,295,279,330"/>
          <area id="310" shape="rect" coords="278,352,329,387"/>
          <area id="311" shape="rect" coords="365,352,384,387"/>
          <area id="312" shape="rect" coords="362,292,382,331"/>
          <area id="313" shape="rect" coords="385,352,435,387"/>
          <area id="314" shape="rect" coords="383,293,437,330"/>
          <area id="315" shape="rect" coords="436,354,471,387"/>
          <area id="316" shape="rect" coords="472,354,489,386"/>
          <area id="317" shape="rect" coords="471,293,487,331"/>
          <area id="318" shape="rect" coords="491,353,541,388"/>
          <area id="319" shape="rect" coords="487,294,541,330"/>
          <area id="320" shape="rect" coords="540,352,591,386"/>
          <area id="321" shape="rect" coords="591,320,638,387"/>
          <area id="322" shape="rect" coords="314,192,347,256"/>
          <area id="323" shape="rect" coords="265,192,299,256"/>
          <area id="324" shape="rect" coords="314,150,347,191"/>
          <area id="325" shape="rect" coords="314,88,357,149"/>
          <area id="326" shape="rect" coords="265,84,299,149"/>
          <area id="327" shape="rect" coords="314,47,346,86"/>
          <area id="328" shape="rect" coords="314,3,346,46"/>
        </map>
        <img src="map4.jpg" width="640" height="390" alt="map4" usemap="#map4" style="display:none" id="imgmap4"/>
        <map name="map4" id="map4">
          <area id="400" shape="rect" coords="2,90,108,388"/>
          <area id="401" shape="rect" coords="109,351,140,386"/>
          <area id="402-1" shape="rect" coords="140,353,176,387"/>
          <area id="402-2" shape="rect" coords="174,354,206,387"/>
          <area id="403-1" shape="rect" coords="139,291,187,330"/>
          <area id="403-2" shape="rect" coords="187,292,206,330"/>
          <area id="404" shape="rect" coords="206,352,223,387"/>
          <area id="405" shape="rect" coords="224,352,240,387"/>
          <area id="406" shape="rect" coords="240,352,303,386"/>
          <area id="407-1" shape="rect" coords="238,292,270,332"/>
          <area id="407-2" shape="rect" coords="271,292,303,331"/>
          <area id="408" shape="rect" coords="304,352,352,386"/>
          <area id="409" shape="rect" coords="384,351,450,388"/>
          <area id="410" shape="rect" coords="382,296,450,330"/>
          <area id="411" shape="rect" coords="450,352,482,387"/>
          <area id="412" shape="rect" coords="481,352,546,387"/>
          <area id="413-1" shape="rect" coords="482,296,528,333"/>
          <area id="413-2" shape="rect" coords="530,296,546,332"/>
          <area id="414" shape="rect" coords="546,352,594,387"/>
          <area id="415" shape="rect" coords="595,322,637,387"/>
          <area id="416" shape="rect" coords="338,190,366,257"/>
          <area id="417" shape="rect" coords="291,190,322,254"/>
          <area id="418" shape="rect" coords="338,171,366,190"/>
          <area id="419" shape="rect" coords="338,149,367,169"/>
          <area id="7" shape="rect" coords="336,86,380,150"/>
          <area id="421" shape="rect" coords="292,85,323,147"/>
          <area id="422" shape="rect" coords="338,46,366,84"/>
          <area id="423" shape="rect" coords="338,2,366,42"/>
        </map>
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