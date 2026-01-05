package service;

import dao.ExpenseDAO;
import dao.BudgetDAO;
import model.Expense;
import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Service layer for generating comprehensive expense reports
 * Uses Streams API extensively for data aggregation and processing
 */
public class ReportService {
    
    private ExpenseDAO expenseDAO;
    private BudgetDAO budgetDAO;
    
    public ReportService(ExpenseDAO expenseDAO, BudgetDAO budgetDAO) {
        this.expenseDAO = expenseDAO;
        this.budgetDAO = budgetDAO;
    }
    
    /**
     * Get total expenses grouped by category using Streams API
     */
    public Map<String, Double> getExpensesByCategory(int userId) throws Exception {
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        
        return expenses.stream()
            .collect(Collectors.groupingBy(
                e -> e.getCategory().getDisplayName(),
                Collectors.summingDouble(Expense::getAmount)
            ));
    }
    
    /**
     * Get monthly expense trend for last N months
     */
    public Map<String, Double> getMonthlyExpenseTrend(int userId, int months) 
            throws Exception {
        
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        
        return expenses.stream()
            .filter(e -> isWithinLastMonths(e.getDate(), months))
            .collect(Collectors.groupingBy(
                e -> formatYearMonth(e.getDate()),
                TreeMap::new,
                Collectors.summingDouble(Expense::getAmount)
            ));
    }
    
    /**
     * Get detailed budget analysis
     */
    public Map<String, Map<String, Object>> getBudgetAnalysis(int userId) 
            throws Exception {
        
        Map<String, Double> budgets = budgetDAO.getAllBudgets(userId);
        Map<String, Double> actuals = getExpensesByCategory(userId);
        
        return budgets.keySet().stream()
            .collect(Collectors.toMap(
                category -> category,
                category -> {
                    double budget = budgets.getOrDefault(category, 0.0);
                    double actual = actuals.getOrDefault(category, 0.0);
                    double remaining = budget - actual;
                    double utilization = budget > 0 ? (actual / budget) * 100 : 0;
                    
                    Map<String, Object> analysis = new HashMap<>();
                    analysis.put("budget", Math.round(budget * 100.0) / 100.0);
                    analysis.put("actual", Math.round(actual * 100.0) / 100.0);
                    analysis.put("remaining", Math.round(remaining * 100.0) / 100.0);
                    analysis.put("utilization", Math.round(utilization * 100.0) / 100.0);
                    analysis.put("exceeded", actual > budget);
                    
                    return analysis;
                }
            ));
    }
    
    /**
     * Get category-wise monthly breakdown
     */
    public Map<String, Map<String, Double>> getCategoryMonthlyBreakdown(
            int userId) throws Exception {
        
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        
        return expenses.stream()
            .collect(Collectors.groupingBy(
                e -> e.getCategory().getDisplayName(),
                Collectors.groupingBy(
                    e -> formatYearMonth(e.getDate()),
                    Collectors.summingDouble(Expense::getAmount)
                )
            ));
    }
    
    /**
     * Get top spending categories
     */
    public Map<String, Double> getTopCategories(int userId, int limit) 
            throws Exception {
        
        return getExpensesByCategory(userId).entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(limit)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
    
    /**
     * Get spending statistics
     */
    public Map<String, Object> getSpendingStatistics(int userId) 
            throws Exception {
        
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        
        DoubleSummaryStatistics stats = expenses.stream()
            .mapToDouble(Expense::getAmount)
            .summaryStatistics();
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalExpenses", Math.round(stats.getSum() * 100.0) / 100.0);
        result.put("averageExpense", Math.round(stats.getAverage() * 100.0) / 100.0);
        result.put("maxExpense", Math.round(stats.getMax() * 100.0) / 100.0);
        result.put("minExpense", Math.round(stats.getMin() * 100.0) / 100.0);
        result.put("count", stats.getCount());
        
        return result;
    }
    
    /**
     * Get daily average spending
     */
    public double getDailyAverageSpending(int userId) throws Exception {
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        
        double totalDays = expenses.stream()
            .map(Expense::getDate)
            .distinct()
            .count();
        
        double totalAmount = expenses.stream()
            .mapToDouble(Expense::getAmount)
            .sum();
        
        return totalDays > 0 ? Math.round((totalAmount / totalDays) * 100.0) / 100.0 : 0;
    }
    
    /**
     * Helper method to format date as YYYY-MM
     */
    private String formatYearMonth(LocalDate date) {
        return String.format("%d-%02d", date.getYear(), date.getMonthValue());
    }
    
    /**
     * Helper method to check if date is within last N months
     */
    private boolean isWithinLastMonths(LocalDate date, int months) {
        LocalDate startDate = LocalDate.now().minusMonths(months);
        return !date.isBefore(startDate);
    }
}
