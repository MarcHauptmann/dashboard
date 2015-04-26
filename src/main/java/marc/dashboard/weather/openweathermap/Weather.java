package marc.dashboard.weather.openweathermap;

import javax.xml.bind.annotation.XmlElement;

public class Weather {
        @XmlElement (name = "id")
        long id;

        @XmlElement (name = "main")
        String main;

        @XmlElement (name = "description")
        String description;

        @XmlElement (name = "icon")
        String icon;
}
