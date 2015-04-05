package marc.dashboard.efa;

import marc.dashboard.efa.api.*;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static javax.xml.bind.JAXBContext.newInstance;

public class EfaFetcher {

    private final EfaService efaService;

    public EfaFetcher() {
        efaService = createEfaService();
    }

    public List<Station> getStations(String city, String station) throws JAXBException {
        InputStream inputStream = efaService.findStation(new StationQuery(city, station));

        JAXBContext context = newInstance(StopResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StopResponse stopResponse = (StopResponse) unmarshaller.unmarshal(inputStream);

        Stream<Station> stationStream = stopResponse.getFinder().getName().getOdvName().getNames().stream()
                .filter(odvNameElement1 -> odvNameElement1.getAnyType() == Type.stop)
                .map(odvNameElement -> new Station(odvNameElement.getId(), odvNameElement.getLocality(), odvNameElement.getObjectName()));

        return stationStream.collect(toList());
    }

    public static void main(String[] args) throws IOException, JAXBException {
        EfaFetcher efaFetcher = new EfaFetcher();

        efaFetcher.getStations("Hannover", "Isernhagener").stream().forEach(System.out::println);

        efaFetcher.getStationDepartures(25000341).stream().forEach(System.out::println);
    }

    public List<Departure> getStationDepartures(int stationId) {
        InputStream inputStream = efaService.getDepartures(new DepartureQuery(stationId));

        JAXBContext context = null;

        try {
            context = newInstance(DepartureResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            DepartureResponse departureResponse = (DepartureResponse) unmarshaller.unmarshal(inputStream);

            return departureResponse.getDepartureInfo().getDepartures().stream()
                    .map(EfaFetcher::getStationDeparture)
                    .collect(toList());
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private static Departure getStationDeparture(marc.dashboard.efa.api.Departure dep) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, dep.getDateTime().getDate().getYear());
        calendar.set(Calendar.MONTH, dep.getDateTime().getDate().getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, dep.getDateTime().getDate().getDay());
        calendar.set(Calendar.HOUR_OF_DAY, dep.getDateTime().getTime().getHour());
        calendar.set(Calendar.MINUTE, dep.getDateTime().getTime().getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Departure(dep.getServingLine().getNumber(), dep.getServingLine().getDirection(), calendar.getTime(), dep.getCountdown(), dep.getStationName());
    }

    private EfaService createEfaService() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://mobil.efa.de/mobile3");

        return target.proxy(EfaService.class);
    }
}
