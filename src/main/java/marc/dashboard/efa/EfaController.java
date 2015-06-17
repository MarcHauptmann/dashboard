package marc.dashboard.efa;

import marc.dashboard.config.Configuration;
import marc.dashboard.config.StationDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/efa")
public class EfaController {
    @Autowired
    private Configuration configuration;

    @Autowired
    private EfaFetcher efaFetcher;

    @RequestMapping(value = "departures", method = RequestMethod.GET)
    @ResponseBody
    public List<Departure> getDepartures() {
        return configuration.getStationDefinitions().stream()
                .map(this::toDepartures)
                .flatMap(Collection::stream)
                .sorted(byDepartureTime())
                .collect(toList());
    }

    private List<Departure> toDepartures(StationDefinition def) {
        Station station = getFirstStationByName(def.getStationName());

        return efaFetcher.getStationDepartures(station.getId()).stream()
                .filter(departure -> def.getLinePredicate().test(departure.getLine()))
                .collect(toList());
    }

    private Comparator<Departure> byDepartureTime() {
        return (dep1, dep2) -> dep1.getTime().compareTo(dep2.getTime());
    }

    private Station getFirstStationByName(String name) {
        return efaFetcher.getStations(configuration.getPlace(), name).stream().findFirst().get();
    }
}
