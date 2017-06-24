var ExcelToJSON = function (file) {

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
                    addLectureFromJson(XL_row_object[i], false);
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
};

var addLecturesFromFile = function (file) {
    ExcelToJSON(file);


};

var toWeekDay = function (weeknumber) {
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
};

var addLectureFromForm = function () {
    var params = ($('#sched-form').serialize());

    addLecture(params, true);
    return false;
};

var addLectureFromJson = function (jsonObject, doAlert) {
    var params = "";
    params += "lecturer_email=" + jsonObject.lecturer_email;
    params += "&subject_name=" + jsonObject.subject_name;
    params += "&room_name=" + jsonObject.room_name;
    params += "&week_day=" + toWeekDay(jsonObject.week_day);
    params += "&start_time=" + jsonObject.start_time;
    params += "&end_time=" + jsonObject.end_time;

    sendData("/lectures/addlecture", params, doAlert);
};

var sendData = function (url, params, doAlert) {
    var http = new XMLHttpRequest();
    http.open("POST", url, true);

    //Send the proper header information along with the request
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function (alert) {//Call a function when the state changes.
        if (http.readyState === 4 && http.status === 200 && doAlert) {
            window.alert(http.responseText);
        }
    };
    http.send(params);
};

function createDialog() {
	var conf = confirm("დარწმუნებული ხართ რომ გსურთ ყველა ლექციის შესახებ მონაცემების წაშლა?");
	if(conf) {
	    var params = "remove_all=true";
	    sendData("/lectures/removelecture", params, true);
	}
}

var removeRoomFromForm = function () {
    var params = ($('#remove-room-form').serialize());

    sendData("/rooms/removeroom", params, true);
    return false;
};

var addRoomFromForm = function () {
    var params = ($('#add-room-form').serialize());

    sendData("/rooms/addroom", params, true);
    return false;
};

var addSubjectFromForm = function () {
    var params = ($('#add-subj-form').serialize());

    sendData("/lectures/addsubject", params, true);
    return false;
};

function handleFileSelect(evt) {
    var files = evt.target.files; // FileList object
    // files is a FileList of File objects. List some properties.
    var output = [];
    for (var i = 0, f; f = files[i]; i++) {

        addLecturesFromFile(f);
    }
    document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

$(document).ready(function () {
    document.getElementById('file').addEventListener('change', handleFileSelect, false);
});