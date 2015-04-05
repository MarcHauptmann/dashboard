package marc.dashboard.efa.api;

import javax.ws.rs.QueryParam;

public class DepartureQuery {
    @QueryParam("outputFormat")
    String outputFormat = "xml";

    @QueryParam("mode")
    String mode = "direct";

    @QueryParam("name_dm")
    long stationId;

    @QueryParam("type_dm")
    String type_dm = "stopID";

    public DepartureQuery(long stationId) {

        this.stationId = stationId;
    }
}
