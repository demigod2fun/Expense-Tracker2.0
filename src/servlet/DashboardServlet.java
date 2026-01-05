package servlet;

import model.User;
import service.ExpenseService;
import service.ReportService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            User user = (User) session.getAttribute("user");
            ExpenseService expenseService = new ExpenseService();
            ReportService reportService = new ReportService();
            
            Map<String, Double> categoryData = reportService.getExpensesByCategory(user.getId());
            Map<String, Object> stats = reportService.getSpendingStatistics(user.getId());
            
            request.setAttribute("categoryData", categoryData);
            request.setAttribute("stats", stats);
            
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading dashboard");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
}

