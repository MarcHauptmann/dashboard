package marc.dashboard.efa;

import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.List;

import static javax.xml.bind.JAXBContext.newInstance;

public class EfaFetcher {
    public static class StationQuery {
        @QueryParam ("locationServerActive")
        int locationServerActive = 1;

        @QueryParam ("outputFormat")
        public String outputFormat = "xml";

        @QueryParam ("type_sf")
        public String type_sf = "any";

        @QueryParam ("name_sf")
        public String name_sf;

        @QueryParam ("place_sf")
        public String place_sf;

        public StationQuery(String place_sf, String name_sf) {
            this.place_sf = place_sf;
            this.name_sf = name_sf;
        }
    }

    @XmlRootElement(name = "itdRequest")
    public static class Request {
        @XmlElementRef
        StopFinderRequest finder;
    }

    @XmlRootElement (name = "itdStopFinderRequest")
    public static class StopFinderRequest {
        @XmlElementRef
        Odv name;
    }

    @XmlRootElement (name = "itdOdv")
    public static class Odv {
        @XmlElementRef
        OdvName odvName;
    }

    @XmlRootElement (name = "itdOdvName")
    public static class OdvName {
        @XmlElement (name = "odvNameElem")
        List<OdvNameElement> names;
    }

    @XmlRootElement (name = "odvNameElem")
    public static class OdvNameElement {
        @XmlAttribute
        String locality;

        @XmlAttribute
        String objectName;
    }

    public interface EfaService {
        @GET
        @Path ("XSLT_STOPFINDER_REQUEST")
        @Produces ("text/xml;charset=utf-8")
        InputStream findStation(@Form StationQuery query);
    }

    public static void main(String[] args) throws IOException, JAXBException {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://mobil.efa.de/mobile3");

        EfaService efaService = target.proxy(EfaService.class);

        InputStream inputStream = efaService.findStation(new StationQuery("Hannover", "Hauptbahnhof"));

        JAXBContext context = newInstance(Request.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Request request = (Request) unmarshaller.unmarshal(inputStream);

        request.finder.name.odvName.names.stream().map(odvNameElement -> odvNameElement.objectName)
            .forEach(s -> System.out.println(s));
    }
}
