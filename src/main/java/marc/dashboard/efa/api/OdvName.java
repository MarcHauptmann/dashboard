package marc.dashboard.efa.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "itdOdvName")
public class OdvName {
    @XmlElement(name = "odvNameElem")
    List<OdvNameElement> names;

    public List<OdvNameElement> getNames() {
        return names;
    }
}
