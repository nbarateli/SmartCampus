function getRoomNames() {
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
    return availableTags;
}

var roomNames = getRoomNames();

function roomNameAutocomplete(selectorId) {
    return new autoComplete({
        selector: '#' + selectorId,
        minChars: 1,
        source: function (term, suggest) {
            term = term.toLowerCase();
            console.log(term);
            var choices = roomNames;
            var suggestions = [];
            for (i = 0; i < choices.length; i++)
                if (~choices[i].toLowerCase().indexOf(term)) suggestions.push(choices[i]);
            suggest(suggestions);
        }
    });
}
