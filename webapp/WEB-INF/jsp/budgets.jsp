<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Budgets - Expense Tracker</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>🎯 Budget Management</h1>
            <a href="dashboard.jsp" class="btn-back">← Back</a>
        </header>
        
        <div class="budget-form">
            <h2>Set Budget</h2>
            <form action="../budget" method="POST">
                <div class="form-group">
                    <label for="category">Category:</label>
                    <select id="category" name="category" required>
                        <option value="">-- Select Category --</option>
                        <option value="Food & Dining">Food & Dining</option>
                        <option value="Transportation">Transportation</option>
                        <option value="Shopping">Shopping</option>
                        <option value="Entertainment">Entertainment</option>
                        <option value="Utilities">Utilities</option>
                        <option value="Healthcare">Healthcare</option>
                        <option value="Education">Education</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="amount">Budget Amount:</label>
                    <input type="number" id="amount" name="amount" step="0.01" required>
                </div>
                
                <button type="submit" class="btn-primary">Set Budget</button>
            </form>
        </div>
    </div>
</body>
</html>
