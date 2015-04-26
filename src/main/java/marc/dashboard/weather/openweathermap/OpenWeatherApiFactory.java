package marc.dashboard.weather.openweathermap;

import marc.dashboard.weather.openweathermap.OpenWeatherMapApi;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.inject.Produces;

public class OpenWeatherApiFactory {
    @Produces
    public static OpenWeatherMapApi createOpenWeatherApi() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://api.openweathermap.org/data/2.5");

        OpenWeatherMapApi api = target.proxy(OpenWeatherMapApi.class);

        return api;
    }
}
