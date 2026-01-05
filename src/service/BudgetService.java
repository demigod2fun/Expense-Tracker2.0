package service;

import dao.BudgetDAO;
import dao.ExpenseDAO;
import model.Budget;
import model.Expense;
import java.util.*;

public class BudgetService {
    private BudgetDAO budgetDAO = new BudgetDAO();
    private ExpenseDAO expenseDAO = new ExpenseDAO();
    
    public void createBudget(Budget budget) throws Exception {
        if (budget.getBudgetAmount() <= 0) {
            throw new IllegalArgumentException("Budget amount must be positive");
        }
        budgetDAO.addBudget(budget);
    }
    
    public Budget getBudget(int budgetId) throws Exception {
        return budgetDAO.getBudgetById(budgetId);
    }
    
    public List<Budget> getUserBudgets(int userId) throws Exception {
        return budgetDAO.getBudgetsByUser(userId);
    }
    
    public void updateBudget(Budget budget) throws Exception {
        budgetDAO.updateBudget(budget);
    }
    
    public void deleteBudget(int budgetId) throws Exception {
        budgetDAO.deleteBudget(budgetId);
    }
    
    public Map<String, Double> getBudgetStatus(int userId) throws Exception {
        Map<String, Double> budgets = budgetDAO.getAllBudgets(userId);
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        
        Map<String, Double> status = new HashMap<>();
        for (String category : budgets.keySet()) {
            double budgetAmount = budgets.get(category);
            double spent = expenses.stream()
                .filter(e -> e.getCategory().getDisplayName().equals(category))
                .mapToDouble(Expense::getAmount)
                .sum();
            status.put(category, budgetAmount - spent);
        }
        return status;
    }
    
    public boolean isBudgetExceeded(int userId, String category) throws Exception {
        Map<String, Double> status = getBudgetStatus(userId);
        return status.containsKey(category) && status.get(category) < 0;
    }
}
