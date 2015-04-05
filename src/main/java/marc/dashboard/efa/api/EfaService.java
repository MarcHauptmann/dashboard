package marc.dashboard.efa.api;

import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.InputStream;

public interface EfaService {

    @GET
    @Path("XSLT_STOPFINDER_REQUEST")
    @Produces("text/xml;charset=utf-8")
    InputStream findStation(@Form StationQuery query);

    @GET
    @Path("XSLT_DM_REQUEST")
    @Produces("text/xml;charset=utf-8")
    InputStream getDepartures(@Form DepartureQuery query);

}
