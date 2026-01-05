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
    <title>Currency Converter - Expense Tracker</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>💱 Currency Converter</h1>
            <a href="dashboard.jsp" class="btn-back">← Back</a>
        </header>
        
        <div class="converter-card">
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="number" id="amount" value="1" step="0.01">
            </div>
            
            <div class="form-group">
                <label for="fromCurrency">From:</label>
                <select id="fromCurrency">
                    <option value="INR">INR ₹</option>
                    <option value="USD">USD $</option>
                    <option value="EUR">EUR €</option>
                    <option value="GBP">GBP £</option>
                    <option value="JPY">JPY ¥</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="toCurrency">To:</label>
                <select id="toCurrency">
                    <option value="USD">USD $</option>
                    <option value="INR">INR ₹</option>
                    <option value="EUR">EUR €</option>
                    <option value="GBP">GBP £</option>
                    <option value="JPY">JPY ¥</option>
                </select>
            </div>
            
            <button onclick="convert()" class="btn-primary">Convert</button>
            
            <div id="result" style="display:none;" class="result">
                <p id="resultText"></p>
            </div>
        </div>
    </div>
    
    <script>
        async function convert() {
            const amount = document.getElementById('amount').value;
            const from = document.getElementById('fromCurrency').value;
            const to = document.getElementById('toCurrency').value;
            
            try {
                const response = await fetch('../api/currency', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: `amount=${amount}&from=${from}&to=${to}`
                });
                
                const data = await response.json();
                if (data.success) {
                    const rate = (data.convertedAmount / data.originalAmount).toFixed(4);
                    document.getElementById('resultText').innerHTML = 
                        `${data.originalAmount} ${data.fromCurrency} = ${data.convertedAmount.toFixed(2)} ${data.toCurrency}<br>Rate: 1 ${from} = ${rate} ${to}`;
                    document.getElementById('result').style.display = 'block';
                }
            } catch(error) {
                alert('Conversion failed: ' + error);
            }
        }
    </script>
</body>
</html>
