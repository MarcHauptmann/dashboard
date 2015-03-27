package marc.dashboard.weather.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public interface OpenWeatherApi {

    @Path ("weather")
    @GET
    WeatherData getWeather(@QueryParam ("q") String city);
}
