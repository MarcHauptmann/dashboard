package marc.dashboard.weather.api;

import javax.xml.bind.annotation.XmlElement;

public class Wind {
        @XmlElement (name = "speed")
        double speed;

        @XmlElement (name = "gust")
        double gust;

        @XmlElement (name = "deg")
        int deg;
}
