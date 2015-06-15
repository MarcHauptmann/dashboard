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

            $("#currentTemperature").text(Math.round(weather.temperature));
            $("#currentHumidity").text(Math.round(weather.humidity));
            $("#currentWindSpeed").text(Math.round(weather.windSpeed));
            $("#currentWindDirection").text(Math.round(weather.windDirection));
            $("#currentDescription").text(weather.description);
            $("#currentIcon").attr("src", "resources/weatherIcons/" + weather.icon + ".png");
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

            $.each($(response).slice(0, 16), function (index, departure) {
                tableBody.append($("<tr>")
                                .append($("<td>").append(lineIconImage(departure.line)))
                                .append($("<td>").text(departure.line))
                                .append($("<td>").text(departure.direction))
                                .append($("<td>").text(departure.station))
                                .append($("<td>").text(formatCountdown(departure.countdown)))
                                .append($("<td>").text(getDelay(departure.delay)))
                );
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

        $(function () {
            var chartContainer = $('#weatherChart');

            initChart(chartContainer);

            var MINUTES = 60 * 1000;

            pollJSON("weather/current", updateCurrentWeather, 15 * MINUTES);
            pollJSON("weather/temperature", updateTemperatureChart, 15 * MINUTES);
            pollJSON("weather/rain", updateRainChart, 15 * MINUTES);

            pollJSON("efa/departures", updateDepartures, 20 * 1000);
        });
    </script>
<head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-6">
            <div id="currentWeather" class="panel panel-default clearfix" style="width: 100%;">
                <div class="panel-heading">Aktuelles Wetter</div>
                <div class="panel-body">
                    <div id="weatherIcon">
                        <img id="currentIcon" src="resources/weatherIcons/dunno.png">

                        <div id="currentDescription"></div>
                    </div>

                    <div class="weather-value">
                        <label for="currentTemperature">Temperatur:</label>

                        <div class="value-field">
                            <span id="currentTemperature"></span>
                            <span class="unit">°C</span>
                        </div>
                    </div>

                    <div class="weather-value">
                        <label for="currentHumidity">Luftfeuchtigkeit:</label>

                        <div class="value-field">
                            <span id="currentHumidity"></span>
                            <span class="unit">%</span>
                        </div>
                    </div>

                    <div class="weather-value">
                        <label for="currentWind">Luftfeuchtigkeit:</label>

                        <div id="currentWind" class="value-field">
                            <span id="currentWindSpeed"></span>
                            <span class="unit">km/h</span> /
                            <span id="currentWindDirection"></span>
                            <span class="unit">°</span>
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
                    <table id="departureTable" class="table table-condensed" style="width: 100%;">
                        <colgroup>
                            <col width="5%"/>
                            <col width="5%"/>
                            <col width="45%"/>
                            <col width="30%"/>
                            <col width="10%"/>
                            <col width="5%"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th colspan="2">Linie</th>
                            <th>Richtung</th>
                            <th>Station</th>
                            <th colspan="2">Zeit</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
