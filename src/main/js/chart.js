var Highcharts = require("highcharts");

function initChart(chartContainer) {
    Highcharts.setOptions({
        lang: {
            months: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli',
                'August', 'September', 'Oktober', 'November', 'Dezember'],
            shortMonths: ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'],
            weekdays: ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag']
        }
    });

    Highcharts.chart(chartContainer, {
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

function updateTemperatureChart(response) {
    console.log("updating temperature data");

    var chart = Highcharts.charts[0];
    var data = [];

    $.each(response, function (index, dataPoint) {
        data.push([dataPoint.date, dataPoint.value]);
    });

    chart.series[0].setData(data);
}

function updateRainChart(response) {
    console.log("updating rain data");

    var chart = Highcharts.charts[0];
    var data = [];

    $.each(response, function (index, dataPoint) {
        data.push([dataPoint.date, dataPoint.value]);
    });

    chart.series[1].setData(data);
}

module.exports = {
    initChart: initChart,
    updateRainChart: updateRainChart,
    updateTemperatureChart: updateTemperatureChart
};