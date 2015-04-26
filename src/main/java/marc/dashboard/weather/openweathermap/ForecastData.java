package marc.dashboard.weather.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastData {
    @XmlElement(name = "dt")
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Date date;

    @XmlElementRef(name = "main")
    private Main main;

    @XmlElementRef(name = "weather")
    private List<WeatherCondition> weatherConditions;

    @XmlElementRef(name = "rain")
    private RainData rainData = new RainData();

    public Main getMain() {
        return main;
    }

    public List<WeatherCondition> getWeatherConditions() {
        return weatherConditions;
    }

    public RainData getRainData() {
        return rainData;
    }

    public Date getDate() {
        return date;
    }
}
