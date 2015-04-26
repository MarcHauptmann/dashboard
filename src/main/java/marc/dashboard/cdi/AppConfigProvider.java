package marc.dashboard.cdi;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.util.Properties;

public class AppConfigProvider {
    Properties applicationProperties;

    @PostConstruct
    public void loadProperties() throws IOException {
        applicationProperties = new Properties();
        applicationProperties.load(getClass().getResourceAsStream("/application.properties"));
    }

    @Produces
    @AppConfig(key = "")
    public String getConfigValue(InjectionPoint injectionPoint) {
        Annotated annotated = injectionPoint.getAnnotated();

        AppConfig annotation = annotated.getAnnotation(AppConfig.class);

        if (annotation != null) {
            String key = annotation.key();

            return applicationProperties.getProperty(key);
        } else {
            throw new IllegalStateException("AppConfig annotation not found");
        }
    }
}
