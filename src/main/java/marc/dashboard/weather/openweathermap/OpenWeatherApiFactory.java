package marc.dashboard.weather.openweathermap;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class OpenWeatherApiFactory {
//    @Produces
    public static OpenWeatherMapApi createOpenWeatherApi() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://api.openweathermap.org/data/2.5");

        OpenWeatherMapApi api = target.proxy(OpenWeatherMapApi.class);

        return api;
    }
}
