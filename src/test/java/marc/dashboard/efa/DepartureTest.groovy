package marc.dashboard.efa

import spock.lang.Specification

import java.text.DateFormat
import java.text.SimpleDateFormat

class DepartureTest extends Specification {
    def "calculating countdown"() {
        setup:
        def departure = new Departure(100, "some station", "some direction", departureTime, realTime)

        expect:
        departure.getCountdown(time) == expectedCountdown

        where:

        departureTime    | realTime         | time             | expectedCountdown
        time("11:00:00") | time("11:00:00") | time("11:00:00") | 0
        time("11:00:00") | time("10:59:00") | time("11:00:00") | 0
        time("11:00:00") | time("11:00:00") | time("11:00:31") | 0
        time("11:01:00") | time("11:01:00") | time("11:00:00") | 1
        time("11:01:00") | time("11:01:00") | time("11:00:31") | 0
        time("11:02:00") | time("11:02:00") | time("11:00:31") | 1
        time("11:02:00") | time("11:02:00") | time("11:00:29") | 2
        time("11:02:00") | time("11:01:00") | time("11:00:00") | 2
        time("12:00:00") | time("12:00:00") | time("11:00:00") | 60
    }

    def "calculating delay"() {
        setup:
        def departure = new Departure(100, "some station", "some direction", plannedDepartureTime, realDepartureTime)

        expect:
        departure.getDelay() == expectedDelay

        where:

        plannedDepartureTime | realDepartureTime | expectedDelay
        time("11:00:00")     | time("11:00:00")  | 0
        time("11:00:00")     | time("11:00:59")  | 0
        time("11:00:00")     | time("11:01:00")  | 1
        time("11:00:00")     | time("12:00:00")  | 60
    }

    def Date time(String str) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss")

        return dateFormat.parse(str)
    }
}
