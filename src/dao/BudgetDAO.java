package dao;

import model.Budget;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetDAO {
    
    public void addBudget(Budget budget) throws SQLException {
        String query = "INSERT INTO budgets (user_id, category, budget_amount, month, year) " +
                      "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, budget.getUserId());
            stmt.setString(2, budget.getCategory());
            stmt.setDouble(3, budget.getBudgetAmount());
            stmt.setInt(4, budget.getMonth());
            stmt.setInt(5, budget.getYear());
            stmt.executeUpdate();
        }
    }
    
    public Budget getBudgetById(int id) throws SQLException {
        String query = "SELECT * FROM budgets WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBudget(rs);
            }
        }
        return null;
    }
    
    public List<Budget> getBudgetsByUser(int userId) throws SQLException {
        List<Budget> budgets = new ArrayList<>();
        String query = "SELECT * FROM budgets WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                budgets.add(mapResultSetToBudget(rs));
            }
        }
        return budgets;
    }
    
    public Map<String, Double> getAllBudgets(int userId) throws SQLException {
        Map<String, Double> budgets = new HashMap<>();
        String query = "SELECT category, budget_amount FROM budgets WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                budgets.put(rs.getString("category"), rs.getDouble("budget_amount"));
            }
        }
        return budgets;
    }
    
    public void updateBudget(Budget budget) throws SQLException {
        String query = "UPDATE budgets SET budget_amount = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, budget.getBudgetAmount());
            stmt.setInt(2, budget.getId());
            stmt.executeUpdate();
        }
    }
    
    public void deleteBudget(int budgetId) throws SQLException {
        String query = "DELETE FROM budgets WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, budgetId);
            stmt.executeUpdate();
        }
    }
    
    private Budget mapResultSetToBudget(ResultSet rs) throws SQLException {
        Budget budget = new Budget();
        budget.setId(rs.getInt("id"));
        budget.setUserId(rs.getInt("user_id"));
        budget.setCategory(rs.getString("category"));
        budget.setBudgetAmount(rs.getDouble("budget_amount"));
        budget.setMonth(rs.getInt("month"));
        budget.setYear(rs.getInt("year"));
        budget.setCreatedAt(rs.getDate("created_at").toLocalDate());
        return budget;
    }
}
