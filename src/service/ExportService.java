package service;

import dao.ExpenseDAO;
import model.Expense;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;

public class ExportService {
    private ExpenseDAO expenseDAO = new ExpenseDAO();
    
    public String exportToCSV(int userId, String filename) throws Exception {
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Date,Category,Description,Amount,Currency\\n");
            
            for (Expense expense : expenses) {
                writer.write(String.format("%s,%s,%s,%.2f,%s\\n",
                    expense.getDate(),
                    expense.getCategory().getDisplayName(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getCurrency()
                ));
            }
            
            return filename;
        }
    }
    
    public String exportToCSVByDateRange(int userId, LocalDate startDate, LocalDate endDate, 
                                         String filename) throws Exception {
        List<Expense> expenses = expenseDAO.getExpensesByDateRange(userId, startDate, endDate);
        
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Date,Category,Description,Amount,Currency\\n");
            
            for (Expense expense : expenses) {
                writer.write(String.format("%s,%s,%s,%.2f,%s\\n",
                    expense.getDate(),
                    expense.getCategory().getDisplayName(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getCurrency()
                ));
            }
            
            return filename;
        }
    }
}
