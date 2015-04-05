package marc.dashboard.efa.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdTime")
public class Time {
    @XmlAttribute(name = "hour")
    int hour;

    @XmlAttribute(name = "minute")
    int minute;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
