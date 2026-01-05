package model;

import java.time.LocalDate;

/**
 * Budget model class for tracking budget limits
 */
public class Budget {
    private int id;
    private int userId;
    private String category;
    private double budgetAmount;
    private int month;
    private int year;
    private LocalDate createdAt;
    
    public Budget() {}
    
    public Budget(int userId, String category, double budgetAmount, int month, int year) {
        this.userId = userId;
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.month = month;
        this.year = year;
        this.createdAt = LocalDate.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public double getBudgetAmount() { return budgetAmount; }
    public void setBudgetAmount(double budgetAmount) { this.budgetAmount = budgetAmount; }
    
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", userId=" + userId +
                ", category='" + category + '\'' +
                ", budgetAmount=" + budgetAmount +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}