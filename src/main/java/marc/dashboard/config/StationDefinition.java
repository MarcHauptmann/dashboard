package marc.dashboard.config;

import java.util.Arrays;
import java.util.function.Predicate;

public class StationDefinition {
    String stationName;
    Predicate<Integer> linePredicate;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Predicate<Integer> getLinePredicate() {
        return linePredicate;
    }

    public void setLinePredicate(Predicate<Integer> linePredicate) {
        this.linePredicate = linePredicate;
    }

    public static Predicate<Integer> allLinesBut(final Integer... lines) {
        return line -> !Arrays.asList(lines).contains(line);
    }

    public static Predicate<Integer> allLines() {
        return line -> true;
    }
}
