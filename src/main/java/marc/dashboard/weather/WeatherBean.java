package marc.dashboard.weather;

import marc.dashboard.cdi.Primary;
import org.primefaces.model.chart.*;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static java.util.stream.Collectors.toMap;

@Named
@RequestScoped
public class WeatherBean {

    @Inject
    @Primary
    private WeatherService weatherService;

    public double getTemperatureInCelsius() {
        return weatherService.getCurrentTemperature();
    }

    public double getPressure() {
        return weatherService.getCurrentPressure();
    }

    public double getHumidity() {
        return weatherService.getCurrentHumidity();
    }

    public double getWindSpeed() {
        return weatherService.getCurrentWindSpeed();
    }

    public double getWindDirection() {
        return weatherService.getCurrentWindDirection();
    }

    public String getConditionString() {
        return weatherService.getCurrentConditionString();
    }

    public String getIcon() {
        return weatherService.getCurrentIcon();
    }

    public CartesianChartModel getForecastModel() {
        LineChartModel forecastModel = new LineChartModel();
        forecastModel.addSeries(createRainSeries());
        forecastModel.addSeries(createTemperatureSeries());

        forecastModel.getAxes().put(AxisType.X, createDateAxis());
        forecastModel.getAxes().put(AxisType.Y, new LinearAxis("Temperatur/°C"));
        forecastModel.getAxes().put(AxisType.Y2, new LinearAxis("Niederschlag/mm"));
        forecastModel.setShowDatatip(false);
        forecastModel.setMouseoverHighlight(false);
        forecastModel.setShowPointLabels(false);

        Axis y2Axis = forecastModel.getAxis(AxisType.Y2);
        y2Axis.setMin(0);

        return forecastModel;
    }

    private DateAxis createDateAxis() {
        DateAxis axis = new DateAxis();
        axis.setTickAngle(-50);
        axis.setTickFormat("%H:%M");
        return axis;
    }

    private ChartSeries createRainSeries() {
        LineChartSeries rainSeries = new LineChartSeries();
        rainSeries.setLabel("Niederschlag");

        Map<Object, Number> rainData = weatherService.getRainForecast();

        rainSeries.setData(rainData);
        rainSeries.setXaxis(AxisType.X);
        rainSeries.setYaxis(AxisType.Y2);

        return rainSeries;
    }

    private ChartSeries createTemperatureSeries() {
        LineChartSeries temperatureSeries = new LineChartSeries();
        temperatureSeries.setLabel("Temperatur");

        Map<Object, Number> temperatures = weatherService.getTemparatureForecast();

        temperatureSeries.setData(temperatures);
        temperatureSeries.setXaxis(AxisType.X);
        temperatureSeries.setYaxis(AxisType.Y);

        return temperatureSeries;
    }
}
