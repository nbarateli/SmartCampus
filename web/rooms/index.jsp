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
        <svg xmlns:xlink="http://www.w3.org/1999/xhtml" version="1.1" id="map1" xmlns="http://www.w3.org/2000/svg"
             x="0px"
             y="0px" viewBox="620 291 3040 920" style="enable-background:new 620 291 3040 920;" xml:space="preserve">
<style type="text/css">
  .st0 {
    fill: #FFFFFF;
    stroke: #000000;
    stroke-miterlimit: 10;
  }

  .st1 {
    fill: #BEC0C2;
    stroke: #000000;
    stroke-miterlimit: 10;
  }

  .st2 {
    fill: none;
    stroke: #000000;
    stroke-miterlimit: 10;
  }

  .st3 {
    font-family: 'Consolas';
  }

  .st4 {
    font-size: 18px;
  }

  .st5 {
    font-family: 'Sylfaen';
  }

  .st6 {
    font-size: 18.5893px;
  }
</style>
          <rect id="xST1" x="1341.8" y="880" class="st0" width="60" height="235"/>
          <rect id="XMLID_3_" x="1342.8" y="844.8" class="st0" width="70" height="35"/>
          <polygon id="XMLID_4_" class="st1" points="1412.8,862.3 1478,862.3 1478,879.8 1778.3,880 1778.3,1115 1403,1115 1403,880
	1412.8,879.8 "/>
          <line id="XMLID_6_" class="st2" x1="1412.8" y1="844.8" x2="1628.3" y2="844.8"/>
          <polygon class="st0" points="1571.8,844.8 1571.8,594.8 1371.8,594.8 1371.8,469.8 1786.8,469.8 1786.8,789.8 1741.8,789.8
	1741.8,844.8 "/>
          <path id="XMLID_8_" class="st0"
                d="M2193,791h109v54.1h70V1011h-438v-42l1,146h-156.7V880c51.9,0.2,103.8,0.3,155.7,0.5v41"/>
          <path id="XMLID_10_" class="st0" d="M1956.6,845.1c202.7,96.6,276.5,101.2,297,75c15.3-19.5,9-66.5-13.5-76.5
	c-17.1-7.6-44.1,5.9-66,33"/>
          <polyline id="XMLID_9_" class="st0" points="2302,791 2262,791 2262,776 "/>
          <rect id="XMLID_12_" x="2165" y="311" class="st0" width="170" height="465"/>
          <rect id="x101ა" x="2165.5" y="660" class="st0" width="60" height="115"/>
          <rect id="x102ა" x="2274.5" y="660" class="st0" width="60" height="115"/>
          <rect id="x103ა" x="2274.5" y="595.3" class="st0" width="60" height="65"/>
          <rect id="x105ა" x="2274.3" y="479.3" class="st0" width="60" height="115"/>
          <rect id="x104ა" x="2164.5" y="480" class="st0" width="60" height="115"/>
          <rect id="x106ა" x="2274.5" y="408.5" class="st0" width="60" height="70"/>
          <rect id="x107ა" x="2274.5" y="311" class="st0" width="60" height="97.5"/>
          <rect id="x108ა" x="2165" y="359.8" class="st0" width="57.5" height="48.8"/>
          <polyline id="xC_pathxE_" class="st2" points="2762.6,845.1 2372,845.1 2372,1011 3448.1,1011 "/>
          <rect id="x101" x="2371.6" y="951.1" class="st0" width="150" height="60"/>
          <rect id="x102" x="2372" y="845.1" class="st0" width="150" height="60"/>
          <rect id="x105" x="2584.7" y="845.1" class="st0" width="177" height="60"/>
          <polyline class="st0" points="2761.7,845.1 2761.7,791 2802,791 2802,775.7 2737,775.7 2737,311 2897.7,311 2897.7,775.7
	2832.7,775.7 2832.7,791 2872.7,791 2873,845.1 2873.3,885 "/>
          <rect id="x114" x="2833" y="661" class="st0" width="65" height="115"/>
          <rect id="x115" x="2737" y="660" class="st0" width="65" height="115"/>
          <polygon id="x116" class="st0" points="2833,591 2898,590 2898,660 2833,661 "/>
          <rect id="x117" x="2833" y="475.5" class="st0" width="65" height="115"/>
          <rect id="x119" x="2833" y="408.6" class="st0" width="65" height="66.9"/>
          <rect id="x120" x="2833" y="311" class="st0" width="65" height="97.5"/>
          <line class="st2" x1="2802" y1="311" x2="2802" y2="331.7"/>
          <polyline class="st0" points="2737,311 2737,386.3 2802,386.3 2802,357 "/>
          <rect id="x118" x="2737" y="475.5" class="st0" width="65" height="115"/>
          <line class="st2" x1="3332.6" y1="845.1" x2="2873" y2="845.1"/>
          <rect id="x108" x="2938.5" y="845.1" class="st0" width="150" height="50"/>
          <rect id="x111" x="3153" y="845.1" class="st0" width="150" height="50"/>
          <rect id="x103" x="2521.6" y="951.1" class="st0" width="63.1" height="60"/>
          <rect id="x104" x="2581.8" y="951.1" class="st0" width="179.8" height="60"/>
          <rect id="x106" x="2761.7" y="951.1" class="st0" width="111.3" height="60"/>
          <rect id="x107" x="2938.5" y="951.1" class="st0" width="150" height="60"/>
          <rect id="x109" x="3088.5" y="951.1" class="st0" width="65.5" height="60"/>
          <rect id="x110" x="3153.3" y="951.1" class="st0" width="150" height="60"/>
          <rect id="x112" x="3302.7" y="951.1" class="st0" width="116.1" height="60"/>
          <a xlink:href="google.com">
            <rect id="x113" x="3418.8" y="901.8" class="st0" width="90" height="110"/>
          </a>
          <polyline class="st0" points="3302.7,845.2 3302.7,775.1 3342.7,775.1 3378.2,775.1 3418.8,775.1 3418.8,845.2 3508.8,845.2
	3508.8,901.2 "/>
          <text transform="matrix(1 0 0 1 1356.6 990.65)" class="st3 st4">ST1</text>
          <text transform="matrix(1 0 0 1 1620 663.95)" class="st5 st4">ბიბლიოთეკა</text>
          <polyline class="st0" points="1741.8,844.8 2193,844.8 2193,791 2238,791 2238,776 "/>
          <text transform="matrix(1 0 0 1 2176.3335 721.1667)" class="st5 st4">101ა</text>
          <text transform="matrix(1 0 0 1 2284.3003 724.3335)" class="st5 st4">102ა</text>
          <text transform="matrix(0.9683 0 0 1 2285.8337 632.3865)" class="st5 st6">103ა</text>
          <text transform="matrix(1 0 0 1 2287.3337 551.333)" class="st5 st4">105ა</text>
          <text transform="matrix(1 0 0 1 2176.8337 547.0002)" class="st5 st4">104ა</text>
          <text transform="matrix(1 0 0 1 2285.333 450.6665)" class="st5 st4">106ა</text>
          <text transform="matrix(1 0 0 1 2284.667 370)" class="st5 st4">107ა</text>
          <text transform="matrix(1 0 0 1 2428.9998 977.334)" class="st5 st4">101</text>
          <text transform="matrix(1 0 0 1 2430.6003 879.334)" class="st5 st4">102</text>
          <text transform="matrix(1 0 0 1 2538.6504 981.767)" class="st5 st4">103</text>
          <text transform="matrix(1 0 0 1 2656.8828 981.7676)" class="st5 st4">104</text>
          <text transform="matrix(1 0 0 1 2653.7 879.2999)" class="st5 st4">105</text>
          <text transform="matrix(1 0 0 1 2803.333 983.7673)" class="st5 st4">106</text>
          <text transform="matrix(1 0 0 1 2994.1667 985.1003)" class="st5 st4">107</text>
          <text transform="matrix(1 0 0 1 2992 876)" class="st5 st4">108</text>
          <text transform="matrix(1 0 0 1 3106.6663 983.767)" class="st5 st4">109</text>
          <text transform="matrix(1 0 0 1 3210.3337 981.1006)" class="st5 st4">110</text>
          <text transform="matrix(1 0 0 1 3211.6663 872.866)" class="st5 st4">111</text>
          <text transform="matrix(1 0 0 1 3344.717 985.101)" class="st5 st4">112</text>
          <text transform="matrix(1 0 0 1 3448.4331 944.6663)" class="st5 st4">113</text>
          <text transform="matrix(1 0 0 1 2752.1667 727.0002)" class="st5 st4">115</text>
          <text transform="matrix(1 0 0 1 2851 727.3335)" class="st5 st4">114</text>
          <text transform="matrix(1 0 0 1 2851.5002 635.7197)" class="st5 st4">116</text>
          <text transform="matrix(1 0 0 1 2849.9998 542.9998)" class="st5 st4">117</text>
          <text transform="matrix(1 0 0 1 2752.1663 543)" class="st5 st4">118</text>
          <text transform="matrix(1 0 0 1 2850.833 445.333)" class="st5 st4">119</text>
          <text transform="matrix(1 0 0 1 2851.5 368.6665)" class="st5 st4">120</text>
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