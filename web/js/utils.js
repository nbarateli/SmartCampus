function getRoomNames() {
    var availableTags = [];
    $.get('/rooms/allroomnames', "",
        function (returnedData) {
            for (i in returnedData) {
                var roomName = returnedData[i].toString();

                availableTags.push(roomName);
            }
        }).fail(function () {
        console.log("error");
    });
    return availableTags;
}

var allRoomNames = getRoomNames();

function roomNameAutocomplete(selectorId) {
    return new autoComplete({
        selector: '#' + selectorId,
        minChars: 0,
        source: function (term, suggest) {
            term = term.toLowerCase();
            var choices = allRoomNames;
            var suggestions = [];
            for (i = 0; i < choices.length; i++)
                if (~choices[i].toLowerCase().indexOf(term)) suggestions.push(choices[i]);
            suggest(suggestions);
        }
    });
}

function getSubjectNames() {
    var availableTags = [];
    $.get('/getallsubjects', "",
        function (returnedData) {
            for (i in returnedData) {
                var subjectName = returnedData[i]['subject_name'].toString();

                availableTags.push(subjectName);
            }

        }).fail(function () {

        console.log("error");
    });
    return availableTags;
}

var subjectNames = getSubjectNames();

function subjectNameAutocomplete(selectorId) {

    return new autoComplete({
        selector: '#' + selectorId,
        minChars: 0,
        source: function (term, suggest) {
            term = term.toLowerCase();

            var choices = subjectNames;
            var suggestions = [];
            for (i = 0; i < choices.length; i++)
                if (~choices[i].toLowerCase().indexOf(term)) suggestions.push(choices[i]);
            suggest(suggestions);
        }
    });
}
