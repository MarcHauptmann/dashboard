package marc.dashboard.config

import spock.lang.Specification

class StationDefinitionTest extends Specification {
    def "filtering lines"() {
        expect:
        StationDefinition.allLinesBut("100", "200").test("100") == false
        StationDefinition.allLinesBut("100", "200").test("1") == true
    }
}
