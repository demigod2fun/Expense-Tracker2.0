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
    <title>Add Expense - Expense Tracker</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>➕ Add Expense</h1>
            <a href="dashboard.jsp" class="btn-back">← Back</a>
        </header>
        
        <form action="../expense" method="POST" class="expense-form">
            <input type="hidden" name="action" value="add">
            
            <div class="form-group">
                <label for="date">Date:</label>
                <input type="date" id="date" name="date" required>
            </div>
            
            <div class="form-group">
                <label for="category">Category:</label>
                <select id="category" name="category" required>
                    <option value="">-- Select Category --</option>
                    <option value="Food & Dining">🍔 Food & Dining</option>
                    <option value="Transportation">🚗 Transportation</option>
                    <option value="Shopping">🛍️ Shopping</option>
                    <option value="Entertainment">🎬 Entertainment</option>
                    <option value="Utilities">💡 Utilities</option>
                    <option value="Healthcare">🏥 Healthcare</option>
                    <option value="Education">📚 Education</option>
                    <option value="Subscriptions">📱 Subscriptions</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="description">Description:</label>
                <input type="text" id="description" name="description" required>
            </div>
            
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="number" id="amount" name="amount" step="0.01" required>
            </div>
            
            <div class="form-group">
                <label for="currency">Currency:</label>
                <select id="currency" name="currency">
                    <option value="INR">₹ INR</option>
                    <option value="USD">$ USD</option>
                    <option value="EUR">€ EUR</option>
                    <option value="GBP">£ GBP</option>
                </select>
            </div>
            
            <button type="submit" class="btn-primary">Add Expense</button>
        </form>
    </div>
    
    <script>
        document.getElementById('date').valueAsDate = new Date();
    </script>
</body>
</html>
