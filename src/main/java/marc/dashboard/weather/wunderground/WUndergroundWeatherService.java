package marc.dashboard.weather.wunderground;

import marc.dashboard.cdi.AppConfig;
import marc.dashboard.cdi.Primary;
import marc.dashboard.config.Configuration;
import marc.dashboard.weather.WeatherService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.*;

@RequestScoped
@Primary
public class WUndergroundWeatherService implements WeatherService {

    @Inject
    private Configuration configuration;

    private Observation observation;
    private List<Forecast> forecasts;
    private SunPhase sunPhase;

    @Inject
    @AppConfig(key = "wunderground.api.key")
    String apiKey;

    @PostConstruct
    public void initialize() {
        ResteasyClient client = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = client.target("http://api.wunderground.com/api/").path(apiKey);

        WUndergroundApi api = target.proxy(WUndergroundApi.class);

        WUndergroundResponse wUndergroundResponse = api.getData("Germany", configuration.getPlace());

        observation = wUndergroundResponse.getObservation();
        forecasts = wUndergroundResponse.getForecasts();
        sunPhase = wUndergroundResponse.getSunPhase();

        client.close();
    }

    @Override
    public double getCurrentPressure() {
        return observation.getPressure();
    }

    @Override
    public double getCurrentTemperature() {
        return observation.getTemperature();
    }

    @Override
    public double getCurrentHumidity() {
        return 100 * getSaturationVapourPressure(observation.getDewpoint()) /
                getSaturationVapourPressure(observation.getTemperature());
    }

    private double getSaturationVapourPressure(double temp) {
        double a = 7.6;
        double b = 240.7;

        return 6.1078 * pow(10, ((a * temp) / (b + temp)));
    }

    @Override
    public double getCurrentWindSpeed() {
        return observation.getWindSpeed();
    }

    @Override
    public int getCurrentWindDirection() {
        return (int) observation.getWindDirection();
    }

    @Override
    public String getCurrentConditionString() {
        return observation.getWeather();
    }

    @Override
    public String getCurrentIcon() {
        switch (observation.getIconName()) {
            case "clear":
            case "sunny":
                return decorateNight("sunny");
            case "mostlysunny":
                return decorateNight("cloudy1");
            case "partlysunny":
                return decorateNight("cloudy2");
            case "partlycloudy":
                return decorateNight("cloudy3");
            case "mostlycloudy":
                return decorateNight("cloudy4");
            case "cloudy":
                return "cloudy5";
            case "fog":
                return decorateNight("fog");
            case "hazy":
                return decorateNight("mist");
            case "rain":
                return "shower3";
            case "sleet":
                return "sleet";
            case "tstorms":
                return "tstorm3";
            case "snow":
                return "snow5";
            default:
                return "dunno";
        }
    }

    public String decorateNight(String icon) {
        LocalTime now = LocalTime.now();

        if (now.isBefore(sunPhase.getSunrise()) || now.isAfter(sunPhase.getSunset())) {
            return icon + "_night";
        } else {
            return icon;
        }
    }

    private String toDate(Forecast forecast) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return dateFormat.format(forecast.getTime());
    }

    @Override
    public Map<Object, Number> getRainForecast() {
        return forecasts.stream().collect(Collectors.toMap(this::toDate, Forecast::getRain));
    }

    @Override
    public Map<Object, Number> getTemparatureForecast() {
        return forecasts.stream().collect(Collectors.toMap(this::toDate, Forecast::getTemperature));
    }
}
