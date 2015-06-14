package marc.dashboard.weather.wunderground;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//@Scheduled(cronExpression = "0 0/15 * * * ?")
public class UpdateWeatherJob implements Job {
//    @Inject
//    @Primary
    WUndergroundWeatherService weatherService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        weatherService.updateWeatherData();
    }
}
