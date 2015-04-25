package marc.dashboard.efa;

import marc.dashboard.efa.api.*;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static javax.xml.bind.JAXBContext.newInstance;

@ApplicationScoped
public class EfaFetcher {

    @Inject
    private EfaService efaService;

    public List<Station> getStations(String city, String station) {
        InputStream inputStream = efaService.findStation(new StationQuery(city, station));

        StopResponse stopResponse = readObjectFromStream(StopResponse.class, inputStream);

        Stream<Station> stationStream = stopResponse.getFinder().getName().getOdvName().getNames().stream()
                .filter(odvNameElement1 -> odvNameElement1.getAnyType() == Type.stop)
                .map(odvNameElement -> new Station(odvNameElement.getId(), odvNameElement.getLocality(),
                        odvNameElement.getObjectName()));

        return stationStream.collect(toList());
    }

    public List<Departure> getStationDepartures(long stationId) {
        InputStream inputStream = efaService.getDepartures(new DepartureQuery(stationId));

        DepartureResponse departureResponse = readObjectFromStream(DepartureResponse.class, inputStream);

        return departureResponse.getDepartureInfo().getDepartures().stream()
                .map((dep) -> getStationDeparture(dep))
                .collect(toList());
    }

    private <T> T readObjectFromStream(Class<T> stopResponseClass, InputStream inputStream) {
        try {
            JAXBContext context = newInstance(stopResponseClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private Departure getStationDeparture(marc.dashboard.efa.api.Departure dep) {
        Date plannedDepartureTime = dep.getDateTime().getDate();

        Date realtimeDate = dep.getRealtimeDateTime().getDate();

        return new Departure(dep.getServingLine().getNumber(), dep.getStationName(), dep.getServingLine().getDirection(),
                plannedDepartureTime, realtimeDate);
    }
}
