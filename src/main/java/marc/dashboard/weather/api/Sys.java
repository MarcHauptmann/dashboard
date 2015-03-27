package marc.dashboard.weather.api;

import javax.xml.bind.annotation.XmlElement;

public class Sys {
        @XmlElement (name = "type")
        int type;

        @XmlElement (name = "id")
        long id;

        @XmlElement (name = "message")
        double message;

        @XmlElement (name = "country")
        String country;

        @XmlElement (name = "sunrise")
        long sunrise;

        @XmlElement (name = "sunset")
        long sunset;
}
