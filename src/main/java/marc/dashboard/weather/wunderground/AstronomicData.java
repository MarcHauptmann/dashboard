package marc.dashboard.weather.wunderground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElementRef;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AstronomicData {

    @XmlElementRef(name = "sun_phase")
    private SunPhase sunPhase;

    @Override
    public String toString() {
        return "AstronomicData{" +
                "sunPhase=" + sunPhase +
                '}';
    }

    public SunPhase getSunPhase() {
        return sunPhase;
    }

}
