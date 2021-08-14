package au.mebank.balanceviewer.util;

import au.mebank.balanceviewer.dto.Transaction;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvReader {

    final Logger LOG = LoggerFactory.getLogger(CsvReader.class);

    public Map<String, Transaction> loadTransactions(String path) {

        try {
            CSVReader reader =
                    new CSVReaderBuilder(new FileReader(path)).
                            withSkipLines(1). // Skiping firstline as it is header
                            build();
            Map<String, Transaction> transactions = reader.readAll().stream().map(data -> {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(data[0]);
                transaction.setFromAccountId(data[1]);
                transaction.setToAccountId(data[2]);
                try {
                    transaction.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(data[3]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                transaction.setAmount(new BigDecimal(data[4]));
                transaction.setPaymentType(data[5]);
                transaction.setRelatedTransactionId(data[6]);
                return transaction;
            }).collect(Collectors.toMap(Transaction::getTransactionId, Function.identity()));
            LOG.info("Size of all transactions from csv {}", transactions);
            return transactions;
        } catch (FileNotFoundException e) {
            LOG.error("CSV file not found {} reason {}", path, e.getMessage());
        } catch (IOException e) {
            LOG.error("IO Exception while accessing the CSV file {} reason {}", path, e.getMessage());
        } catch (CsvException e) {
            LOG.error("Exception occurred while parsing CSV file {} reason {}", path, e.getMessage());
        } catch (Exception e) {
            LOG.error("Generic exception occurred {} reason {}", path, e.getMessage());
        }
        return null;
    }
}
