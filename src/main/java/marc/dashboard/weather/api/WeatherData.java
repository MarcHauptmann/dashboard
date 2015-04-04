package marc.dashboard.weather.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import static java.lang.String.format;

@XmlRootElement
//@JsonIgnoreProperties (value = {"rain", "clouds", "dt", "id", "cod"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    @XmlElement (name = "name")
    String name;

    @XmlElementRef (name = "coord")
    Coordinate coordinate;

    @XmlElementRef (name = "sys")
    Sys sys;

    @XmlElementRef (name = "weather")
    List<WeatherCondition> weather;

    @XmlElement (name = "base")
    String base;

    @XmlElementRef (name = "main")
    Main main;

    @XmlElement (name = "wind")
    Wind wind;

    @Override
    public String toString() {
        return format("Wetter in %s:\n %s",
                      name, weather);
    }

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public List<WeatherCondition> getWeather() {
        return weather;
    }
}
