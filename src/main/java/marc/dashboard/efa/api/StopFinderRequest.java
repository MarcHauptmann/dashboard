package marc.dashboard.efa.api;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdStopFinderRequest")
public class StopFinderRequest {
    @XmlElementRef
    Odv name;

    public Odv getName() {
        return name;
    }
}
