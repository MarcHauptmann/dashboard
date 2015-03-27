package marc.dashboard.weather;

import javax.xml.bind.annotation.XmlElement;

public class Main {
        @XmlElement (name = "temp")
        double temp;

        @XmlElement (name = "pressure")
        int pressure;

        @XmlElement (name = "temp_min")
        double tempMin;

        @XmlElement (name = "temp_max")
        double tempMax;

        @XmlElement (name = "humidity")
        int humidity;
}
