package marc.dashboard.weather;

import marc.dashboard.cdi.Primary;
import marc.dashboard.weather.wunderground.WUndergroundWeatherService;
import org.primefaces.model.chart.*;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Named
@RequestScoped
public class WeatherBean {

    @Inject
    @Primary
    private WeatherService weatherService;

    public double getTemperatureInCelsius() {
        return weatherService.getCurrentTemperature();
    }

    public double getPressure() {
        return weatherService.getCurrentPressure();
    }

    public double getHumidity() {
        return weatherService.getCurrentHumidity();
    }

    public double getWindSpeed() {
        return weatherService.getCurrentWindSpeed();
    }

    public double getWindDirection() {
        return weatherService.getCurrentWindDirection();
    }

    public String getConditionString() {
        return weatherService.getCurrentConditionString();
    }

    public String getIcon() {
        return weatherService.getCurrentIcon();
    }

    public List<WUndergroundWeatherService.DateDoublePair> getRainData() {
        Map<Date, Double> rainForecast = weatherService.getRainForecast();

        return rainForecast.keySet().stream()
                .map(date -> new WUndergroundWeatherService.DateDoublePair(date, rainForecast.get(date)))
                .sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate())).collect(toList());
    }

    public List<WUndergroundWeatherService.DateDoublePair> getTemperatureData() {
        Map<Date, Double> temparatureForecast = weatherService.getTemparatureForecast();

        return temparatureForecast.keySet().stream()
                .map(date -> new WUndergroundWeatherService.DateDoublePair(date, temparatureForecast.get(date)))
                .sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate())).collect(toList());
    }
}
