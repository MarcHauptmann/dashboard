package marc.dashboard.efa;

import org.apache.commons.lang3.builder.ToStringBuilder;

class Station {
    long id;
    String city;
    String name;

    public Station(long id, String city, String name) {
        this.id = id;
        this.city = city;
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
