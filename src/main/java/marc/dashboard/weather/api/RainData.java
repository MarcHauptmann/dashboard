package marc.dashboard.weather.api;

import javax.xml.bind.annotation.XmlElement;

public class RainData {
    @XmlElement(name = "3h")
    private double amount3h = 0;

    public double getAmount3h() {
        return amount3h;
    }
}
