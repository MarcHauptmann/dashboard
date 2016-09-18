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

module.exports = updateCurrentWeather;