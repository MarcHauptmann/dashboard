package marc.dashboard.weather;

import marc.dashboard.config.Configuration;
import marc.dashboard.weather.api.*;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Named
@RequestScoped
public class WeatherBean {
    public static final double KELVIN_DIFFERENCE = 273.15;
    public static final int FORECAST_POINTS = 12;

    @Inject
    Configuration configuration;

    @Inject
    OpenWeatherApi api;

    private WeatherData weatherData;

    private Forecast forecast;

    @PostConstruct
    public void initialize() {
        weatherData = api.getWeather(configuration.getPlace(), "de");
        forecast = api.getForecast(configuration.getPlace(), "de");
    }

    public double getTemperatureInCelsius() {
        return weatherData.getMain().getTemp() - KELVIN_DIFFERENCE;
    }

    public double getPressure() {
        return weatherData.getMain().getPressure();
    }

    public double getHumidity() {
        return weatherData.getMain().getHumidity() / 100.;
    }

    public Wind getWind() {
        return weatherData.getWind();
    }

    public String getLocationName() {
        return weatherData.getName();
    }

    public String getConditionString() {
        return getWeather()
                .map(weatherCondition -> weatherCondition.getDescription())
                .orElse("?");
    }

    public String getIconUrl() {
        return getWeather()
                .map(weatherCondition -> weatherCondition.getIconUrlString())
                .orElse("");
    }

    public String getIcon() {
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

    private Optional<WeatherCondition> getWeather() {
        return weatherData.getWeather().stream().findFirst();
    }

    public CartesianChartModel getForecastModel() {
        LineChartModel forecastModel = new LineChartModel();
        forecastModel.addSeries(createRainSeries());
        forecastModel.addSeries(createTemperatureSeries());

        forecastModel.getAxes().put(AxisType.X, createDateAxis());
        forecastModel.getAxes().put(AxisType.Y, new LinearAxis("Temperatur/°C"));
        forecastModel.getAxes().put(AxisType.Y2, new LinearAxis("Niederschlag/mm"));
        forecastModel.setShowDatatip(false);
        forecastModel.setMouseoverHighlight(false);
        forecastModel.setShowPointLabels(false);

        Axis y2Axis = forecastModel.getAxis(AxisType.Y2);
        y2Axis.setMin(0);

        return forecastModel;
    }

    private DateAxis createDateAxis() {
        DateAxis axis = new DateAxis();
        axis.setTickAngle(-50);
        axis.setTickFormat("%H:%M");
        return axis;
    }

    private ChartSeries createRainSeries() {
        LineChartSeries rainSeries = new LineChartSeries();
        rainSeries.setLabel("Niederschlag");

        Map<Object, Number> rainData = forecast.getData().stream()
                .limit(FORECAST_POINTS)
                .collect(toMap(this::toDate, this::toRain));

        rainSeries.setData(rainData);
        rainSeries.setXaxis(AxisType.X);
        rainSeries.setYaxis(AxisType.Y2);

        return rainSeries;
    }

    private String toDate(ForecastData forecastData) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return dateFormat.format(forecastData.getDate());
    }

    private double toRain(ForecastData forecastData) {
        return forecastData.getRainData().getAmount3h();
    }

    private ChartSeries createTemperatureSeries() {
        LineChartSeries temperatureSeries = new LineChartSeries();
        temperatureSeries.setLabel("Temperatur");

        Map<Object, Number> temperatures = forecast.getData().stream()
                .limit(FORECAST_POINTS)
                .collect(toMap(this::toDate, this::toTemperature));

        temperatureSeries.setData(temperatures);
        temperatureSeries.setXaxis(AxisType.X);
        temperatureSeries.setYaxis(AxisType.Y);

        return temperatureSeries;
    }

    private double toTemperature(ForecastData forecastData) {
        return forecastData.getMain().getTemp() - KELVIN_DIFFERENCE;
    }

    private <T> Function<T, Integer> timeSeries() {
        return new Function<T, Integer>() {
            private int i = 0;

            @Override
            public Integer apply(T aDouble) {
                return i += 3;
            }
        };
    }
}
