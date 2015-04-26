package marc.dashboard.weather.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @XmlElement(name = "list")
    private List<ForecastData> data;

    public List<ForecastData> getData() {
        return data;
    }
}
