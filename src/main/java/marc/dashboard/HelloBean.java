package marc.dashboard;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class HelloBean {
    public String getGreeting() {
        return "Hallo Welt!";
    }
}
