package marc.dashboard;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

@ManagedBean
@ApplicationScoped
public class HelloBean {
    @Inject
    private Greeter greeter;

    public String getGreeting() {
        return greeter.getGreetingText();
    }
}
