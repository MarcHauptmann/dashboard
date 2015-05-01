package marc.dashboard.weather.wunderground;

import marc.dashboard.cdi.Primary;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

@Scheduled(cronExpression = "0 0/15 * * * ?")
public class UpdateWeatherJob implements Job {
    @Inject
    @Primary
    WUndergroundWeatherService weatherService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        weatherService.updateWeatherData();
    }
}
