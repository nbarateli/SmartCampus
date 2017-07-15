/**
 * Created by Nino on 04.07.2017.
 */

function addBookingFromForm() {
    var params = ($('#booking-form').serialize());

    // clearFormInputs(document.getElementById("booking-form"));

    sendData("/bookings/addbooking", params);
    return false;
}

function addLectureFromForm() {
    var params = ($('#booking-form').serialize());

    console.log(params);

    //clearFormInputs(document.getElementById("sched-form"));

    sendData("/lectures/addlecture", params, true);
    return false;
}

function sendData(url, params) {
    var http = new XMLHttpRequest();
    http.open("POST", url, true);

    //Send the proper header information along with the request
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function (alert) {//Call a function when the state changes.
        if (http.readyState === 4 && http.status === 200) {
            window.alert(http.responseText);
        }
    };
    console.log("params are: " + params);
    http.send(params);
}
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

function changeDate(date) {
    console.log("bliad");
    $("#start_dt").val(date);
}

function changeTimeStart(startTime) {
    console.log("bliad");
    $("#start_tm").val(startTime);
}

function changeTimeEnd(endTime) {
    console.log("bliad");
    $("#end_tm").val(endTime);
}

function changeDateSecond(date) {
    console.log("bliad");
    $("#end_dt").val(date);
}

function changeName(name) {
    $("#r_name").val(name);
}

function changeMail(mail) {
    $("#lect_mail").val(mail);
}

function hideNeededInputs() {
    $("#lect_mail").hide();
    $("#subj_name").hide();
}
