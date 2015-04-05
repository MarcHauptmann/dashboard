package marc.dashboard.efa.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "itdDepartureMonitorRequest")
public class DepartureInfo {
    @XmlElementRef
    @XmlElementWrapper(name = "itdDepartureList")
    List<Departure> departures;

    @XmlElement(name = "itdDateTime")
    DateTime dateTime;

    public List<Departure> getDepartures() {
        return departures;
    }

    public DateTime getDateTime() {
        return dateTime;
    }
}
