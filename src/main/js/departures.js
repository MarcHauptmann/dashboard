export default function updateDepartures(response) {
    console.log("updating departures");

    var tableBody = $("#departureTable tbody");

    tableBody.empty();

    function lineIconImage(icon) {
        return $("<img>").attr("src", "resources/img/efaIcons/" + icon);
    }

    function getDelay(delay) {
        if (delay > 0) {
            return "(+" + delay + ")";
        } else {
            return "";
        }
    }

    function formatCountdown(countdown) {
        if (countdown == 0) {
            return "jetzt";
        } else {
            return countdown + " min";
        }
    }

    function formatTime(millis) {
        const date = new Date(millis);

        function twoDigits(num) {
            if (num < 10) {
                return "0" + num;
            } else {
                return num;
            }
        }

        return twoDigits(date.getHours()) + ":" + twoDigits(date.getMinutes());
    }

    $.each($(response).slice(0, 7), function (index, departure) {

        tableBody.append($("<tr>")
            .append($("<td>").append(lineIconImage(departure.icon)))
            .append($("<td>").text(departure.line))
            .append($("<td>").text(departure.direction))
            .append($("<td>").text(departure.station))
            .append($("<td>").addClass("departureTime")
                .text(formatTime(departure.realTime)))
            .append($("<td>").addClass("departureCountdown")
                .text(formatCountdown(departure.countdown)))
            .append($("<td>").addClass("departureDelay")
                .text(getDelay(departure.delay))));
    });
}
