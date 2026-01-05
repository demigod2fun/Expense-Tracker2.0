package dao;

import model.Expense;
import model.ExpenseCategory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    
    public void addExpense(Expense expense) throws SQLException {
        String query = "INSERT INTO expenses (user_id, date, category, description, amount, currency) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, expense.getUserId());
            stmt.setDate(2, java.sql.Date.valueOf(expense.getDate()));
            stmt.setString(3, expense.getCategory().getDisplayName());
            stmt.setString(4, expense.getDescription());
            stmt.setDouble(5, expense.getAmount());
            stmt.setString(6, expense.getCurrency());
            stmt.executeUpdate();
        }
    }
    
    public Expense getExpenseById(int id) throws SQLException {
        String query = "SELECT * FROM expenses WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToExpense(rs);
            }
        }
        return null;
    }
    
    public List<Expense> getAllExpenses(int userId) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE user_id = ? ORDER BY date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                expenses.add(mapResultSetToExpense(rs));
            }
        }
        return expenses;
    }
    
    public List<Expense> getExpensesByCategory(int userId, ExpenseCategory category) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE user_id = ? AND category = ? ORDER BY date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, category.getDisplayName());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                expenses.add(mapResultSetToExpense(rs));
            }
        }
        return expenses;
    }
    
    public List<Expense> getExpensesByDateRange(int userId, LocalDate startDate, LocalDate endDate) 
            throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE user_id = ? AND date BETWEEN ? AND ? ORDER BY date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(startDate));
            stmt.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                expenses.add(mapResultSetToExpense(rs));
            }
        }
        return expenses;
    }
    
    public void updateExpense(Expense expense) throws SQLException {
        String query = "UPDATE expenses SET date = ?, category = ?, description = ?, amount = ?, currency = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(expense.getDate()));
            stmt.setString(2, expense.getCategory().getDisplayName());
            stmt.setString(3, expense.getDescription());
            stmt.setDouble(4, expense.getAmount());
            stmt.setString(5, expense.getCurrency());
            stmt.setInt(6, expense.getId());
            stmt.executeUpdate();
        }
    }
    
    public void deleteExpense(int expenseId) throws SQLException {
        String query = "DELETE FROM expenses WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, expenseId);
            stmt.executeUpdate();
        }
    }
    
    private Expense mapResultSetToExpense(ResultSet rs) throws SQLException {
        Expense expense = new Expense();
        expense.setId(rs.getInt("id"));
        expense.setUserId(rs.getInt("user_id"));
        expense.setDate(rs.getDate("date").toLocalDate());
        expense.setCategory(ExpenseCategory.fromString(rs.getString("category")));
        expense.setDescription(rs.getString("description"));
        expense.setAmount(rs.getDouble("amount"));
        expense.setCurrency(rs.getString("currency"));
        expense.setCreatedAt(rs.getDate("created_at").toLocalDate());
        return expense;
    }
}
