package au.mebank.balanceviewer;

import au.mebank.balanceviewer.dto.Transaction;
import au.mebank.balanceviewer.service.AccountService;
import au.mebank.balanceviewer.util.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static au.mebank.balanceviewer.util.DateUtil.getDate;

@SpringBootApplication
public class BalanceViewerApplication implements CommandLineRunner {

    final Logger LOG = LoggerFactory.getLogger(BalanceViewerApplication.class);

    private static final String FILE_NAME = "transactions.csv";

    @Autowired
    public AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(BalanceViewerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting transaction parser application");
        Map<String, Transaction> transactions = new CsvReader().loadTransactions(args[3]);
        LOG.info("Post loading transactions '{}'", transactions.size());
        accountService.findRelativeBalance(transactions, args[0], getDate(args[1]), getDate(args[2]));
    }

}
