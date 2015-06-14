package marc.dashboard.weather;

import marc.dashboard.weather.wunderground.WUndergroundWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    WeatherService weatherService;

    @RequestMapping(value = "current", method = RequestMethod.GET)
    @ResponseBody
    public WeatherConditions getCurrentWeather() {
        WeatherConditions conditions = new WeatherConditions();
        conditions.setTemperature(weatherService.getCurrentTemperature());
        conditions.setIcon(weatherService.getCurrentIcon());
        conditions.setHumidity(weatherService.getCurrentHumidity());
        conditions.setWindSpeed(weatherService.getCurrentWindSpeed());
        conditions.setWindDirection(weatherService.getCurrentWindSpeed());
        conditions.setDescription(weatherService.getCurrentConditionString());

        return conditions;
    }

    @RequestMapping(value = "rain", method = RequestMethod.GET)
    @ResponseBody
    public List<WUndergroundWeatherService.DateDoublePair> getRainData() {
        Map<Date, Double> rainForecast = weatherService.getRainForecast();

        return rainForecast.keySet().stream()
                .map(date -> new WUndergroundWeatherService.DateDoublePair(date, rainForecast.get(date)))
                .sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate())).collect(toList());
    }

    @RequestMapping(value = "temperature", method = RequestMethod.GET)
    @ResponseBody
    public List<WUndergroundWeatherService.DateDoublePair> getTemperatureData() {
        Map<Date, Double> temparatureForecast = weatherService.getTemparatureForecast();

        return temparatureForecast.keySet().stream()
                .map(date -> new WUndergroundWeatherService.DateDoublePair(date, temparatureForecast.get(date)))
                .sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate())).collect(toList());
    }

    public Map<Long, Double> getRainForecast() {
        return convertDates(weatherService.getRainForecast());
    }

    public Map<Long, Double> getTemperatureForecast() {
        return convertDates(weatherService.getTemparatureForecast());
    }

    private Map<Long, Double> convertDates(Map<Date, Double> rainForecast1) {
        Map<Long, Double> value = new HashMap<>();

        Map<Date, Double> rainForecast = rainForecast1;
        rainForecast.keySet().stream().forEach(date -> {
            value.put(date.getTime(), rainForecast.get(date));
        });

        return value;
    }
}
