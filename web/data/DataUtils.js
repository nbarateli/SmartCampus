var ExcelToJSON = function (file, type) {

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
                	if(type === "lecture") {
                		addLectureFromJson(XL_row_object[i], false);
                	} else if (type === "subject") {
                		
                		addSubjectFromJson(XL_row_object[i], false);
                	} else {
                		addRoomFromJson(XL_row_object[i], false);
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
};

var addLecturesFromFile = function (file) {
    ExcelToJSON(file, "lecture");
};

var addSubjectsFromFile = function (file) {
    ExcelToJSON(file, "subject");
};

var addRoomsFromFile = function (file) {
    ExcelToJSON(file, "room");
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

var toRoomType = function (type) {
    switch (type) {
        case "1":
            return "auditorium";
        case "2":
            return "utility";
    }
};

var toSeatType = function (type) {
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

var addSubjectFromJson = function (jsonObject, doAlert) {
    var params = "subj_name=" + jsonObject.subj_name;

    sendData("/lectures/addsubject", params, doAlert);
};

var addRoomFromJson = function (jsonObject, doAlert) {
    var params = "";
    params += "room_name=" + jsonObject.room_name;
    params += "&room_floor=" + jsonObject.room_floor;
    params += "&capacity=" + jsonObject.capacity;
    params += "&room_type=" + toRoomType(jsonObject.room_type);
    params += "&seat_type=" + toSeatType(jsonObject.seat_type);
    
    if(jsonObject.cen_be_booked === 1)
    	params += "&can_be_booked=true";

    sendData("/rooms/addroom", params, doAlert);
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

var addLectureFromForm = function () {
    var params = ($('#sched-form').serialize());
    
    clearFormInputs(document.getElementById("sched-form"));
    
    sendData("/lectures/addlecture", params, true);
    return false;
};

var removeRoomFromForm = function () {
    var params = ($('#remove-room-form').serialize());
    
    clearFormInputs(document.getElementById("remove-room-form"));
    
    sendData("/rooms/removeroom", params, true);
    return false;
};

var addRoomFromForm = function () {
    var params = ($('#add-room-form').serialize());
    
    clearFormInputs(document.getElementById("add-room-form"));
    
    sendData("/rooms/addroom", params, true);
    return false;
};

var addSubjectFromForm = function () {
    var params = ($('#add-subj-form').serialize());
    
    clearFormInputs(document.getElementById("add-subj-form"));
    
    sendData("/lectures/addsubject", params, true);
    return false;
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

function handleFileSelect(evt, type) {
	var files = evt.target.files; // FileList object
	// files is a FileList of File objects. List some properties.
	for (var i = 0, f; f = files[i]; i++) {
		if (type === "lecture") {
			addLecturesFromFile(f);
		} else if (type === "subject") {
			addSubjectsFromFile(f);
		} else {
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
});