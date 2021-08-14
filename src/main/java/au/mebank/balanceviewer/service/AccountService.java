package au.mebank.balanceviewer.service;

import au.mebank.balanceviewer.dto.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccountService {

    void findRelativeBalance(Map<String, Transaction> transaction, String accountId, Date startDate, Date endDate);

    List<Transaction> getReverseTransactions(Map<String, Transaction> transactions, String accountId);

}
