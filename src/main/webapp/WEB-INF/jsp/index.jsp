<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>

    <spring:url value="/resources/css/style.css" var="cssUrl"/>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapUrl"/>
    <spring:url value="/resources/img/hawcons/icon-69-thermometer-half.png" var="thermometerIconUrl"/>
    <spring:url value="/resources/img/hawcons/icon-43-wind.png" var="windIconUrl"/>
    <spring:url value="/resources/img/hawcons/icon-52-barometer.png" var="pressureIconUrl"/>

    <link rel="stylesheet" href="${cssUrl}">
    <link rel="stylesheet" href="${bootstrapUrl}">

    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/highcharts.js"></script>
    <script src="resources/js/main.js"></script>

    <script type="application/javascript">
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
                                    <img id="currentWeather-icon" src="resources/img/weatherIcons/dunno.png">

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
