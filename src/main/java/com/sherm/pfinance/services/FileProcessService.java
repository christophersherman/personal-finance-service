package com.sherm.pfinance.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.sherm.pfinance.models.Accounts;
import com.sherm.pfinance.models.CurrencyType;
import com.sherm.pfinance.models.Transactions;
import com.sherm.pfinance.services.AccountsService;
import com.sherm.pfinance.services.UsersService;
import com.sherm.pfinance.utils.HttpUtils;
import com.sherm.pfinance.utils.TransactionHasher;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class FileProcessService {
  private TransactionsService transactionsService;
  @Autowired private AccountsService accountsService;

  public FileProcessService(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }

  public void processFile(Path filePath) {
    ArrayList<Transactions> transactionList = new ArrayList<Transactions>();
    try (CSVReader reader = new CSVReader(new FileReader(filePath.toFile()))) {
      List<String[]> records = reader.readAll();
      // skip the header
      if (!records.isEmpty()) {
        records.remove(0);
      }
      for (String[] record : records) {
        System.out.println("this is the record : " + Arrays.toString(record));
        Transactions transaction = new Transactions();
        transaction.setTransaction_id(
            TransactionHasher.GenerateUniqueHash(record));
        DateTimeFormatter inputFormatter =
            DateTimeFormatter.ofPattern("dd MMM yy", Locale.ENGLISH);
        transaction.setDate(LocalDate.parse(record[0], inputFormatter));
        transaction.setAmount(Double.parseDouble(record[1]));
        transaction.setDescription(record[5]);
        transaction.setCurrency(CurrencyType.AUD);
        // hard code the account id for now...
        transaction.setAccount(
            accountsService.findById("a94722c4-4df4-4b2f-9f36-c2b10c5d5a9a"));
        transactionList.add(transaction);
      }
    } catch (IOException | CsvException e) {
      e.printStackTrace();
    }
    for (Transactions t : transactionList) {
      if (transactionsService.getTransactionById(t.getTransaction_id()) !=
          null) {
        System.out.println("Transaction already exists");
        continue;
      }

      transactionsService.saveTransaction(t);
    }
  }
}
