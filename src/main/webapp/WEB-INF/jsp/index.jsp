<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>

    <spring:url value="/resources/js/jquery-2.1.4.min.js" var="jqueryUrl"/>
    <spring:url value="/resources/js/highcharts.js" var="highchartsUrl"/>
    <spring:url value="/resources/css/style.css" var="cssUrl"/>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapUrl"/>
    <spring:url value="/resources/hawcons/icon-69-thermometer-half.png" var="thermometerIconUrl"/>
    <spring:url value="/resources/hawcons/icon-43-wind.png" var="windIconUrl"/>
    <spring:url value="/resources/hawcons/icon-52-barometer.png" var="pressureIconUrl"/>

    <link rel="stylesheet" href="${cssUrl}">
    <link rel="stylesheet" href="${bootstrapUrl}">

    <script src="${jqueryUrl}"></script>
    <script src="${highchartsUrl}"></script>

    <script type="application/javascript">
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
            $("#currentWeather-icon").attr("src", "resources/weatherIcons/" + weather.icon + ".png");
        }

        function updateDepartures(response) {
            console.log("updating departures");

            var tableBody = $("#departureTable tbody");

            tableBody.empty();

            function lineIconImage(line) {
                if (line < 100) {
                    return $("<img>").attr("src", "resources/efaIcons/u_bahn.gif");
                } else {
                    return $("<img>").attr("src", "resources/efaIcons/bus.gif");
                }
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
                    if (num < 10)
                        return "0" + num;
                    else
                        return num;
                }

                return twoDigits(date.getHours()) + ":" + twoDigits(date.getMinutes());
            }

            $.each($(response).slice(0, 13), function (index, departure) {

                tableBody.append($("<tr>")
                        .append($("<td>").append(lineIconImage(departure.line)))
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
            if (num <= 9)
                return "0" + num;
            else
                return num;
        }

        $(function () {
            var chartContainer = $('#weatherChart');

            initChart(chartContainer);

            var MINUTES = 60 * 1000;

            pollJSON("weather/current", updateCurrentWeather, 15 * MINUTES);
            pollJSON("weather/temperature", updateTemperatureChart, 15 * MINUTES);
            pollJSON("weather/rain", updateRainChart, 15 * MINUTES);

            pollJSON("efa/departures", updateDepartures, 20 * 1000);

            pollJSON("todo/today", updateTodo, 15 * MINUTES);

            setInterval(updateClock, 250);
        });
    </script>
    <head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-6">
            <div class="row">
                <div class="col-md-7">
                    <div id="currentWeather" class="panel panel-default">
                        <div class="panel-heading">Aktuelles Wetter</div>
                        <div class="panel-body">
                            <div>
                                <div id="weatherIcon">
                                    <img id="currentWeather-icon" src="resources/weatherIcons/dunno.png">

                                    <div id="currentWeather-description"></div>
                                </div>

                                <div class="weather-value temperature">
                                    <img src="${thermometerIconUrl}"/>

                                    <label for="currentWeather-temperature">Temperatur:</label>

                                    <div class="value-field">
                                        <span id="currentWeather-temperature"></span>
                                        <span class="unit">°C</span>
                                    </div>
                                </div>
                            </div>

                            <div class="weather-value pressure">
                                <img src="${pressureIconUrl}"/>

                                <label for="currentWeather-pressure">Druck:</label>

                                <div class="value-field">
                                    <span id="currentWeather-pressure"></span>
                                    <span class="unit">hpa</span>
                                </div>
                            </div>

                            <div class="weather-value wind">
                                <img src="${windIconUrl}"/>

                                <label for="currentWind">Wind:</label>

                                <div id="currentWind" class="value-field">
                                    <span id="currentWeather-windSpeed"></span>
                                    <span class="unit">km/h</span> /
                                    <span id="currentWeather-windDirection"></span>
                                    <span class="unit">°</span>
                                </div>
                            </div>

                            <div class="weather-value humidity">
                                <label for="currentWeather-humidity">Luftfeuchtigkeit:</label>

                                <div class="value-field">
                                    <span id="currentWeather-humidity"></span>
                                    <span class="unit">%</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-5">
                    <div id="clockPanel" class="panel panel-default">
                        <div class="panel-heading">Uhrzeit</div>
                        <div class="panel-body">
                            <div id="clock"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">Vorhersage</div>
                <div class="panel-body">
                    <div id="weatherChart" style="width: 100%;"></div>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Nächste Abfahrten
                </div>
                <div class="panel-body">
                    <table id="departureTable" class="table table-condensed">
                        <colgroup>
                            <col width="5%"/>
                            <col width="5%"/>
                            <col width="40%"/>
                            <col width="30%"/>
                            <col width="7%"/>
                            <col width="7%"/>
                            <col width="6%"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th colspan="2">Linie</th>
                            <th>Richtung</th>
                            <th>Station</th>
                            <th colspan="3">Zeit</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    Heute zu erledigen
                </div>
                <div class="panel-body">
                    <div id="todoList"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
