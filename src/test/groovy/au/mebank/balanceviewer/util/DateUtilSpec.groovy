package au.mebank.balanceviewer.util

import spock.lang.Specification

class DateUtilSpec extends Specification {

    def dateUtil

    def setup() {
        dateUtil = new DateUtil()
    }

    def "it should parse incoming string and return a valid date object "() {
        when:
        def result = dateUtil.getDate("20/10/2018 12:00:00")
        then:
        result != null
    }

    def "it should throw exception when incoming string isn't a valid date string "() {
        when:
        def result = dateUtil.getDate("20/10/2018 120:00")
        then:
        result == null
    }

}
