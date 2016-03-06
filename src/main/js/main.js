function initChart(chartContainer) {
    Highcharts.setOptions({
        lang: {
            months: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli',
                'August', 'September', 'Oktober', 'November', 'Dezember'],
            shortMonths: ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'],
            weekdays: ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag']
        }
    });

    chartContainer.highcharts({
        chart: {
            animation: false,
            type: 'line'
        },
        legend: {
            enabled: false
        },
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            title: {
                text: 'Zeit'
            },
            dateTimeLabelFormats: {
                day: '%A',
            }
        },
        yAxis: [{
            title: {
                text: 'Temperatur/°C'
            },
            maxPadding: 0.0
        }, {
            title: {
                text: 'Niederschlag/mm'
            },
            opposite: true
        }],
        series: [{
            type: 'areaspline',
            color: '#F0C000',
            fillOpacity: 0.3,
            zIndex: 2,
            marker: {
                enabled: false
            },
            data: [[0, 0], [1, 1]]
        }, {
            type: 'column',
            color: '#7cb5ec',
            zIndex: 1,
            yAxis: 1,
            groupPadding: 0,
            pointPadding: 0,
            data: []
        }
        ],
        plotOptions: {
            series: {
                animation: false
            }
        }
    });
}

function pollJSON(url, handler, interval) {
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        timeout: 3000,
        success: handler,
        complete: setTimeout(function () {
            pollJSON(url, handler, interval);
        }, interval)
    });
}

function updateCurrentWeather(weather) {
    console.log("updating current weather");

    $("#currentWeather-temperature").text(Math.round(weather.temperature));
    $("#currentWeather-humidity").text(Math.round(weather.humidity));
    $("#currentWeather-windSpeed").text(Math.round(weather.windSpeed));
    $("#currentWeather-pressure").text(Math.round(weather.pressure));
    $("#currentWeather-windDirection").text(Math.round(weather.windDirection));
    $("#currentWeather-description").text(weather.description);
    $("#currentWeather-icon").attr("src", "resources/img/weatherIcons/" + weather.icon + ".png");
}

function updateDepartures(response) {
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

function updateTemperatureChart(response) {
    console.log("updating temperature data");

    var chart = $("#weatherChart").highcharts();
    var data = [];

    $.each(response, function (index, dataPoint) {
        data.push([dataPoint.date, dataPoint.value]);
    });

    chart.series[0].setData(data);
}

function updateRainChart(response) {
    console.log("updating rain data");

    var chart = $("#weatherChart").highcharts();
    var data = [];

    $.each(response, function (index, dataPoint) {
        data.push([dataPoint.date, dataPoint.value]);
    });

    chart.series[1].setData(data);
}

function updateClock() {
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

function updateTodo(response) {
    var todoList = $("#todoList");

    if (response.length > 0) {
        var list = $("<ul>");

        var table = $("<table>").addClass("table").addClass("table-condensed");

        $.each(response, function (index, item) {
            var date = new Date(item.date);

            var dateString = formatNumber(date.getHours()) + ":" + formatNumber(date.getMinutes());

            table.append($("<row>")
                .append($("<td>").addClass("time").text(dateString))
                .append($("<td>").addClass("title").text(item.title)));
        });

        todoList.empty();
        todoList.append(table);
    } else {
        todoList.empty();

        todoList.append($("<div>").addClass("emptyList")
            .append($("<span>").text("☺").addClass("smiley"))
            .append($("<span>").text("nichts …").addClass("text")));
    }
}

function formatNumber(num) {
    if (num <= 9) {
        return "0" + num;
    } else {
        return num;
    }
}