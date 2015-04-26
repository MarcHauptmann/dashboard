package marc.dashboard.weather.wunderground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.xml.txw2.annotation.XmlElement;

import javax.xml.bind.annotation.XmlElementRef;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConditionsResponse {
    @XmlElementRef(name = "current_observation")
    Observation observation;

    @Override
    public String toString() {
        return "ConditionsResponse{" +
                "observation=" + observation +
                '}';
    }

    public Observation getObservation() {
        return observation;
    }
}
