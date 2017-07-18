/**
 * Created by Shota on 6/26/2017.
 */
let maps = [null];
load();
function showFloor(floor) {
    if (floor < 1 || floor > 4) return;
    for (var i = 1; i <= 4; i++) {
        if (i === floor) {
            maps[i].show();
        } else {
            maps[i].hide();
        }
    }
}

function load() {
    maps.push($('#map1'));
    maps.push($('#map2'));
    maps.push($('#map3'));
    maps.push($('#map4'));
    var map1 = document.getElementById("map1");
    var map2 = document.getElementById("map2");
    var map3 = document.getElementById("map3");
    var map4 = document.getElementById("map4");
    workMaps(map1);
    workMaps(map2);
    workMaps(map3);
    workMaps(map4);
    showFloor(1);
    var span = document.getElementById("closeModal");
    var modal = document.getElementById('myModal');
    span.onclick = function () {

        modal.style.display = "none";
        document.getElementById('roomForm').innerHTML = "";
    };
    window.onclick = function (event) {
        if (event.target == modal) {
            document.getElementById('roomForm').innerHTML = "";
            modal.style.display = "none";
        }
    }
}

function workMaps(map) {
    var areas = map.childNodes;
    for (i = 0; i < areas.length; i++) {
        // var className = areas[i].getAttribute("class");
        var child = areas[i];
        var name = $(child).attr('class') == undefined ? null : $(child).attr('class').split(' ')[0];
        if (name !== 'room' && name !== 'room_name') continue;
        areas[i].addEventListener("click", roomClicked);
        // areas[i].addEventListener("mouseover", myHover);
        // areas[i].addEventListener("mouseout", myOut);
    }
}


function roomClicked() {

    var id = this.id;
    var modal = document.getElementById('myModal');

    $.get('/rooms/room', {name: id.substring(1)},
        function (returnedData) {
            displayRoom(returnedData, 'roomForm');
        }).fail(function () {
        console.log("error");
    });

    modal.style.display = "block";
}

function myOut() {
    var roomId = document.getElementById("x" + this.id.substr(1)).id;
    var id = "h" + roomId;
    var hoverElement = document.getElementById(id);
    hoverElement.style.display = "none";
}

function myHover() {
    //return;
    var roomId = document.getElementById("x" + this.id.substr(1)).id;
    var already = document.getElementById("h" + roomId);
    if (already !== null) {
        already.style.display = "unset";
        return;
    }

    var div = document.createElement("div");
    var rect = document.getElementById(roomId).getBoundingClientRect();
    var leftPos = +rect.left + rect.width;
    var topPos = +rect.top;
    console.log(leftPos + " " + topPos);

    var text = document.createTextNode("this room is: " + roomId.substr(1));
    div.appendChild(text);

    div.className = "popup-div";
    div.id = "h" + roomId;
    div.style.left = leftPos + "px";
    div.style.top = topPos + "px";

    document.body.appendChild(div);
}

function floorChange() {
    var floorString = document.getElementById("floors").value;
    let floor;
    switch (floorString) {
        case "first":
            floor = 1;
            break;
        case "second":
            floor = 2;
            break;
        case "third":
            floor = 3;
            break;
        case "fourth":
            floor = 4;
            break;
    }
    showFloor(floor);
}


function unHighLightRooms() {
    var map1 = document.getElementById("map1");
    var map2 = document.getElementById("map2");
    var map3 = document.getElementById("map3");
    var map4 = document.getElementById("map4");

    function unHighlightMap(map) {
        var children = map.childNodes;

        function getColorFromStyle(style) {
            console.log(style);
            var elem = document.querySelector(style);
            console.log(elem);
            return getComputedStyle(elem).fill;
        }

        for (i  in children) {
            var obj = children[i];
            var cname = obj.className; //$(obj).attr("class");
            if (cname === undefined || cname.baseVal !== 'room_selected') continue;
            obj.className.baseVal = 'room';
        }
    }

    unHighlightMap(map1);
    unHighlightMap(map2);
    unHighlightMap(map3);
    unHighlightMap(map4);
}

function findRooms() {
    var queryString = $('#search-form').serialize();
    $.get('/rooms/findrooms', queryString,
        function (returnedData) {
            unHighLightRooms();
            for (i in returnedData) {
                var room = returnedData[i];
                highlightRoom(room);
            }
        }).fail(function () {
        console.log("error");
    });
    // return false;
}

function highlightRoom(room) {
    var roomName = room['name'].toString();
    var area = document.getElementById("x" + roomName);
    if (area !== null) {
        if (room['booked'] !== undefined) {
            area.className.baseVal = room['booked'] ? 'room_booked' : 'room_selected';
        } else {
            area.className.baseVal = 'room_selected';
        }
    }
}

function displayRoom(data, id) {
    var tmp = document.getElementById("mustmpl").innerHTML;

    function parseRoomType(roomtype) {
        switch (roomtype.toString().toLowerCase()) {
            case 'laboratory':
                return "ლაბორატორია";
            case 'auditorium':
                return "აუდიტორია"
        }
        return "სხვა";
    }

    var parseSeatType = function (seattype) {
        switch (seattype.toString().toLowerCase()) {
            case 'desks':
                return 'მერხები';
            case 'chairs':
                return 'სკამები';
            case 'computers':
                return 'კომპიუტერები';
            case 'tables':
                return 'მაგიდები';
        }
    };
    data['roomtype'] = parseRoomType(data['roomtype']);

    data['seattype'] = parseSeatType(data['seattype']);
    var available = data['available'];
    if (available) {
        data['available'] = 'კი';
        data['spanid'] = 'yes-span';
    } else {
        data['available'] = 'არა';
        data['spanid'] = 'no-span';
    }
    html = Mustache.to_html(tmp, data);
    var box = document.getElementById(id);
    box.innerHTML = html;
}
