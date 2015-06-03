package marc.dashboard.weather.wunderground;

import marc.dashboard.weather.openweathermap.WeatherData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface WUndergroundApi {

    @GET
    @Path("conditions/q/{country}/{city}.json")
    ConditionsResponse getConditions(@PathParam("country") String country, @PathParam("city") String city);


    @GET
    @Path("hourly/q/{country}/{city}.json")
    ForecaseResponse getHourlyForcast(@PathParam("country") String country, @PathParam("city") String city);

    @GET
    @Path("astronomy/q/{country}/{city}.json")
    AstronomicData getAstronomicData(@PathParam("country") String country, @PathParam("city") String city);

    @GET
    @Path("astronomy/conditions/hourly/q/{country}/{city}.json")
    WUndergroundResponse getData(@PathParam("country") String country, @PathParam("city") String city);
}
