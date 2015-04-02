package marc.dashboard.weather;

import marc.dashboard.config.Configuration;
import marc.dashboard.weather.api.OpenWeatherApi;
import marc.dashboard.weather.api.WeatherData;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class WeatherBean {
    public static final double KELVIN_DIFFERENCE = 273.15;

    @Inject
    Configuration configuration;

    private WeatherData loadWeather() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://api.openweathermap.org/data/2.5");

        OpenWeatherApi api = target.proxy(OpenWeatherApi.class);

        WeatherData weatherData = api.getWeather(configuration.getPlace());

        resteasyClient.close();

        return weatherData;
    }

    public double getTemperatureInCelsius() {
        return 5; //loadWeather().getMain().getTemp() - KELVIN_DIFFERENCE;
    }
}
