package marc.dashboard.efa;

import marc.dashboard.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/efa")
public class EfaController {
    @Autowired
    private Configuration configuration;

    @Autowired
    EfaFetcher efaFetcher;

    public List<String> getStations() {
        return configuration.getStations();
    }

    @RequestMapping(value = "departures", method = RequestMethod.GET)
    @ResponseBody
    public List<Departure> getDepartures() {
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
