package marc.dashboard.efa;

import marc.dashboard.config.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
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

        List<Long> stationIds = configuration.getStations().stream()
                .map(name -> getFirstStationByName(efaFetcher, name).getId())
                .collect(toList());

        return stationIds.stream()
                .map(efaFetcher::getStationDepartures)
                .flatMap(list -> list.stream())
                .sorted((dep1, dep2) -> dep1.getTime().compareTo(dep2.getTime()))
                .collect(toList());
    }

    private Station getFirstStationByName(EfaFetcher efaFetcher, String name) {
        return efaFetcher.getStations(configuration.getPlace(), name).stream().findFirst().get();
    }
}
