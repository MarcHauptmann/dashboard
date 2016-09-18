window.$ = require("jquery");

window.pollJSON = require("./polling.js");

window.initChart = require("./chart.js").initChart;
window.updateRainChart  = require("./chart.js").updateRainChart;
window.updateTemperatureChart = require("./chart.js").updateTemperatureChart;

window.updateCurrentWeather = require("./weather.js");

window.updateDepartures = require("./departures");

window.updateClock = require("./clock.js");

window.updateTodo = require("./todo.js");