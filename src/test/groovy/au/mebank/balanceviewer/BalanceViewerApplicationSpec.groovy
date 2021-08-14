package au.mebank.balanceviewer

import au.mebank.balanceviewer.service.AccountService
import spock.lang.Specification

class BalanceViewerApplicationSpec extends Specification {

    def app
    def accountService

    def setup() {
        accountService = Mock(AccountService)
        app = new BalanceViewerApplication(
                accountService: accountService
        )
    }

    def "it should invoke the run method to kick start balance viewer"() {
        given:
        when:
        app.run("ACCOUNTID", "20/10/2018 12:00:00", "20/10/2018 12:00:00", "./transactions.csv")
        then:
        1 * accountService.findRelativeBalance(_ as Map, _ as String, _ as Date, _ as Date)
        and:
        noExceptionThrown()
    }


}
