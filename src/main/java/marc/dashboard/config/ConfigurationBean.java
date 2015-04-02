package marc.dashboard.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationBean implements Serializable {
    String       place    = "Hannover";
    List<String> stations = Arrays.asList("Isernhagener Str.", "Vahrenwalder Platz");

    public ConfigurationBean() {
    }

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

    public ConfigurationBean clone() {
        ConfigurationBean configurationBean = new ConfigurationBean();
        configurationBean.setPlace(place);
        configurationBean.setStations(new ArrayList<>(stations));

        return configurationBean;
    }

    public void addStation(String newStation) {
        this.stations.add(newStation);
    }

    public void removeStation(String selectedStation) {
        this.stations.remove(selectedStation);
    }
}