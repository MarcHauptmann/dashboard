package marc.dashboard.efa;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class Departure {
    int line;
    String direction;
    Date time;
    int countdown;
    String station;

    public Departure(int line, String direction, Date time, int countdown, String station) {
        this.line = line;
        this.direction = direction;
        this.time = time;
        this.countdown = countdown;
        this.station = station;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public int getLine() {
        return line;
    }

    public String getDirection() {
        return direction;
    }

    public Date getTime() {
        return time;
    }

    public int getCountdown() {
        return countdown;
    }

    public String getIcon() {
        if(line <= 100)
            return "u_bahn.gif";
        else
            return "bus.gif";
    }

    public String getStation() {
        return station;
    }
}
