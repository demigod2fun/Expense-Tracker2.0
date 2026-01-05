package servlet;

import util.CurrencyAPIClient;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/currency")
public class CurrencyServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String amount = request.getParameter("amount");
            String fromCurrency = request.getParameter("from");
            String toCurrency = request.getParameter("to");
            
            double convertedAmount = CurrencyAPIClient.convertCurrency(
                Double.parseDouble(amount),
                fromCurrency.toUpperCase(),
                toCurrency.toUpperCase()
            );
            
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("originalAmount", amount);
            result.put("fromCurrency", fromCurrency.toUpperCase());
            result.put("toCurrency", toCurrency.toUpperCase());
            result.put("convertedAmount", convertedAmount);
            
            response.getWriter().write(result.toString());
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("error", e.getMessage());
            response.getWriter().write(error.toString());
        }
    }
}
