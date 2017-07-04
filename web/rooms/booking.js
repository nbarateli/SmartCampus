/**
 * Created by Nino on 04.07.2017.
 */

function addBookingFromForm() {
    var params = ($('#booking-form').serialize());

    // clearFormInputs(document.getElementById("booking-form"));

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

function clearFormInputs(formToClear) {

    var elems = formToClear.elements;
    formToClear.reset();

    for (i = 0; i < elems.length; i++) {

        fieldType = elems[i].type.toLowerCase();

        if (fieldType === "checkbox")
            elems[i].checked = false;
        else if (fieldType === "select")
            elems[i].selectedIndex = 1;
        else if (fieldType != "button" && fieldType != "submit")
            elems[i].value = "";
    }
}

function changeName(name) {
    $("#r_name").val(name);
}
