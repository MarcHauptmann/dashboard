package marc.dashboard.config;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ConfigurationView implements Serializable {
    private List<String> stations;
    private String       selectedStation;
    private String       place;

    private ConfigurationBean configurationBean;
    private String            newStation;

    public ConfigurationView() {
    }

    @Inject
    public ConfigurationView(ConfigurationBean configurationBean) {
        this.configurationBean = configurationBean;

        this.place = configurationBean.getPlace();
        this.stations = new ArrayList<>(configurationBean.getStations());
    }

    public void addStation() {
        this.stations.add(newStation);
    }

    public void removeStation() {
        this.stations.remove(this.selectedStation);
    }

    public void save() {
        configurationBean.setPlace(place);
        configurationBean.setStations(stations);

        FacesContext facesContext = FacesContext.getCurrentInstance();

        facesContext.addMessage(null, new FacesMessage("Daten wurden gespeichert"));
    }

    public String getSelectedStation() {
        return selectedStation;
    }

    public void setSelectedStation(String selectedStation) {
        this.selectedStation = selectedStation;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setNewStation(String newStation) {
        this.newStation = newStation;
    }

    public String getNewStation() {
        return newStation;
    }
}
