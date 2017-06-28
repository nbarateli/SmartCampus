<%@ page import="misc.Utils" %>
<%@ page import="model.rooms.Room" %>
<%@ page import="static misc.WebConstants.*" %>
<%@ page import="model.rooms.RoomManager" %>
<%@ page import="model.rooms.RoomSearchQueryGenerator" %>
<%@ page import="static misc.Utils.*" %>
<%@ page import="java.util.List" %><%--
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
  RoomManager manager1 = (RoomManager) request.getServletContext().getAttribute(ROOM_MANAGER);
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
            <option value="wooden_chair">სკამ-მერხები (ხის)</option>
            <option value="plastic_chair">სკამ-მერხები
              (პლასტმასის)
            </option>
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
          RoomSearchQueryGenerator query = new RoomSearchQueryGenerator();
          buildQuery(request, query);
          List<Room> rooms = manager1.find(query);
          System.out.println(rooms.size());
          for (Room room : rooms) {
            out.println("<div id=\"room-id-div\">" + "<a href=showroom.jsp?id=" + room.getId() + ">"
                    + room.getRoomName() + "</a></div>");
            out.println("<button>ვრცლად</button>");

            List<String> images = manager1.getAllImagesOf(room);
            String src = images.size() > 0 ? images.get(0) : NO_IMAGE;
            out.println("<div class=\"to-hide\">");
            out.println("<div><img src=\"" + src + "\" height=\"60%\"></div>");

            out.println("<div id=\"room-info-div\">");
            out.println("<div>სართული: " + room.getFloor() + ".</div>");
            out.println("<div>ოთახის ტიპი: " + roomTypeToString(room.getRoomType(), true) + ".</div>");
            out.println("<div>ადგილები: " + room.getCapacity() + ".</div>");
            out.println("<div>შეიძლება დაჯავშნა: ");
            out.println(room.isAvailableForStudents() ? "<span id=\"yes-span\">კი</span>"
                    : "<span id=\"no-span\">არა</span>");
            out.println("</div>");
            out.println("<div>ადგილის ტიპი: " + seatTypeToString(room.getSeatType(), true) + ".</div>");
            out.println("</div>");
            out.println("</div>");

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
      <div>
        <img src="map1-4.jpg" width="640" height="390" alt="map1" usemap="#map1" id="imgmap1"/>
        <map name="map1" id="map1">
          <area id="101" shape="rect" coords="2,295,87,331"/>
          <area id="102" shape="rect" coords="4,355,87,387"/>
          <area id="103" shape="rect" coords="88,355,127,387"/>
          <area id="104" shape="rect" coords="128,355,212,387"/>
          <area id="105" shape="rect" coords="126,296,212,332"/>
          <area id="106" shape="rect" coords="213,354,276,387"/>
          <area id="107" shape="rect" coords="314,352,400,388"/>
          <area id="108" shape="rect" coords="315,295,400,333"/>
          <area id="109" shape="rect" coords="401,354,440,387"/>
          <area id="110" shape="rect" coords="440,352,524,386"/>
          <area id="111" shape="rect" coords="438,294,524,333"/>
          <area id="112" shape="rect" coords="524,354,584,388"/>
          <area id="113" shape="rect" coords="584,324,638,384"/>
          <area id="114" shape="rect" coords="256,192,293,256"/>
          <area id="115" shape="rect" coords="198,192,236,258"/>
          <area id="116" shape="rect" coords="256,150,292,191"/>
          <area id="117" shape="rect" coords="256,84,293,150"/>
          <area id="118" shape="rect" coords="198,87,234,150"/>
          <area id="119" shape="rect" coords="256,43,293,84"/>
          <area id="120" shape="rect" coords="256,3,294,44"/>
        </map>
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
  <div class="modal-content">
    <span id="closeModal" class="close">&times;</span>
    <div id="roomForm"></div>
  </div>
</div>
</body>
</html>