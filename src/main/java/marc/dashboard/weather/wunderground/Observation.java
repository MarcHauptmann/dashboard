package marc.dashboard.weather.wunderground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Observation {
    @XmlElement(name = "weather")
    private String weather;

    @XmlElement(name = "temp_c")
    private double temperature;

    @XmlElement(name = "dewpoint_c")
    private double dewpoint;

    @XmlElement(name = "pressure_mb")
    private double pressure;

    @XmlElement(name = "wind_kph")
    private double windSpeed;

    @XmlElement(name = "wind_degrees")
    private double windDirection;

    @XmlElement(name = "icon")
    private String iconName;

    @Override
    public String toString() {
        return "Observation{" +
                "weather='" + weather + '\'' +
                ", temperature=" + temperature +
                ", dewpoint=" + dewpoint +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                ", iconName='" + iconName + '\'' +
                '}';
    }

    public String getWeather() {
        return weather;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public String getIconName() {
        return iconName;
    }

    public double getDewpoint() {
        return dewpoint;
    }
}
