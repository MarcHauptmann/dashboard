package marc.dashboard.config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Bean to hold the configuration
 */
@Named
@ApplicationScoped
public class ConfigurationBean implements Configuration, Serializable {
    private String       place    = "Hannover";
    private List<String> stations = Arrays.asList("Isernhagener Str.", "Vahrenwalder Platz");

    @Override
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }
}