package com.sherm.pfinance.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.sherm.pfinance.utils.HttpUtils;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter; 
import com.sherm.pfinance.models.Transactions;
import org.springframework.stereotype.Service;
import com.sherm.pfinance.models.CurrencyType;
import java.util.ArrayList;
import java.util.List;
import com.sherm.pfinance.services.AccountsService;
import com.sherm.pfinance.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import java.net.URLEncoder; 
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
@Service
public class TransactionFetchService {
    @Autowired
    private AccountsService accountsService;
    
    @Autowired
    private UsersService usersService;

    private TransactionsService transactionsService;

    public TransactionFetchService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    //@Scheduled(cron = "0 0 0 * * ?")    
    @Scheduled(cron = "0 * * * * ?") 
    public void fetchAndSaveTransactions() {
        List<Transactions> lastDaysTransactions = fetchUpBankTransactions();
        for (Transactions t : lastDaysTransactions) {
            // revisit how to do this... getTransactionbyId has a .orElse null return. probs should build a exists function
            if(transactionsService.getTransactionById(t.getTransaction_id()) != null) {
                System.out.println("Transaction already exists"); 
                continue;
            }
            
            transactionsService.saveTransaction(t);                       
        }
    }

    public List<Transactions> fetchUpBankTransactions() {
        //this whole function is absolutely vomit-worthy please god fix this 
        //things to consider - how to assign user?
        System.out.println("test"); 
        String api_url = "https://api.up.com.au/api/v1/transactions?filter[since]=";
        api_url = api_url + getUpBankTimeISO();
        String bearer_token = System.getenv("UP_BANK_API"); 
    	JsonNode jsonResponse = HttpUtils.sendGetRequestWithBearerToken(api_url, bearer_token);       
        
        jsonResponse = jsonResponse.path("data");
        ArrayList<Transactions> transactionList = new ArrayList<Transactions>();
            for (JsonNode transactionNode : jsonResponse) {
                Transactions transaction = new Transactions();
                
                String transactionId = transactionNode.get("id").asText();
                String description = transactionNode.get("attributes").get("description").asText(); 
                JsonNode amount = transactionNode.get("attributes").get("amount");
                Double transactionAmount = amount.get("value").asDouble();
                JsonNode relationships = transactionNode.get("relationships"); 
                String accountId = relationships.get("account").get("data").get("id").asText();
                //get currencyType
                CurrencyType currType = CurrencyType.valueOf(amount.get("currencyCode").asText());
                
                //build transaction and put it in the list 
                transaction.setTransaction_id(transactionId);
                transaction.setDescription(description);
                transaction.setAmount(transactionAmount);
                System.out.println("finding account by id : "+ accountId); 
                transaction.setAccount(accountsService.findById(accountId));
                if(transaction.getAccount() == null) {
                    continue;
                }
                transaction.setCurrency(currType);
                transaction.setUser(usersService.findById(1L)); 
                transaction.setDate(LocalDateTime.parse(transactionNode.get("attributes").get("settledAt").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                transactionList.add(transaction);
            }
        return transactionList;
    }


    public String getUpBankTimeISO() {
        //This function returns the string value of 24hours prior to now, in ISO8601 format
        ZonedDateTime now = ZonedDateTime.now();         
        ZonedDateTime twentyFourHoursAgo = now.minusHours(24);         
        return URLEncoder.encode(twentyFourHoursAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")), StandardCharsets.UTF_8); 
    }

}
