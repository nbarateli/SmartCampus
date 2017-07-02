/**
 * Created by Shota on 6/26/2017.
 */
window.addEventListener('load', load, false);

function load() {
    var map1 = document.getElementById("map1");
    var map2 = document.getElementById("map2");
    var map3 = document.getElementById("map3");
    var map4 = document.getElementById("map4");
    workMaps(map1);
    workMaps(map2);
    workMaps(map3);
    workMaps(map4);
    map2.style.display = "none";
    map3.style.display = "none";
    map4.style.display = "none";
    var span = document.getElementById("closeModal");
    var modal = document.getElementById('myModal');
    span.onclick = function () {

        modal.style.display = "none";
        document.getElementById('roomForm').innerHTML = "";
        modal.getElementById()
    };
    window.onclick = function (event) {
        if (event.target == modal) {
            document.getElementById('roomForm').innerHTML = "";
            modal.style.display = "none";
        }
    }
};

function workMaps(map) {
    var areas = map.childNodes;
    for (i = 0; i < areas.length; i++) {
        // var className = areas[i].getAttribute("class");
        var child = areas[i];
        var name = $(child).attr("class");
        if (name !== 'room') continue;
        areas[i].addEventListener("mouseover", myHover);
        areas[i].addEventListener("click", myClick);
    }
}


function myClick() {
    //testing
    var id = this.id;
    var modal = document.getElementById('myModal');
    console.log(id);
    $.get('/rooms/room', {name: id.substring(1)},
        function (returnedData) {
            displayRoom(returnedData, 'roomForm');
        }).fail(function () {
        console.log("error");
    });
    // $.ajax({
    //     type: 'POST',
    //     url: '/rooms/room',
    //     data: id,
    //     parameterNames: 'id',
    //     success: function (data) {
    //         var parsedData = JSON.parse(data);
    //         console.log(parsedData);
    //     }
    // })


    modal.style.display = "block";
}

function myHover() {
    return;
    var id = this.id;
    var rect = document.getElementById(id).getBoundingClientRect();
    var coords = this.getAttribute("coords").split(",");
    var leftPos = +rect.left + 130 + +coords[2];
    var topPos = +rect.top + +coords[3];

    console.log("sum is: " + leftPos + " " + topPos);
    console.log(leftPos + " " + topPos);
    var div = document.getElementById("popup-div");
    div.removeChild(div.childNodes[0]);
    var text = document.createTextNode("this room is: " + id);
    div.appendChild(text);

    div.style.backgroundColor = "yellow";
    div.style.border = "1px";
    div.style.position = "absolute";
    div.style.left = leftPos + "px";
    div.style.top = topPos + "px"; //div.style.right = "2px"; div.style.bottom = "2px";
    div.style.height = "100px";
    div.style.width = "100px";
    document.body.appendChild(div);

    document.getElementById("par").innerHTML = id;
}

function floorChange() {
    var x = document.getElementById("floors").value;
    if (x === "first") showFirst();
    if (x === "second") showSecond();
    if (x === "third") showThird();
    if (x === "fourth") showFourth();
}

function showFirst() {
    document.getElementById("map2").style.display = "none";
    document.getElementById("map3").style.display = "none";
    document.getElementById("map4").style.display = "none";
    document.getElementById("map1").style.display = "unset";
}

function showSecond() {
    document.getElementById("map1").style.display = "none";
    document.getElementById("map3").style.display = "none";
    document.getElementById("map4").style.display = "none";
    document.getElementById("map2").style.display = "unset";
}

function showThird() {
    document.getElementById("map1").style.display = "none";
    document.getElementById("map2").style.display = "none";
    document.getElementById("map4").style.display = "none";
    document.getElementById("map3").style.display = "unset";
}

function showFourth() {
    document.getElementById("map1").style.display = "none";
    document.getElementById("map2").style.display = "none";
    document.getElementById("map3").style.display = "none";
    document.getElementById("map4").style.display = "unset";
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