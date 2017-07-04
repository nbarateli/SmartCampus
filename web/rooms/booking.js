/**
 * Created by Nino on 04.07.2017.
 */

function addBookingFromForm() {
    var params = ($('#booking-form').serialize());

    clearFormInputs(document.getElementById("booking-form"));

    sendData("/bookings/addbooking", params);
    return false;
}

function sendData (url, params) {
    var http = new XMLHttpRequest();
    http.open("POST", url, true);

    //Send the proper header information along with the request
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function (alert) {//Call a function when the state changes.
        if (http.readyState === 4 && http.status === 200) {
            window.alert(http.responseText);
        }
    };
    http.send(params);
};

function changeName(name) {
    $("#r_name").val(name);
}
