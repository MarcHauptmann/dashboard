package marc.dashboard.weather.openweathermap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public interface OpenWeatherMapApi {

    @Path("weather")
    @GET
    WeatherData getWeather(@QueryParam("q") String city);


    @Path("weather")
    @GET
    WeatherData getWeather(@QueryParam("q") String city, @QueryParam("lang") String language);

    @Path("forecast")
    @GET
    Forecast getForecast(@QueryParam("q") String city, @QueryParam("lang") String language);
}
