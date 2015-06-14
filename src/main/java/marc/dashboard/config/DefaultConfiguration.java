package marc.dashboard.config;

import java.io.Serializable;
import java.util.List;

/**
 * Bean to hold the configuration
 */
//@ApplicationScoped
public class DefaultConfiguration implements Configuration, Serializable {
    private ConfigurationBean configurationBean = new ConfigurationBean();

    @Override
    public String getPlace() {
        return configurationBean.getPlace();
    }

    @Override
    public List<String> getStations() {
        return configurationBean.getStations();
    }

    public ConfigurationBean getConfigurationBean() {
        return configurationBean.clone();
    }

    public void setConfigurationBean(ConfigurationBean configurationBean) {
        this.configurationBean = configurationBean;
    }
}