package com.sherm.pfinance.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.sherm.pfinance.models.Accounts;
import com.sherm.pfinance.models.CurrencyType;
import com.sherm.pfinance.services.AccountsService;
import com.sherm.pfinance.services.UsersService;
import com.sherm.pfinance.utils.HttpUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountsRefreshService {

  private AccountsService accountsService;

  public AccountsRefreshService(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  @Scheduled(cron = "20 15 * * *")
  //@Scheduled(cron = "0 * * * * ?")
  public void refreshAccounts() {
    String api_url = "https://api.up.com.au/api/v1/accounts";
    String bearer_token = System.getenv("UP_BANK_API");
    JsonNode jsonResponse =
        HttpUtils.sendGetRequestWithBearerToken(api_url, bearer_token);

    jsonResponse = jsonResponse.path("data");
    ArrayList<Accounts> accountList = new ArrayList<Accounts>();

    for (JsonNode node : jsonResponse) {
      String accountIdFromNode = node.get("id").asText();
      JsonNode attributes = node.get("attributes");

      Accounts optionalAccount = accountsService.findById(accountIdFromNode);

      if (optionalAccount != null) {
        System.out.println("account present");
        Double accountVal = attributes.get("balance").get("value").asDouble();
        System.out.println("account value : " + accountVal);
        optionalAccount.setBalance(accountVal);
        accountsService.save(optionalAccount);
      }
    }
  }
}
