package au.mebank.balanceviewer.util

import spock.lang.Specification

class CsvReaderSpec extends Specification {

    def reader

    def setup() {
        reader = new CsvReader()
    }

    def "it should load all transactions"() {
        when:
        def transactions = reader.loadTransactions("transactions.csv")
        then:
        transactions != null
        transactions.size() == 5
    }

    def "it should return null when csv file is not found "() {
        when:
        def transactions = reader.loadTransactions("invalidFile.csv")
        then:
        transactions == null
    }
}
