<%@ page import="model.rooms.Room" %>
<%@ page import="model.rooms.RoomManager" %>
<%@ page import="static misc.WebConstants.*" %>
<%@ page import="model.rooms.RoomSearchQueryGenerator" %>
<%@ page import="java.util.List" %>
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
<head>
  <title>ოთახები</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="SearchPageStyle.css">
  <script
          src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script
          src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="RoomsSearch.js"></script>
</head>
<body>
<script type="text/javascript">
    window.addEventListener('load', load, false);
    function load() {
        var map = document.getElementById("map1");
        var areas = map.childNodes;
        for (i = 0; i < areas.length; i++) {
            areas[i].addEventListener("mouseover", myHover);
            areas[i].addEventListener("click",myClick);
        }
        function myHover(){
            //testing hover
            var id = this.id;
            document.getElementById("par").innerHTML = id;
            //to do hover functionality 
        }
        function myClick(){
            //testing click
            var id = this.id;
            alert(id);
            //to do click functionality
        }
    };
</script>
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
            out.println("<div>ოთახის ტიპი: " + roomTypeToString(room.getRoomType()) + ".</div>");
            out.println("<div>ადგილები: " + room.getCapacity() + ".</div>");
            out.println("<div>შეიძლება დაჯავშნა: ");
            out.println(room.isAvailableForStudents() ? "<span id=\"yes-span\">კი</span>"
                    : "<span id=\"no-span\">არა</span>");
            out.println("</div>");
            out.println("<div>ადგილის ტიპი: " + seatTypeToString(room.getSeatType()) + ".</div>");
            out.println("</div>");
            out.println("</div>");

          }
        %>
      </div>
    <h1 id="par">This is a Heading</h1>

    <img src="map1-4.jpg" width="640" height="390" alt="map1" usemap="#map1"/>
    <map name="map1" id="map1">
      <area id="101" shape="rect" coords="2,295,87,331" />
      <area id="102" shape="rect" coords="4,355,87,387" />
      <area id="103" shape="rect" coords="88,355,127,387" />
      <area id="104" shape="rect" coords="128,355,212,387" />
      <area id="105" shape="rect" coords="126,296,212,332" />
      <area id="106" shape="rect" coords="213,354,276,387" />
      <area id="107" shape="rect" coords="314,352,400,388" />
      <area id="108" shape="rect" coords="315,295,400,333" />
      <area id="109" shape="rect" coords="401,354,440,387" />
      <area id="110" shape="rect" coords="440,352,524,386" />
      <area id="111" shape="rect" coords="438,294,524,333" />
      <area id="112" shape="rect" coords="524,354,584,388" />
      <area id="113" shape="rect" coords="584,324,638,384" />
      <area id="114" shape="rect" coords="256,192,293,256" />
      <area id="115" shape="rect" coords="198,192,236,258" />
      <area id="116" shape="rect" coords="256,150,292,191" />
      <area id="117" shape="rect" coords="256,84,293,150" />
      <area id="118" shape="rect" coords="198,87,234,150" />
      <area id="119" shape="rect" coords="256,43,293,84" />
      <area id="120" shape="rect" coords="256,3,294,44" />
    </map>
    </td>
  </tr>
</table>
</body>
</html>