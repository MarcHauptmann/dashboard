package marc.dashboard.weather;

import marc.dashboard.config.Configuration;
import marc.dashboard.weather.api.OpenWeatherApi;
import marc.dashboard.weather.api.WeatherData;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

@ManagedBean
@RequestScoped
public class WeatherBean {
    public static final double KELVIN_DIFFERENCE = 273.15;

    @Inject
    Configuration configuration;

    private WeatherData weatherData;

    private WeatherData loadWeather() {
        if (weatherData == null) {
            ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

            ResteasyWebTarget target = resteasyClient.target("http://api.openweathermap.org/data/2.5");

            OpenWeatherApi api = target.proxy(OpenWeatherApi.class);

            weatherData = api.getWeather(configuration.getPlace());
        }

        return weatherData;
    }

    public double getTemperatureInCelsius() {
        return loadWeather().getMain().getTemp() - KELVIN_DIFFERENCE;
    }
}
