/**
 *
 */

$(document).ready(function () {
    $(".to-hide").hide();
    $("button").click(function () {
        $(this).next().slideToggle(400);
    });
});
$(function () {
    var availableTags = [];
    $.get('/rooms/allroomnames', "",
        function (returnedData) {
            for (i in returnedData) {
                var roomName = returnedData[i].toString();

                availableTags.push(roomName);
            }
            console.log(availableTags);
        }).fail(function () {
        console.log("error");
    });
    $("#room_name_in").autocomplete({
        source: availableTags
    });
});