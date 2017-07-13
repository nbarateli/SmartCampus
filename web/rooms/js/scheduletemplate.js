/**
 * Created by Niko on 13.07.2017.
 */
const WEEKDAYS = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday'];

function displayDay(currentDay, elemId) {
    document.getElementById(elemId).innerHTML = "";
    let room_id = getParameterByName('id', window.location['href']);
    $.get('/bookings/bookings_on', {day: currentDay, room_id: room_id},
        function (returnedData) {
            console.log(currentDay);
            console.log(elemId);
            if (undefined === returnedData['bookings']) return;

            for (i in returnedData['bookings']) {
                let booking = returnedData['bookings'][i];
                let tmp = document.getElementById('bookingtemplate').innerHTML + " ";
                console.log(booking);
                document.getElementById(elemId).innerHTML += Mustache.to_html(tmp, booking);
            }
        });
}

function displayWeek(endDate, formatSettings) {

    for (let day = 6; day >= 0; day--) {
        let dateFormat = formatSettings.dateFormat || $.datepicker._defaults.dateFormat;
        let currentDay = $.datepicker.formatDate(dateFormat, new Date(endDate - 86400000 * day), formatSettings);
        displayDay(currentDay, WEEKDAYS[6 - day]);
    }
}