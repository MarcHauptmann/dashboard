package marc.dashboard.weather.wunderground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
class SunPhase {
    @XmlElement(name = "sunrise")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime sunrise;

    @XmlElement(name = "sunset")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime sunset;

    @Override
    public String toString() {
        return "AstronomicData{" +
                "sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }
}
