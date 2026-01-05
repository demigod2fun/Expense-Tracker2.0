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
    <title>Dashboard - Expense Tracker</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Dashboard</h1>
            <div class="header-right">
                <span>Welcome, ${sessionScope.user.name}</span>
                <a href="logout" class="btn-logout">Logout</a>
            </div>
        </header>
        
        <nav class="navbar">
            <a href="dashboard.jsp" class="active">Dashboard</a>
            <a href="addExpense.jsp">Add Expense</a>
            <a href="reports.jsp">Reports</a>
            <a href="currencyConverter.jsp">Currency</a>
            <a href="budgets.jsp">Budgets</a>
        </nav>
        
        <main>
            <section class="dashboard-grid">
                <div class="card">
                    <h2>📊 Category Chart</h2>
                    <img src="../report?type=category" alt="Category Chart">
                </div>
                
                <div class="card">
                    <h2>📈 Monthly Trend</h2>
                    <img src="../report?type=monthly" alt="Monthly Chart">
                </div>
                
                <div class="card">
                    <h2>💰 Statistics</h2>
                    <div class="stats">
                        <div class="stat-item">
                            <label>Total Expenses</label>
                            <span class="value">${stats.totalExpenses}</span>
                        </div>
                        <div class="stat-item">
                            <label>Average</label>
                            <span class="value">${stats.averageExpense}</span>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </div>
</body>
</html>
