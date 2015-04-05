package marc.dashboard.efa.api;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "odvNameElem")
public class OdvNameElement {
    @XmlAttribute
    long id;

    @XmlAttribute
    String locality;

    @XmlAttribute
    String objectName;

    @XmlAttribute
    Type anyType;

    public long getId() {
        return id;
    }

    public String getLocality() {
        return locality;
    }

    public String getObjectName() {
        return objectName;
    }

    public Type getAnyType() {
        return anyType;
    }
}
