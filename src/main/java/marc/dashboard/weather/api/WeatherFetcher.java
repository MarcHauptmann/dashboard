package marc.dashboard.weather.api;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class WeatherFetcher {

    public static void main(String[] args) {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://api.openweathermap.org/data/2.5");

        OpenWeatherApi api = target.proxy(OpenWeatherApi.class);

        System.out.println(api.getWeather("Hannover"));
    }
}
