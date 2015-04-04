package marc.dashboard.weather.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    public double getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public int getHumidity() {
        return humidity;
    }
}
