import formatNumber from "./string.js";

export default function updateClock() {
    var now = new Date();
    var hour = now.getHours();
    var minutes = now.getMinutes();
    var seconds = now.getSeconds();

    $("#clock").empty();
    $("#clock").append($("<span>").addClass("hour").text(formatNumber(hour)))
        .append($("<span>").addClass("minuteColon").text(":"))
        .append($("<span>").addClass("minute").text(formatNumber(minutes)))
        .append($("<span>").addClass("secondsColon").text(":"))
        .append($("<span>").addClass("seconds").text(formatNumber(seconds)));
}