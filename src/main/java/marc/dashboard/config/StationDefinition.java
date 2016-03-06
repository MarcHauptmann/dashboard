package marc.dashboard.config;

import java.util.Arrays;
import java.util.function.Predicate;

public class StationDefinition {
    String stationName;
    Predicate<String> linePredicate;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Predicate<String> getLinePredicate() {
        return linePredicate;
    }

    public void setLinePredicate(Predicate<String> linePredicate) {
        this.linePredicate = linePredicate;
    }

    public static Predicate<String> allLinesBut(final String... lines) {
        return line -> !Arrays.asList(lines).contains(line);
    }

    public static Predicate<String> allLines() {
        return line -> true;
    }
}
