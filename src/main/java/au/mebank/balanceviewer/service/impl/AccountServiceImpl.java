package au.mebank.balanceviewer.service.impl;

import au.mebank.balanceviewer.dto.Transaction;
import au.mebank.balanceviewer.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public void findRelativeBalance(Map<String, Transaction> transactions, String accountId, Date startDate, Date endDate) {
        List<Transaction> reversed = getReverseTransactions(transactions, accountId);
        reversed.forEach(filteredTransaction -> {
            transactions.remove(filteredTransaction.getRelatedTransactionId());
            transactions.remove(filteredTransaction.getTransactionId());
        });

        Map<String, Transaction> validTransactions = transactions.entrySet().stream().filter(trans -> trans.getValue().getFromAccountId().equals(accountId)
                && (startDate.compareTo(trans.getValue().getCreatedAt()) <= 0
                && endDate.compareTo(trans.getValue().getCreatedAt()) >= 0)).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        LOG.info("Valid Transactions {}", validTransactions);
        Optional<BigDecimal> totalCredit = validTransactions.values().stream().map(vale -> {
            if (vale.getToAccountId().equals(accountId)) {
                return vale.getAmount();
            }
            return BigDecimal.ZERO;
        }).reduce(BigDecimal::add);

        Optional<BigDecimal> totalDebit = validTransactions.values().stream().map(vale -> {
            if (vale.getFromAccountId().equals(accountId)) {
                return vale.getAmount();
            }
            return BigDecimal.ZERO;
        }).reduce(BigDecimal::add);

        BigDecimal relativeBalance = (totalCredit.orElse(BigDecimal.ZERO)).subtract(totalDebit.orElse(BigDecimal.ZERO));
        LOG.info("Relative Balance is {}", relativeBalance);
        LOG.info("Total Transactions is {}", validTransactions.size());
    }

    public List<Transaction> getReverseTransactions(Map<String, Transaction> transactions, String accountId) {
        List<Transaction> filtered = new ArrayList<>();
        transactions.forEach((k, v) -> {
            if (StringUtils.isNotBlank(v.getRelatedTransactionId()) && v.getFromAccountId().equals(accountId)) {
                filtered.add(v);
            }
        });
        return filtered;
    }
}
