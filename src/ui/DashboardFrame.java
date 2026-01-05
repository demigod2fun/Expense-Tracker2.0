package ui;

import dao.*;
import model.*;
import service.ExportService;
import util.ValidationUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class DashboardFrame extends JFrame {
    private User currentUser;
    private ExpenseDAO expenseDAO;
    private CategoryDAO categoryDAO;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JLabel totalExpenseLabel;
    
    public DashboardFrame(User user) {
        this.currentUser = user;
        this.expenseDAO = new ExpenseDAO();
        this.categoryDAO = new CategoryDAO();
        initComponents();
        loadExpenses();
    }
    
    private void initComponents() {
        setTitle("Expense Tracker - Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel - Welcome and stats
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        totalExpenseLabel = new JLabel("Total Expenses: ₹0.00");
        totalExpenseLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(totalExpenseLabel, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center panel - Expense table
        String[] columns = {"ID", "Category", "Amount", "Date", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        expenseTable = new JTable(tableModel);
        expenseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Bottom panel - Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> addExpense());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Expense");
        editButton.addActionListener(e -> editExpense());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("Delete Expense");
        deleteButton.addActionListener(e -> deleteExpense());
        buttonPanel.add(deleteButton);
        
        JButton budgetButton = new JButton("Manage Budgets");
        budgetButton.addActionListener(e -> manageBudgets());
        buttonPanel.add(budgetButton);
        
        JButton exportButton = new JButton("Export to CSV");
        exportButton.addActionListener(e -> exportToCSV());
        buttonPanel.add(exportButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadExpenses());
        buttonPanel.add(refreshButton);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void loadExpenses() {
        try {
            List<Expense> expenses = expenseDAO.getAllExpenses(currentUser.getUserId());
            tableModel.setRowCount(0);
            
            double total = 0;
            for (Expense expense : expenses) {
                Object[] row = {
                    expense.getExpenseId(),
                    expense.getCategoryName(),
                    String.format("₹%.2f", expense.getAmount()),
                    expense.getExpenseDate(),
                    expense.getDescription()
                };
                tableModel.addRow(row);
                total += expense.getAmount();
            }
            
            totalExpenseLabel.setText(String.format("Total Expenses: ₹%.2f", total));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading expenses: " + ex.getMessage());
        }
    }
    
    private void addExpense() {
        try {
            List<Category> categories = categoryDAO.getAllCategories();
            JComboBox<Category> categoryBox = new JComboBox<>(categories.toArray(new Category[0]));
            JTextField amountField = new JTextField();
            JTextField dateField = new JTextField(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
            JTextField descField = new JTextField();
            
            Object[] message = {
                "Category:", categoryBox,
                "Amount:", amountField,
                "Date (YYYY-MM-DD):", dateField,
                "Description:", descField
            };
            
            int option = JOptionPane.showConfirmDialog(this, message, "Add Expense", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Category selectedCategory = (Category) categoryBox.getSelectedItem();
                String amountText = amountField.getText().trim();
            
            // Validate the amount
            if (!ValidationUtil.isValidAmount(amountText)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
                double amount = Double.parseDouble(amountText);
                Date date = Date.valueOf(dateField.getText());
                String description = descField.getText();
                
                Expense expense = new Expense(currentUser.getUserId(), selectedCategory.getCategoryId(),amount, date, description);
                if (expenseDAO.addExpense(expense)) {
                    JOptionPane.showMessageDialog(this, "Expense added successfully!");
                    loadExpenses();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to edit");
            return;
        }
        
        try {
            int expenseId = (Integer) tableModel.getValueAt(selectedRow, 0);
            List<Expense> expenses = expenseDAO.getAllExpenses(currentUser.getUserId());
            Expense expense = expenses.stream()
                .filter(e -> e.getExpenseId() == expenseId)
                .findFirst().orElse(null);
            
            if (expense == null) return;
            
            List<Category> categories = categoryDAO.getAllCategories();
            JComboBox<Category> categoryBox = new JComboBox<>(categories.toArray(new Category[0]));
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).getCategoryId() == expense.getCategoryId()) {
                    categoryBox.setSelectedIndex(i);
                    break;
                }
            }
            
            JTextField amountField = new JTextField(String.valueOf(expense.getAmount()));
            JTextField dateField = new JTextField(expense.getExpenseDate().toString());
            JTextField descField = new JTextField(expense.getDescription());
            
            Object[] message = {
                "Category:", categoryBox,
                "Amount:", amountField,
                "Date (YYYY-MM-DD):", dateField,
                "Description:", descField
            };
            
            int option = JOptionPane.showConfirmDialog(this, message, "Edit Expense", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                expense.setCategoryId(((Category) categoryBox.getSelectedItem()).getCategoryId());
                String amountText = amountField.getText().trim();
            
            // Validate the amount
            if (!ValidationUtil.isValidAmount(amountText)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
                expense.setAmount(Double.parseDouble(amountField.getText()));
                expense.setExpenseDate(Date.valueOf(dateField.getText()));
                expense.setDescription(descField.getText());
                
                if (expenseDAO.updateExpense(expense)) {
                    JOptionPane.showMessageDialog(this, "Expense updated successfully!");
                    loadExpenses();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this expense?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int expenseId = (Integer) tableModel.getValueAt(selectedRow, 0);
                if (expenseDAO.deleteExpense(expenseId)) {
                    JOptionPane.showMessageDialog(this, "Expense deleted successfully!");
                    loadExpenses();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting expense: " + ex.getMessage());
            }
        }
    }
    
    private void manageBudgets() {
        JOptionPane.showMessageDialog(this, "Budget management feature - View your budgets and set new ones");
    }
    
    private void exportToCSV() {
        try {
            List<Expense> expenses = expenseDAO.getAllExpenses(currentUser.getUserId());
            String filename = "expenses_" + System.currentTimeMillis() + ".csv";
            
            // Using multithreading for export
            ExportService exportService = new ExportService(expenses, filename);
            exportService.start();
            
            JOptionPane.showMessageDialog(this, "Export started. File will be saved as: " + filename);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error exporting: " + ex.getMessage());
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", 
            "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
