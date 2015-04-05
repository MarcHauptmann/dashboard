package marc.dashboard.efa.api;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdRequest")
public class DepartureResponse {
    @XmlElementRef
    DepartureInfo departureInfo;

    public DepartureInfo getDepartureInfo() {
        return departureInfo;
    }
}
