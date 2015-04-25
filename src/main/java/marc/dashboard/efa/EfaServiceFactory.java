package marc.dashboard.efa;

import marc.dashboard.efa.api.EfaService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.inject.Produces;

public class EfaServiceFactory {
    @Produces
    public static EfaService createEfaService() {
        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();

        ResteasyWebTarget target = resteasyClient.target("http://mobil.efa.de/mobile3");

        return target.proxy(EfaService.class);
    }
}
