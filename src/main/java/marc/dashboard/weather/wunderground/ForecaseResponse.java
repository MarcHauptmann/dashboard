package marc.dashboard.weather.wunderground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import marc.dashboard.efa.api.Date;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecaseResponse {
    @XmlElement(name = "hourly_forecast")
    List<Forecast> forecasts;

    @Override
    public String toString() {
        return "ForecaseResponse{" +
                "forecasts=" + forecasts +
                '}';
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }
}
