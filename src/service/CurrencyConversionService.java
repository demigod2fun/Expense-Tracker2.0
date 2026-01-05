package service;

import dao.ExpenseDAO;
import model.Expense;
import util.CurrencyAPIClient;
import java.util.*;

public class CurrencyConversionService {
    private ExpenseDAO expenseDAO = new ExpenseDAO();
    
    public double convertExpenseToCurrency(Expense expense, String targetCurrency) throws Exception {
        return CurrencyAPIClient.convertCurrency(
            expense.getAmount(),
            expense.getCurrency(),
            targetCurrency
        );
    }
    
    public Map<String, Double> getTotalExpensesByCategory(int userId, String targetCurrency) throws Exception {
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        Map<String, Double> categoryTotals = new HashMap<>();
        
        for (Expense expense : expenses) {
            try {
                double convertedAmount = convertExpenseToCurrency(expense, targetCurrency);
                String category = expense.getCategory().getDisplayName();
                categoryTotals.merge(category, convertedAmount, Double::sum);
            } catch (Exception e) {
                categoryTotals.merge(expense.getCategory().getDisplayName(), 
                    expense.getAmount(), Double::sum);
            }
        }
        
        return categoryTotals;
    }
    
    public double getTotalExpensesInCurrency(int userId, String targetCurrency) throws Exception {
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        double total = 0;
        
        for (Expense expense : expenses) {
            try {
                total += convertExpenseToCurrency(expense, targetCurrency);
            } catch (Exception e) {
                total += expense.getAmount();
            }
        }
        
        return total;
    }
}
