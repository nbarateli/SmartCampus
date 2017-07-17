function ExcelToJSON(file, type) {

    this.parseExcel = function (file) {

        var reader = new FileReader();

        reader.onload = function (e) {
            var data = e.target.result;
            var workbook = XLSX.read(data, {type: 'binary'});

            workbook.SheetNames.forEach(function (sheetName) {
                // Here is your object
                var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                var json_object = JSON.stringify(XL_row_object);

                for (i in XL_row_object) {
                    if (type === "lecture") {
                        addLectureFromJson(XL_row_object[i], false);
                        showLectureSuccess();
                    } else if (type === "subject") {
                        addSubjectFromJson(XL_row_object[i], false);
                        showSubjectSuccess();
                    } else {
                        if(addRoomFromJson(XL_row_object[i], false))
                            showRoomSuccess();
                    }

                }
            })

        };

        reader.onerror = function (ex) {
            console.log(ex);
            return null;
        };

        reader.readAsBinaryString(file);
    };
    this.parseExcel(file);
}

function addLecturesFromFile(file) {
    ExcelToJSON(file, "lecture");
}

function addSubjectsFromFile(file) {
    ExcelToJSON(file, "subject");
}

function addRoomsFromFile(file) {
    ExcelToJSON(file, "room");
}

function toWeekDay(weeknumber) {
    switch (weeknumber) {
        case "1":
            return "monday";
        case "2":
            return "tuesday";
        case "3":
            return "wednesday";
        case "4":
            return "thursday";
        case "5":
            return "friday";
        case "6":
            return "saturday";
        case "7":
            return "sunday";
    }
}

function toRoomType(type) {
    switch (type) {
        case "1":
            return "auditorium";
        case "2":
            return "utility";
    }
}

function toSeatType(type) {
    switch (type) {
        case "1":
            return "desks";
        case "2":
            return "wooden_chair";
        case "3":
            return "plastic_chair";
        case "4":
            return "computers";
        case "5":
            return "tables";
    }
}

function addLectureFromJson(jsonObject, doAlert) {
    var params = "";
    params += "lecturer_email=" + jsonObject.lecturer_email;
    params += "&subject_name=" + jsonObject.subject_name;
    params += "&room_name=" + jsonObject.room_name;
    params += "&start_time=" + jsonObject.start_time;
    params += "&end_time=" + jsonObject.end_time;
    params += "&start_date=" + jsonObject.start_date;
    params += "&num_weeks=" + jsonObject.num_weeks;
    params += "&repetition=" + jsonObject.repetition;

    console.log(params);
    sendData("/subjects/addlecture", params, doAlert);
}

var addSubjectFromJson = function (jsonObject, doAlert) {
    var params = "subj_name=" + jsonObject.subj_name;

    sendData("/subjects/addsubject", params, doAlert);
};

function addRoomFromJson(jsonObject, doAlert) {
    var name = jsonObject['ოთახის N'] !== undefined ? jsonObject['ოთახის N'].toString() : null;
    var typePure = jsonObject['ტიპი'] !== undefined ? jsonObject['ტიპი'] : null;
    if(name == null || typePure == null){
        showRoomFailure();
        return false;
    }

    function getFloorViaName(name) {

        function isDigit(c) {
            return c >= '0' && c <= '9';
        }

        switch (name) {

            case "ST1":
                return 1;
            case "1":
            case "ST2":
            case "AMP 0":
            case "AMP 13":
            case "AMP 21":
            case "AMP2 3":
            case "AMP 24":
            case "AMP 25":
                return 1;
            case "B1":
                return 2;
            case "VC1":
            case "VC2":
            case "VC3":
            case "VC4":
            case "79":
            case "139":
                return 3;
            default:
                return isDigit(name[0]) ? name[0] : 2;
        }


    }

    var floor = getFloorViaName(name);

    function contains(str, substring) {
        return str.indexOf(substring) !== -1;
    }

    function getRoomType(typevar) {

        if (contains(typevar, "ლაბ")) return "laboratory";
        switch (typevar.toString()) {
            case "PMC/მაგიდიანი/WiFi":
            case "კულინარია":
            case "მარანი":
            case "მასალათა ლაბორატორია":
            case "მექანიკური ქარხანა":
            case "მიკრობიოლოგიის ლაბ":
            case "მიკროსკოპების ლან":
            case "ორგანული ქიმიის ლაბ":
            case "პერფომანსი 2":
            case "პერფორმანსი 1":
            case "პროზექტურა, პათანატომიის ლექცია/სემინარები":
            case "რობოტიკის ლაბ":
            case "სამართლის დარბაზი/მაგიდებიანი":
            case "სტუდია 1":
            case "სტუდია 2":
            case "სტუდია 3":
            case "ქანდაკება":
                return "utility";
            default:

                return "auditorium";
        }
    }

    var type = getRoomType(typePure);
    var params = "";
    params += "room_name=" + name;
    params += "&room_floor=" + floor;
    params += "&capacity=" + jsonObject['სტუდ. ტევადობა'];
    params += "&room_type=" + type;

    function getSeatType(typevar) {

        switch (typevar.toString()) {
            case "არაბული":
            case "თურქეთი":
            case "იაპონია":
            case "ირანი":
            case "მაგიდიანი":
            case "ჩინური":
                return "desks";
            case "კკლ":
            case "PMC/მაგიდიანი/WiFi":
                return "computers";
            case "ლ/სკამიანი":
            case "სკამიანი":
                return "chairs";

        }
        return "tables";
    }

    params += "&seat_type=" + getSeatType(typePure);

    function canBeBooked(typevar) {
        typevar = typevar.toString();
        if (typevar === "ლ/სკამიანი" || typevar === "სკამიანი" || typevar === "მაგიდიანი") {
            return "true";
        }
        return "false";
    }

    params += "&can_be_booked=" + canBeBooked(typePure);

    sendData("/rooms/addroom", params, doAlert);
    return true;
}

function showSuccessMessage() {
    var elem = document.getElementById("output");
    elem.innerHTML = "წარმატებით დაემატა!";
    elem.style.color = "green";
}

function showDataDuplicateMessage() {
    var elem = document.getElementById("output");
    elem.innerHTML = "მოცემულ პერიოდში ოთახი დაკავებულია!";
    elem.style.color = "red";
}

function validateInputs(responseText) {
    if (responseText == "success") {
        showSuccessMessage();
        return;
    }
    if (responseText == "failure") {
        showDataDuplicateMessage();
        return;
    }
    var obj = JSON.parse(responseText);
    if (obj.lecturer !== undefined) document.getElementById("error_lecturer_email").innerHTML = obj.lecturer;
    if (obj.name !== undefined) document.getElementById("error_room_name").innerHTML = obj.name;
    if (obj.start_time !== undefined) document.getElementById("error_start_time").innerHTML = obj.start_time;
    if (obj.end_time !== undefined) document.getElementById("error_end_time").innerHTML = obj.end_time;
    if (obj.booking_date !== undefined) document.getElementById("error_date").innerHTML = obj.booking_date;
    if (obj.numWeeks !== undefined) document.getElementById("error_numWeeks").innerHTML = obj.numWeeks;
    if (obj.subject !== undefined) document.getElementById("error_subject_name").innerHTML = obj.subject;
}
function sendData(url, params, doAlert) {
    var http = new XMLHttpRequest();
    http.open("POST", url, true);

    //Send the proper header information along with the request
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function (alert) {//Call a function when the state changes.
        if (http.readyState === 4 && http.status === 200 && doAlert) {
            // window.alert(http.responseText);
            validateInputs(http.responseText);
        }
    };
    http.send(params);
}

function createDialog() {
    var conf = confirm("დარწმუნებული ხართ რომ გსურთ ყველა ლექციის შესახებ მონაცემების წაშლა?");
    if (conf) {
        var params = "remove_all=true";
        sendData("/subjects/removelecture", params, true);
    }
}

function clearValidationMessages() {
    var messages = document.getElementsByClassName('errorMessage');
    for (var i = 0; i < messages.length; i++) {
        messages[i].innerHTML = "";
    }
}
function clearOutputMessage() {
    document.getElementById("output").innerHTML = "";
}
function addLectureFromForm() {
    var params = ($('#sched-form').serialize());
    clearValidationMessages();
    clearOutputMessage();
    //clearFormInputs(document.getElementById("sched-form"));
    console.log(params);

    sendData("/subjects/addlecture", params, true);
    return false;
}

function removeRoomFromForm() {
    var params = ($('#remove-room-form').serialize());

    clearFormInputs(document.getElementById("remove-room-form"));

    sendData("/rooms/removeroom", params, true);
    return false;
}

function addRoomFromForm() {
    var params = ($('#add-room-form').serialize());

    // clearFormInputs(document.getElementById("add-room-form"));

    sendData("/rooms/addroom", params, true);
    return false;
}

function addSubjectFromForm() {
    var params = ($('#add-subj-form').serialize());

    clearFormInputs(document.getElementById("add-subj-form"));

    sendData("/subjects/addsubject", params, true);
    return false;
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

function handleFileSelect(evt, type) {
    var files = evt.target.files; // FileList object
    // files is a FileList of File objects. List some properties.

    for (var i = 0, f; f = files[i]; i++) {

        if (type === "lecture") {
            showLectureLoading();
            addLecturesFromFile(f);
        } else if (type === "subject") {
            showSubjectLoading();
            addSubjectsFromFile(f);
        } else {
            showRoomLoading();
            addRoomsFromFile(f);
        }
    }
}

function handleFileSelectLect(evt) {
    handleFileSelect(evt, "lecture");

    var output = [];
    document.getElementById('lect-list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

function handleFileSelectSubj(evt) {
    handleFileSelect(evt, "subject");

    var output = [];
    document.getElementById('subj-list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

function handleFileSelectRoom(evt) {
    handleFileSelect(evt, "room");

    var output = [];
    document.getElementById('room-list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

$(document).ready(function () {
    document.getElementById('lect-file').addEventListener('change', handleFileSelectLect, false);
    document.getElementById('subj-file').addEventListener('change', handleFileSelectSubj, false);
    document.getElementById('rooms-file').addEventListener('change', handleFileSelectRoom, false);

    $("#room_n").focus(function() {
        makeRoomSuggestions();
    });

    var roomNames = [];
    var roomAutocomplete = roomNameAutocomplete(roomNames);

    function makeRoomSuggestions() {
        $.get("/rooms/availablerooms",
            $("#sched-form").serialize(),
            function(data) {
                roomNames = [];
                roomAutocomplete.destroy();
                for (i in data) {
                    var roomName = data[i].toString();

                    roomNames.push(roomName);
                }
                roomAutocomplete = roomNameAutocomplete(roomNames);
            }
        );
    }

    function roomNameAutocomplete(roomNames) {
        console.log(roomNames.toString());
        return new autoComplete({
            selector: '#room_n',
            minChars: 0,
            source: function (term, suggest) {
                term = term.toLowerCase();
                var choices = roomNames;
                console.log(roomNames.toString());
                var suggestions = [];
                for (i = 0; i < choices.length; i++)
                    if (~choices[i].toLowerCase().indexOf(term)) suggestions.push(choices[i]);
                suggest(suggestions);
            }
        });
    }

});

function showModalWithName(name) {
    var modal = $('#schedule-modal');
    var room_name = $('#room_n');
    modal.modal('show');
    room_name.value = name;
}


function showSubjectSuccess(){
    document.getElementById("subject-tick").style.display = "inline";
    document.getElementById("subject-w8gif").style.display = "none";
    document.getElementById("subject-fail").style.display = "none'"
}
function showLectureSuccess(){
    document.getElementById("lecture-tick").style.display = "inline";
    document.getElementById("lecture-w8gif").style.display = "none";
    document.getElementById("lecture-fail").style.display = "none'"
}
function showRoomSuccess(){
    document.getElementById("room-tick").style.display = "inline";
    document.getElementById("room-w8gif").style.display = "none";
    document.getElementById("room-fail").style.display = "none'"
}


function showSubjectFailure(){
    document.getElementById("subject-tick").style.display = "none";
    document.getElementById("subject-w8gif").style.display = "none";
    document.getElementById("subject-fail").style.display = "inline";
}
function showLectureFailure(){
    document.getElementById("lecture-tick").style.display = "none";
    document.getElementById("lecture-w8gif").style.display = "none";
    document.getElementById("lecture-fail").style.display = "inline";
}
function showRoomFailure(){
    document.getElementById("room-tick").style.display = "none";
    document.getElementById("room-w8gif").style.display = "none";
    document.getElementById("room-fail").style.display = "inline";
}