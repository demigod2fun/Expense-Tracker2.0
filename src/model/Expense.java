package model;

import java.time.LocalDate;

/**
 * Expense model class representing a user expense
 */
public class Expense {
    private int id;
    private int userId;
    private LocalDate date;
    private ExpenseCategory category;
    private String description;
    private double amount;
    private String currency;
    private LocalDate createdAt;
    
    public Expense() {}
    
    public Expense(int userId, LocalDate date, ExpenseCategory category, 
                   String description, double amount, String currency) {
        this.userId = userId;
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.createdAt = LocalDate.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public ExpenseCategory getCategory() { return category; }
    public void setCategory(ExpenseCategory category) { this.category = category; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}