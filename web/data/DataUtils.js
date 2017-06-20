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


}
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
}
var addLectureFromForm = function () {
    var params = ($('#sched-form').serialize());
    console.log(params);
    addLecture(params, true);

}

var addLectureFromJson = function (jsonObject, doAlert) {
    var params = "";
    params += "lecturer_email=" + jsonObject.lecturer_email;
    params += "&subject_name=" + jsonObject.subject_name;
    params += "&room_name=" + jsonObject.room_name;
    params += "&week_day=" + toWeekDay(jsonObject.week_day);
    params += "&start_time=" + jsonObject.start_time;
    params += "&end_time=" + jsonObject.end_time;
    addLecture(params, doAlert);
}
var addLecture = function (params, doAlert) {
    var url = "/lectures/addlecture";
    console.log(params);
    var http = new XMLHttpRequest();
    http.open("POST", url, true);

    //Send the proper header information along with the request
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function (alert) {//Call a function when the state changes.
        if (http.readyState == 4 && http.status == 200 && doAlert) {
            window.alert(http.responseText);
        }
    }
    http.send(params);
}
