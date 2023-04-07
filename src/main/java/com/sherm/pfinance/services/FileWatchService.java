package com.sherm.pfinance.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.sherm.pfinance.models.Accounts;
import com.sherm.pfinance.models.CurrencyType;
import com.sherm.pfinance.services.AccountsService;
import com.sherm.pfinance.services.UsersService;
import com.sherm.pfinance.utils.HttpUtils;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileWatchService {
  @Autowired private FileProcessService fileProcessService;

  @Async
  public void watchDirectory(String directoryPath, String filePrefix) {
    Path path = Paths.get(directoryPath);
    try {
      WatchService watchService = FileSystems.getDefault().newWatchService();
      path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
      while (true) {
        try {
          WatchKey key = watchService.take();
          for (WatchEvent<?> event : key.pollEvents()) {
            Path changedPath = (Path)event.context();
            if (changedPath.toString().startsWith(filePrefix) &&
                changedPath.toString().endsWith(".csv")) {
              fileProcessService.processFile(path.resolve(changedPath));
            }
          }
          key.reset();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
