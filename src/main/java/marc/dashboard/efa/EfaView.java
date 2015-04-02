package marc.dashboard.efa;

import marc.dashboard.config.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class EfaView {
    @Inject
    private Configuration configuration;

    public List<String> getStations() {
        return configuration.getStations();
    }
}
