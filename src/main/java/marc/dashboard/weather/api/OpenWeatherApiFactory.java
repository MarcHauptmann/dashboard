package marc.dashboard.weather.api;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.inject.Produces;

public class OpenWeatherApiFactory {
    @Produces
    public static OpenWeatherApi createOpenWeatherApi() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://api.openweathermap.org/data/2.5");

        OpenWeatherApi api = target.proxy(OpenWeatherApi.class);

        return api;
    }
}
