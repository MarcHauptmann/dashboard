package marc.dashboard;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class RestUtil {

    public static final int CONNECTION_POOL_SIZE = 100;

    public static <T> T createService(String url, Class<T> serviceClass) {
        ResteasyClient resteasyClient = new ResteasyClientBuilder()
                .connectionPoolSize(CONNECTION_POOL_SIZE)
                .build();

        ResteasyWebTarget target = resteasyClient.target(url);

        return target.proxy(serviceClass);
    }
}
