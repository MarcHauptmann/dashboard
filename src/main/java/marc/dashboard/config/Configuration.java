package marc.dashboard.config;

import java.util.List;

public interface Configuration {
    String getPlace();

    List<StationDefinition> getStationDefinitions();
}
