package dao;

import model.Currency;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
    
    public void saveCurrency(Currency currency) throws SQLException {
        String query = "INSERT INTO currency_rates (currency_code, currency_name, exchange_rate) " +
                      "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE exchange_rate = ?, last_updated = NOW()";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, currency.getCode());
            stmt.setString(2, currency.getName());
            stmt.setDouble(3, currency.getExchangeRate());
            stmt.setDouble(4, currency.getExchangeRate());
            stmt.executeUpdate();
        }
    }
    
    public Currency getCurrencyByCode(String code) throws SQLException {
        String query = "SELECT * FROM currency_rates WHERE currency_code = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code.toUpperCase());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCurrency(rs);
            }
        }
        return null;
    }
    
    public List<Currency> getAllCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT * FROM currency_rates";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                currencies.add(mapResultSetToCurrency(rs));
            }
        }
        return currencies;
    }
    
    private Currency mapResultSetToCurrency(ResultSet rs) throws SQLException {
        Currency currency = new Currency();
        currency.setId(rs.getInt("id"));
        currency.setCode(rs.getString("currency_code"));
        currency.setName(rs.getString("currency_name"));
        currency.setExchangeRate(rs.getDouble("exchange_rate"));
        return currency;
    }
}
