package marc.dashboard.efa.api;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itdRequest")
public class StopResponse {
    @XmlElementRef
    StopFinderRequest finder;

    public StopFinderRequest getFinder() {
        return finder;
    }
}
