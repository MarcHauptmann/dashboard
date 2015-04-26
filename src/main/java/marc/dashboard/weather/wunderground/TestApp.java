package marc.dashboard.weather.wunderground;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.ClientBuilder;
import java.time.LocalTime;

public class TestApp {
    public static void main(String[] args) {
        String apiKey = "???";

        ResteasyClient client = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = client.target("http://api.wunderground.com/api/").path(apiKey);

        WUndergroundApi api = target.proxy(WUndergroundApi.class);

        AstronomicData astronomicData = api.getAstronomicData("Germany", "Hannover");
        System.out.println(astronomicData);


        LocalTime now = LocalTime.now();
        SunPhase sunPhase = astronomicData.getSunPhase();

        if (now.isAfter(sunPhase.getSunrise()) && now.isBefore(sunPhase.getSunset())) {
            System.out.println("Tag");
        } else {
            System.out.println("Nacht");
        }
    }
}
