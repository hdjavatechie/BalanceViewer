package au.mebank.balanceviewer.service

import au.mebank.balanceviewer.dto.Transaction
import au.mebank.balanceviewer.service.impl.AccountServiceImpl
import au.mebank.balanceviewer.util.DateUtil
import spock.lang.Specification

import java.math.MathContext

class AccountServiceSpec extends Specification {

    def accService

    def setup(){
        accService = new AccountServiceImpl()
    }

    def getTransaction(transactionId, fromAccountId, toAccountId, createdAt, amount, paymentType, relatedTransactionId) {
        def value = BigDecimal.valueOf(amount)
//        def value = new BigDecimal(amount, MathContext.DECIMAL64)
        def transaction = new Transaction()
        transaction.setTransactionId(transactionId)
        transaction.setFromAccountId(fromAccountId)
        transaction.setToAccountId(toAccountId)
        transaction.setCreatedAt(DateUtil.getDate(createdAt))
        transaction.setAmount(value)
        transaction.setPaymentType(paymentType)
        transaction.setRelatedTransactionId(relatedTransactionId)
        return transaction
    }

    def "it should find relative balance "() {
        given:
        def transaction1 = getTransaction("TX10001", "ACC334455", "ACC778899", "20/10/2018 12:47:55", 25.00f, "PAYMENT", "")
        def transaction2 = getTransaction("TX10002", "ACC334455", "ACC998877", "20/10/2018 17:33:43", 10.50f, "PAYMENT", "")
        def transaction3 = getTransaction("TX10003", "ACC998877", "ACC778899", "20/10/2018 18:00:00", 5.00f, "PAYMENT", "")
        def transaction4 = getTransaction("TX10004", "ACC334455", "ACC998877", "20/10/2018 19:45:00", 10.50f, "REVERSAL", "TX10002")
        def transaction5 = getTransaction("TX10005", "ACC334455", "ACC778899", "21/10/2018 09:30:00", 7.25f, "PAYMENT", "")

        def transactions = new HashMap<String, Transaction>()
        transactions.put("TX10001", transaction1)
        transactions.put("TX10002", transaction2)
        transactions.put("TX10003", transaction3)
        transactions.put("TX10004", transaction4)
        transactions.put("TX10005", transaction5)
        when:
        accService.findRelativeBalance(transactions, "ACC334455", DateUtil.getDate("20/10/2018 12:00:00"), DateUtil.getDate("20/10/2018 19:00:00"))
        then:
        noExceptionThrown()
    }
}
