package marc.dashboard.config;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ConfigurationView implements Serializable {
    private ConfigurationBean configurationBean;

    private String selectedStation;
    private String newStation;

    @Inject
    private DefaultConfiguration configuration;

    @Inject
    private MessagePublisher messagePublisher;

    public ConfigurationView() {
        this.configurationBean = new ConfigurationBean();
    }

    @PostConstruct
    public void initialize() {
        System.out.println("initialisiere");

        this.configurationBean = configuration.getConfigurationBean();

        clearSelectedStation();
        clearNewStation();
    }

    public void addNewStation(String newStation) {
        this.configurationBean.addStation(newStation);

    }

    public void removeStation(String selectedStation) {
        this.configurationBean.removeStation(selectedStation);

        clearSelectedStation();
    }

    private void clearSelectedStation() {
        this.selectedStation = null;
    }

    public void save() {
        this.configuration.setConfigurationBean(configurationBean.clone());

        messagePublisher.sendMessage("Daten wurden gespeichert");
    }

    public void clearNewStation() {
        this.newStation = null;
    }

    public void reset() {
        this.initialize();
    }

    public String getSelectedStation() {
        return selectedStation;
    }

    public void setSelectedStation(String selectedStation) {
        this.selectedStation = selectedStation;
    }

    public String getNewStation() {
        return newStation;
    }

    public void setNewStation(String newStation) {
        this.newStation = newStation;
    }

    public ConfigurationBean getConfigurationBean() {
        return configurationBean;
    }

    public void setConfigurationBean(ConfigurationBean configurationBean) {
        this.configurationBean = configurationBean;
    }

    public DefaultConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(DefaultConfiguration configuration) {
        this.configuration = configuration;
    }

    public MessagePublisher getMessagePublisher() {
        return messagePublisher;
    }

    public void setMessagePublisher(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }
}
