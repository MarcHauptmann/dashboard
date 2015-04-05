package marc.dashboard.efa.api;

import javax.ws.rs.QueryParam;

public class StationQuery {
    @QueryParam("locationServerActive")
    int locationServerActive = 1;

    @QueryParam("outputFormat")
    public String outputFormat = "xml";

    @QueryParam("type_sf")
    public String type_sf = "any";

    @QueryParam("name_sf")
    public String name_sf;

    @QueryParam("place_sf")
    public String place_sf;

    public StationQuery(String place_sf, String name_sf) {
        this.place_sf = place_sf;
        this.name_sf = name_sf;
    }
}
