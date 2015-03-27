package marc.dashboard.weather.api;

import javax.xml.bind.annotation.XmlElement;

public class Coordinate {
    @XmlElement (name = "lat")
    double lat;

    @XmlElement (name = "lon")
    double lon;
}
