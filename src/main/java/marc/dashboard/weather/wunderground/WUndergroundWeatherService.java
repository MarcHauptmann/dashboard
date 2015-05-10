package marc.dashboard.weather.wunderground;

import marc.dashboard.cdi.AppConfig;
import marc.dashboard.cdi.Primary;
import marc.dashboard.config.Configuration;
import marc.dashboard.weather.WeatherService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.*;
import static java.util.stream.Collectors.averagingDouble;

@ApplicationScoped
@Primary
public class WUndergroundWeatherService implements WeatherService {

    public static final int WINDOW_SIZE = 2;
    @Inject
    private Configuration configuration;

    private Observation observation;
    private List<Forecast> forecasts;
    private SunPhase sunPhase;

    @Inject
    @AppConfig(key = "wunderground.api.key")
    String apiKey;

    private WUndergroundApi api;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void initialize() {
        ResteasyClient client = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = client.target("http://api.wunderground.com/api/").path(apiKey);

        api = target.proxy(WUndergroundApi.class);

        updateWeatherData();
    }

    void updateWeatherData() {
        logger.info("updating weather data");

        WUndergroundResponse wUndergroundResponse = api.getData("Germany", configuration.getPlace());

        observation = wUndergroundResponse.getObservation();
        forecasts = wUndergroundResponse.getForecasts();
        sunPhase = wUndergroundResponse.getSunPhase();
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

    @Override
    public Map<Date, Double> getRainForecast() {
        return forecasts.stream().collect(Collectors.toMap(Forecast::getTime, Forecast::getRain));
    }

    public static class DateDoublePair {
        Date date;
        double value;

        public DateDoublePair(Date date, double value) {
            this.date = date;
            this.value = value;
        }

        public Date getDate() {
            return date;
        }

        public String getDateAsString() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        }

        public double getValue() {
            return value;
        }
    }

    @Override
    public Map<Date, Double> getTemparatureForecast() {
        List<DateDoublePair> list = new ArrayList<>();

        for (int i = 0; i < forecasts.size(); i++) {
            int startIndex = max(0, i - WINDOW_SIZE);
            int endIndex = min(forecasts.size() - 1, i + WINDOW_SIZE);

            Double temp = forecasts.subList(startIndex, endIndex).stream()
                    .collect(averagingDouble(Forecast::getTemperature));

            Date date = forecasts.get(i).getTime();

            list.add(new DateDoublePair(date, temp));
        }

        return list.stream()
                .collect(Collectors.toMap(DateDoublePair::getDate, DateDoublePair::getValue));
    }
}
