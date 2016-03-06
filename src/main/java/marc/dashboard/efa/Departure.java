package marc.dashboard.efa;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

import static java.lang.Math.*;

public class Departure {
    String line;
    String direction;
    Date time;
    Date realTime;
    String station;

    public Departure(String line, String station, String direction, Date time, Date realTime) {
        this.line = line;
        this.direction = direction;
        this.time = time;
        this.station = station;
        this.realTime = realTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public String getDirection() {
        return direction;
    }

    public Date getTime() {
        return time;
    }

    public int getCountdown() {
        return getCountdown(new Date());
    }

    public int getCountdown(Date date) {
        Date realTime = this.realTime;

        return max(max(getDifferenceInMinutes(realTime, date), 0),
                max(getDifferenceInMinutes(time, date), 0));
    }

    public String getIcon() {
        if (line.matches("\\d+")) {
            int lineNumber = Integer.parseInt(line);

            if (lineNumber < 100) {
                return "u_bahn.png";
            } else {
                return "bus.png";
            }
        } else if (line.matches("S\\d+")) {
            return "s_bahn.png";
        } else {
            return null;
        }
    }

    public String getStation() {
        return station;
    }

    public int getDelay() {
        long delayMs = realTime.getTime() - time.getTime();

        return (int) Math.floor(delayMs / 60000.);
    }

    private int getDifferenceInMinutes(Date realTime, Date time) {
        long delayMs = realTime.getTime() - time.getTime();

        return (int) Math.round(delayMs / 60000.);
    }

    public Date getRealTime() {
        return realTime;
    }
}
