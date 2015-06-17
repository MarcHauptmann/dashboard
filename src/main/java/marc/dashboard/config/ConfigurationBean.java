package marc.dashboard.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationBean implements Configuration, Serializable {
    private String place;
    private List<StationDefinition> stationDefinitions;

    @Override
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public List<StationDefinition> getStationDefinitions() {
        return stationDefinitions;
    }

    public void setStationDefinitions(List<StationDefinition> stations) {
        this.stationDefinitions = stations;
    }

    public ConfigurationBean clone() {
        ConfigurationBean configurationBean = new ConfigurationBean();
        configurationBean.setPlace(place);
        configurationBean.setStationDefinitions(new ArrayList<>(stationDefinitions));

        return configurationBean;
    }

    public void addStationDefinition(StationDefinition newStation) {
        this.stationDefinitions.add(newStation);
    }

    public void removeStation(String selectedStation) {
        this.stationDefinitions.remove(selectedStation);
    }
}