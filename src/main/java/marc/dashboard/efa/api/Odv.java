package marc.dashboard.efa.api;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdOdv")
public class Odv {
    @XmlElementRef
    OdvName odvName;

    public OdvName getOdvName() {
        return odvName;
    }
}
