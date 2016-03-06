package marc.dashboard.efa.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdServingLine")
public class ServingLine {

    @XmlAttribute(name = "number")
    String number;

    @XmlAttribute(name = "direction")
    String direction;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getNumber() {
        return number;
    }

    public String getDirection() {
        return direction;
    }
}
