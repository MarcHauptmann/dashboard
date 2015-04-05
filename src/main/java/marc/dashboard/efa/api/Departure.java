package marc.dashboard.efa.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdDeparture")
public class Departure {
    @XmlAttribute(name = "nameWO")
    String stationName;

    @XmlElement(name = "itdDateTime")
    DateTime dateTime;

    @XmlElement(name = "itdRTDateTime")
    DateTime realtimeDateTime;

    @XmlElementRef
    ServingLine servingLine;

    @XmlAttribute(name = "countdown")
    int countdown;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getStationName() {
        return stationName;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public DateTime getRealtimeDateTime() {
        if (realtimeDateTime != null) {
            return realtimeDateTime;
        } else
            return dateTime;
    }

    public ServingLine getServingLine() {
        return servingLine;
    }

    public int getCountdown() {
        return countdown;
    }
}
