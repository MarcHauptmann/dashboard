package marc.dashboard.weather.api;

import javax.xml.bind.annotation.XmlElement;

public class WeatherCondition {
    @XmlElement(name = "id")
    private long id;
    @XmlElement(name = "main")
    private String main;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "icon")
    private String icon;

    @Override
    public String toString() {
        return description;
    }

    public long getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconUrlString() {
        return String.format("http://openweathermap.org/img/w/%s.png", getIcon());
    }
}
