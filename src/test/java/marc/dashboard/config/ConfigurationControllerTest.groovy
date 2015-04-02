package marc.dashboard.config

import spock.lang.Specification

class ConfigurationControllerTest extends Specification {
    def configBean = new DefaultConfiguration()
    def mock = Mock(MessagePublisher)
    def view = new ConfigurationView(configBean, mock)

    def "adding a new station"() {
        when:
        view.setNewStation("test station")
        view.addNewStation(view.newStation)

        then:
        view.getStations().contains("test station")
    }

    def "deleting a station"() {
        setup:
        view.setNewStation("test station")
        view.addNewStation(view.newStation)

        when:
        view.setSelectedStation("test station")
        view.removeSelectedStation(view.selectedStation)

        then:
        !view.getStations().contains("test station")
    }

    def "saving configuration"() {
        setup:
        view.setPlace("some place")
        view.setNewStation("some station")
        view.addNewStation(view.newStation)

        when:
        view.save()

        then:
        configBean.getStations() == ["some station"]
        configBean.getPlace() == "some place"
    }

}
