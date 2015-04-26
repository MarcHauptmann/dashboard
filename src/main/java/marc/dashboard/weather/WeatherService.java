package marc.dashboard.weather;

import java.util.Map;

public interface WeatherService {
    double getCurrentPressure();

    double getCurrentTemperature();

    double getCurrentHumidity();

    double getCurrentWindSpeed();

    int getCurrentWindDirection();

    String getCurrentConditionString();

    String getCurrentIcon();

    Map<Object, Number> getRainForecast();

    Map<Object, Number> getTemparatureForecast();
}
