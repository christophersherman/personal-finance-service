package com.sherm.pfinance;

import com.sherm.pfinance.services.FileWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("com.sherm.pfinance.models")
@EnableScheduling
@EnableAsync
public class PfinanceApplication {
  @Autowired private FileWatchService fileWatchService;
  public static void main(String[] args) {
    SpringApplication.run(PfinanceApplication.class, args);
  }
  @Bean
  public CommandLineRunner startFileWatchService() {
    return args -> {
      String directoryPath = "/home/sherm/Documents/transactions/";
      String filePrefix = "Transactions";
      fileWatchService.watchDirectory(directoryPath, filePrefix);
    };
  }
}
