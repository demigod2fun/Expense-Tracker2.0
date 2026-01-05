package util;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import java.util.*;

/**
 * Utility class for generating various charts using JFreeChart
 */
public class ChartGenerator {
    
    /**
     * Generate 3D Pie Chart for expense distribution by category
     */
    public static JFreeChart generateExpensePieChart(Map<String, Double> categoryExpenses) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        
        categoryExpenses.forEach((category, amount) -> dataset.setValue(category, amount));
        
        JFreeChart chart = ChartFactory.createPieChart3D(
            "Expense Distribution by Category",
            dataset,
            true,
            true,
            false
        );
        
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setDepthFactor(0.10);
        plot.setStartAngle(45);
        
        return chart;
    }
    
    /**
     * Generate Bar Chart for monthly expense trend
     */
    public static JFreeChart generateMonthlyExpenseChart(Map<String, Double> monthlyData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        monthlyData.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> dataset.addValue(entry.getValue(), "Expenses", entry.getKey()));
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Monthly Expense Trend",
            "Month",
            "Amount",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        BarPlot plot = (BarPlot) chart.getPlot();
        plot.setRangeGridlinesVisible(true);
        
        return chart;
    }
    
    /**
     * Generate Line Chart for Budget vs Actual
     */
    public static JFreeChart generateBudgetVsActualChart(
            Map<String, Double> budgetData,
            Map<String, Double> actualData) {
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Set<String> allCategories = new HashSet<>();
        allCategories.addAll(budgetData.keySet());
        allCategories.addAll(actualData.keySet());
        
        for (String category : allCategories) {
            dataset.addValue(budgetData.getOrDefault(category, 0.0), "Budgeted", category);
            dataset.addValue(actualData.getOrDefault(category, 0.0), "Actual", category);
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
            "Budget vs Actual Spending",
            "Category",
            "Amount",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(true);
        
        return chart;
    }
}