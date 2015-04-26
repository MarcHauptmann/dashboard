package marc.dashboard.weather.wunderground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @XmlElement(name = "FCTTIME")
    @JsonDeserialize(using = FctTimeDeserializer.class)
    private Date time;

    @XmlElement(name = "temp")
    @JsonDeserialize(using = MetricValueDeserializer.class)
    private double temperature;

    @XmlElement(name = "qpf")
    @JsonDeserialize(using = MetricValueDeserializer.class)
    private double rain;

    @Override
    public String toString() {
        return "Forecast{" +
                "time=" + time +
                ", temperature=" + temperature +
                ", rain=" + rain +
                '}';
    }

    public Date getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getRain() {
        return rain;
    }
}
