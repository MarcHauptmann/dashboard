package marc.dashboard.weather;

import java.util.Date;
import java.util.Map;

public interface WeatherService {
    double getCurrentPressure();

    double getCurrentTemperature();

    double getCurrentHumidity();

    double getCurrentWindSpeed();

    int getCurrentWindDirection();

    String getCurrentConditionString();

    String getCurrentIcon();

    Map<Date, Double> getRainForecast();

    Map<Date, Double> getTemparatureForecast();
}
