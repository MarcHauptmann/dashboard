package marc.dashboard.weather;

import marc.dashboard.config.Configuration;
import marc.dashboard.weather.api.*;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Named
@RequestScoped
public class WeatherBean {
    public static final double KELVIN_DIFFERENCE = 273.15;
    public static final int FORECAST_POINTS = 8;

    @Inject
    Configuration configuration;

    private WeatherData weatherData;

    private Forecast forecast;

    @PostConstruct
    public void initialize() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://api.openweathermap.org/data/2.5");

        OpenWeatherApi api = target.proxy(OpenWeatherApi.class);

        weatherData = api.getWeather(configuration.getPlace(), "de");
        forecast = api.getForecast(configuration.getPlace(), "de");

        resteasyClient.close();
    }

    public double getTemperatureInCelsius() {
        return weatherData.getMain().getTemp() - KELVIN_DIFFERENCE;
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
