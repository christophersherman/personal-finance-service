package com.sherm.pfinance.models;
import java.time.LocalDate;
import jakarta.persistence.*;
import com.sherm.pfinance.models.TransactionType;

@Entity
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transaction_id;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories category;

    private LocalDate date;

    private String description;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency; 


    public Long getTransaction_id() {
        return this.transaction_id;
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CurrencyType getCurrency() {
        return this.currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }


    public Categories getCategory() {
        return this.category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }
}