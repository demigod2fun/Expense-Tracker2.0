package service;

import dao.ExpenseDAO;
import model.Expense;
import model.ExpenseCategory;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseService {
    private ExpenseDAO expenseDAO = new ExpenseDAO();
    
    public void addExpense(Expense expense) throws Exception {
        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        expenseDAO.addExpense(expense);
    }
    
    public Expense getExpense(int expenseId) throws Exception {
        return expenseDAO.getExpenseById(expenseId);
    }
    
    public List<Expense> getAllExpenses(int userId) throws Exception {
        return expenseDAO.getAllExpenses(userId);
    }
    
    public List<Expense> getExpensesByCategory(int userId, ExpenseCategory category) throws Exception {
        return expenseDAO.getExpensesByCategory(userId, category);
    }
    
    public List<Expense> getExpensesByDateRange(int userId, LocalDate startDate, LocalDate endDate) throws Exception {
        return expenseDAO.getExpensesByDateRange(userId, startDate, endDate);
    }
    
    public void updateExpense(Expense expense) throws Exception {
        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        expenseDAO.updateExpense(expense);
    }
    
    public void deleteExpense(int expenseId) throws Exception {
        expenseDAO.deleteExpense(expenseId);
    }
    
    public double getTotalExpenses(int userId) throws Exception {
        return getAllExpenses(userId).stream()
            .mapToDouble(Expense::getAmount)
            .sum();
    }
    
    public Map<String, Double> getCategoryTotals(int userId) throws Exception {
        return getAllExpenses(userId).stream()
            .collect(Collectors.groupingBy(
                e -> e.getCategory().getDisplayName(),
                Collectors.summingDouble(Expense::getAmount)
            ));
    }
}
