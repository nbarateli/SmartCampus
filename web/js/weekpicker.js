/**
 * Created by Niko on 13.07.2017.
 */

$(function () {
    var startDate;
    var endDate;


    function selectCurrentWeek() {
        window.setTimeout(function () {
            $('.week-picker').find('.ui-datepicker-current-day a').addClass('ui-state-active')
        }, 1);
        console.log('weeeeeak')
    }

    $('.week-picker').datepicker({
        showOtherMonths: true,
        selectOtherMonths: true,
        onSelect: function (dateText, inst) {
            var date = $(this).datepicker('getDate');
            startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay());
            endDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay() + 6);

            var dateFormat = inst.settings.dateFormat || $.datepicker._defaults.dateFormat;
            // $('#startDate').text($.datepicker.formatDate(dateFormat, startDate, inst.settings));
            // $('#endDate').text($.datepicker.formatDate(dateFormat, endDate, inst.settings));
            console.log($.datepicker.formatDate(dateFormat, startDate, inst.settings) + " "
                + $.datepicker.formatDate(dateFormat, endDate, inst.settings));
            selectCurrentWeek();
        },
        beforeShowDay: function (date) {
            var cssClass = '';
            if (date >= startDate && date <= endDate)
                cssClass = 'ui-datepicker-current-day';
            return [true, cssClass];
        },
        onChangeMonthYear: function (year, month, inst) {
            selectCurrentWeek();
        }
    });

    $('.week-picker .ui-datepicker-calendar tr').on('mousemove', function () {
        $(this).find('td a').addClass('ui-state-hover');
    });
    $('.week-picker .ui-datepicker-calendar tr').on('mouseleave', function () {
        $(this).find('td a').removeClass('ui-state-hover');
    });
});

