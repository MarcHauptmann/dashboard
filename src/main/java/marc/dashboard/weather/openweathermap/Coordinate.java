package marc.dashboard.weather.openweathermap;

import javax.xml.bind.annotation.XmlElement;

public class Coordinate {
    @XmlElement (name = "lat")
    double lat;

    @XmlElement (name = "lon")
    double lon;
}
