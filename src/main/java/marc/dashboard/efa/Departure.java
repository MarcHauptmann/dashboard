package marc.dashboard.efa;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class Departure {
    int line;
    String direction;
    Date time;
    int countdown;
    String station;
    private int delay;

    public Departure(int line, String direction, Date time, int countdown, String station, int delay) {
        this.line = line;
        this.direction = direction;
        this.time = time;
        this.countdown = countdown;
        this.station = station;
        this.delay = delay;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setDelay(int delay) {
        this.delay = delay;
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
        if (line <= 100)
            return "u_bahn.gif";
        else
            return "bus.gif";
    }

    public String getStation() {
        return station;
    }

    public int getDelay() {
        return delay;
    }
}
