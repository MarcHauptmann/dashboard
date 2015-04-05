package marc.dashboard.efa.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdDateTime")
public class DateTime {
    @XmlElementRef
    Date date;

    @XmlElementRef
    Time time;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
