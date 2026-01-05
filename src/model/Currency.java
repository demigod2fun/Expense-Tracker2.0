package model;

import java.time.LocalDateTime;

/**
 * Model class for Currency with exchange rates
 */
public class Currency {
    private int id;
    private String code;          // e.g., USD, EUR, INR
    private String name;          // e.g., US Dollar
    private double exchangeRate;  // Rate against base currency (INR)
    private LocalDateTime lastUpdated;
    
    public Currency() {}
    
    public Currency(String code, String name, double exchangeRate) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getExchangeRate() { return exchangeRate; }
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    
    @Override
    public String toString() {
        return code + " (" + name + ") - " + exchangeRate;
    }
}
