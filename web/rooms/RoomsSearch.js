/**
 *
 */

$(document).ready(function () {
    $(".to-hide").hide();
    $("button").click(function () {
        $(this).next().slideToggle(400);
    });
});
var startTimePicker = new TimePicker('start_time', {
    lang: 'en',
    theme: 'dark'
});
startTimePicker.on('change', onChange(evt));

var endTimePicker = new TimePicker('end_time', {
    lang: 'en',
    theme: 'dark'
});
endTimePicker.on('change', onChange(evt));

function onChange(evt) {
    var value = (evt.hour || '00') + ':' + (evt.minute || '00');
    evt.element.value = value;
}

