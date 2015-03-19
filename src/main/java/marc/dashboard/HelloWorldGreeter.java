package marc.dashboard;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Singleton;

@ApplicationScoped
@Singleton
public class HelloWorldGreeter implements Greeter {
    @Override
    public String getGreetingText() {
        return "Hallo Welt!";
    }
}