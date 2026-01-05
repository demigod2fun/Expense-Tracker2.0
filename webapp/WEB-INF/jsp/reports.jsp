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
    <title>Reports - Expense Tracker</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>📊 Reports & Analytics</h1>
            <a href="dashboard.jsp" class="btn-back">← Back</a>
        </header>
        
        <section class="reports-section">
            <div class="filters">
                <label for="reportType">Report Type:</label>
                <select id="reportType" onchange="changeReport()">
                    <option value="">-- Select Report --</option>
                    <option value="category">Category Breakdown</option>
                    <option value="monthly">Monthly Trend</option>
                    <option value="budget">Budget vs Actual</option>
                </select>
            </div>
            
            <div id="reportContainer" class="report-container">
                <p>Select a report type to view</p>
            </div>
        </section>
    </div>
    
    <script>
        function changeReport() {
            const reportType = document.getElementById('reportType').value;
            if (reportType) {
                const img = document.createElement('img');
                img.src = '../report?type=' + reportType;
                img.style.maxWidth = '100%';
                document.getElementById('reportContainer').innerHTML = '';
                document.getElementById('reportContainer').appendChild(img);
            }
        }
    </script>
</body>
</html>
