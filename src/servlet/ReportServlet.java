package servlet;

import model.User;
import service.ReportService;
import util.ChartGenerator;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    
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
            String reportType = request.getParameter("type");
            ReportService reportService = new ReportService();
            
            if ("category".equals(reportType)) {
                Map<String, Double> data = reportService.getExpensesByCategory(user.getId());
                JFreeChart chart = ChartGenerator.generateExpensePieChart(data);
                response.setContentType("image/png");
                ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 600);
            } else if ("monthly".equals(reportType)) {
                Map<String, Double> data = reportService.getMonthlyExpenseTrend(user.getId(), 12);
                JFreeChart chart = ChartGenerator.generateMonthlyExpenseChart(data);
                response.setContentType("image/png");
                ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 600);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
