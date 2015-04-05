package marc.dashboard.efa.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdDate")
public class Date {
    @XmlAttribute(name = "year")
    int year;
    @XmlAttribute(name = "month")
    int month;
    @XmlAttribute(name = "day")
    int day;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
