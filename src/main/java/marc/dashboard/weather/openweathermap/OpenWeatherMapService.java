package marc.dashboard.weather.openweathermap;

import marc.dashboard.config.Configuration;
import marc.dashboard.weather.WeatherService;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

//@RequestScoped
public class OpenWeatherMapService implements WeatherService {
    public static final double KELVIN_DIFFERENCE = 273.15;
    public static final int FORECAST_POINTS = 12;

//    @Inject
    marc.dashboard.weather.openweathermap.OpenWeatherMapApi api;

//    @Inject
    Configuration configuration;

    WeatherData weatherData;
    Forecast forecast;

    @PostConstruct
    public void initialize() {
        String place = configuration.getPlace();

        weatherData = api.getWeather(place, "de");
        forecast = api.getForecast(place, "de");
    }

    @Override
    public double getCurrentPressure() {
        return weatherData.getMain().getPressure();
    }

    @Override
    public double getCurrentTemperature() {
        return weatherData.getMain().getTemp() - KELVIN_DIFFERENCE;
    }

    @Override
    public double getCurrentHumidity() {
        return weatherData.getMain().getHumidity() / 100.;
    }

    @Override
    public double getCurrentWindSpeed() {
        return weatherData.getWind().getSpeed();
    }

    @Override
    public int getCurrentWindDirection() {
        return weatherData.getWind().getDeg();
    }

    @Override
    public String getCurrentConditionString() {
        return getWeather()
                .map(weatherCondition -> weatherCondition.getDescription())
                .orElse("?");
    }

    private Optional<WeatherCondition> getWeather() {
        return weatherData.getWeather().stream().findFirst();
    }

    @Override
    public String getCurrentIcon() {
        String iconName = getWeather().map(weatherCondition -> weatherCondition.getIcon()).orElse("?");

        Map<String, String> translation = new HashMap<>();
        translation.put("01d", "sunny");
        translation.put("01n", "sunny_night");
        translation.put("02d", "cloudy1");
        translation.put("02n", "cloudy1_night");
        translation.put("03d", "cloudy3");
        translation.put("03n", "cloudy3_night");
        translation.put("04d", "cloudy4");
        translation.put("04n", "cloudy4_night");
        translation.put("09d", "shower1");
        translation.put("09n", "shower1_night");
        translation.put("10d", "shower2");
        translation.put("10n", "shower2_night");
        translation.put("11d", "tstorm2");
        translation.put("11n", "tstorm2_night");
        translation.put("13d", "snow3");
        translation.put("13n", "snow3_night");
        translation.put("50d", "fog");
        translation.put("50n", "fog_night");

        String returnValue = translation.get(iconName);

        if (returnValue == null) {
            return "dunno";
        } else {
            return returnValue;
        }
    }

    private double toRain(ForecastData forecastData) {
        return forecastData.getRainData().getAmount3h();
    }

    private double toTemperature(ForecastData forecastData) {
        return forecastData.getMain().getTemp() - KELVIN_DIFFERENCE;
    }

    @Override
    public Map<Date, Double> getRainForecast() {
        return forecast.getData().stream()
                .limit(FORECAST_POINTS)
                .collect(toMap(ForecastData::getDate, this::toRain));
    }

    @Override
    public Map<Date, Double> getTemparatureForecast() {
        return forecast.getData().stream()
                .limit(FORECAST_POINTS)
                .collect(toMap(ForecastData::getDate, this::toTemperature));
    }
}
