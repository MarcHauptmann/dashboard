import pollJSON from "./polling.js";
import updateCurrentWeather from "./weather.js";
import updateClock from "./clock";
import {initChart, updateRainChart, updateTemperatureChart} from "./chart.js"
import updateTodo from "./todo.js";
import updateDepartures from "./departures.js";

window.$ = require("jquery");

window.pollJSON = pollJSON;

window.updateCurrentWeather = updateCurrentWeather;

window.updateClock = updateClock;

window.initChart = initChart;
window.updateRainChart = updateRainChart;
window.updateTemperatureChart = updateTemperatureChart;

window.updateDepartures = updateDepartures;

window.updateTodo = updateTodo;