package marc.dashboard.efa;

import marc.dashboard.config.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Named
public class EfaView {
    @Inject
    private Configuration configuration;

    public List<String> getStations() {
        return configuration.getStations();
    }

    public List<Departure> getDepartures() {
        EfaFetcher efaFetcher = new EfaFetcher();


        List<Departure> stationDepartures = efaFetcher.getStationDepartures(25000341);
        stationDepartures.addAll(efaFetcher.getStationDepartures(25002091));

        return stationDepartures.stream()
                .sorted((dep1, dep2) -> dep1.getTime().compareTo(dep2.getTime())).collect(toList());
    }
}
