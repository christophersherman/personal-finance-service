package com.sherm.pfinance.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sherm.pfinance.models.TransactionType;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transactions {

  /*
  transaction_id (PK)
  user_id (FK)
  account_id (FK)
  category_id (FK)
  date
  description
  amount
  currency
   */

  @Id @Column(length = 100) String transaction_id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Users user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Categories category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Accounts account;

  private LocalDate date;

  private String description;
  private Double amount;

  @Enumerated(EnumType.STRING) private CurrencyType currency;

  public String getTransaction_id() { return this.transaction_id; }

  public void setTransaction_id(String transaction_id) {
    this.transaction_id = transaction_id;
  }

  public Users getUser() { return user; }

  public void setUser(Users user) { this.user = user; }

  public LocalDate getDate() { return this.date; }

  public void setDate(LocalDate date) { this.date = date; }

  public String getDescription() { return this.description; }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getAmount() { return this.amount; }

  public void setAmount(Double amount) { this.amount = amount; }

  public CurrencyType getCurrency() { return this.currency; }

  public void setCurrency(CurrencyType currency) { this.currency = currency; }

  public Categories getCategory() { return this.category; }

  public void setCategory(Categories category) { this.category = category; }

  public Accounts getAccount() { return this.account; }

  public void setAccount(Accounts account) { this.account = account; }
}
