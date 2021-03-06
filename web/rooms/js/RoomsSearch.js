$(document).ready(function () {
    $(".to-hide").hide();
    $("button").click(function () {
        $(this).next().slideToggle(400);
    });

    $("#floor_index").blur(function () {
        quantity_fixer(this, 1, 4);
    });

    $("#seat_quantity_from").blur(function () {
        quantity_fixer(this, 0, 200);
    });

    $("#seat_quantity_to").blur(function () {
        quantity_fixer(this, 0, 200);
    });


});

function quantity_fixer(obj, low, high) {
    var quan = $(obj).val();

    if (quan < 0) $(obj).val(low);
    if (quan > 200) $(obj).val(high);
}

function bookThisRoom(name) {
    window.open('booking.jsp?room_name=' + name, '_blank')
}

function addLectureAtThisRoom(name) {
    window.open('/data/addingData.jsp?room_name=' + name, '_blank');
}

function showOnMap(id) {
    $.get('/rooms/room', {id: id}, function (data) {

        if (undefined === data['error']) {
            unHighLightRooms();
            var floor = data['floor'];
            showFloor(floor);

            highlightRoom(data);
        }
    });
}

function requestOnMap(id) {
    window.open('index.jsp?showonmap=' + id, '_blank');

}
