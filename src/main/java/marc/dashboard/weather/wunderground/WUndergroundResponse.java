package marc.dashboard.weather.wunderground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WUndergroundResponse {
    @XmlElementRef(name = "current_observation")
    Observation observation;

    @XmlElement(name = "hourly_forecast")
    List<Forecast> forecasts;

    @XmlElementRef(name = "sun_phase")
    private SunPhase sunPhase;

    public Observation getObservation() {
        return observation;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public SunPhase getSunPhase() {
        return sunPhase;
    }
}
