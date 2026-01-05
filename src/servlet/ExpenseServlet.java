package servlet;

import model.User;
import model.Expense;
import model.ExpenseCategory;
import service.ExpenseService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/expense")
public class ExpenseServlet extends HttpServlet {
    private ExpenseService expenseService = new ExpenseService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            User user = (User) session.getAttribute("user");
            String action = request.getParameter("action");
            
            if ("add".equals(action)) {
                LocalDate date = LocalDate.parse(request.getParameter("date"));
                ExpenseCategory category = ExpenseCategory.fromString(request.getParameter("category"));
                String description = request.getParameter("description");
                double amount = Double.parseDouble(request.getParameter("amount"));
                String currency = request.getParameter("currency");
                
                Expense expense = new Expense(user.getId(), date, category, description, amount, currency);
                expenseService.addExpense(expense);
                
                request.setAttribute("success", "Expense added successfully");
            }
            
            request.getRequestDispatcher("dashboard").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("addExpense.jsp").forward(request, response);
        }
    }
}
