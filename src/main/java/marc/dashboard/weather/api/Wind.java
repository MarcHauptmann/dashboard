package marc.dashboard.weather.api;

import javax.xml.bind.annotation.XmlElement;

public class Wind {
        @XmlElement (name = "speed")
        double speed;

        @XmlElement (name = "gust")
        double gust;

        @XmlElement (name = "deg")
        int deg;

        public double getSpeed() {
                return speed;
        }

        public double getGust() {
                return gust;
        }

        public int getDeg() {
                return deg;
        }
}
