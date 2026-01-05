Java Expense Tracker – Web & Desktop
A full-stack Java Expense Tracker that works as both a web app and a desktop app, built to demonstrate Core Java, JDBC, Servlets/JSP, Swing UI, JFreeChart reporting, and real-time currency conversion.

✨ Features
User authentication (register/login, password hashing with SHA-256)

Add, view, update, delete expenses

Category-based expense management (Food, Transport, Shopping, etc.)

Monthly and yearly expense reports

Budget creation and tracking per category

JFreeChart-based visual reports (Pie, Bar, Line charts)

Real-time currency conversion via external API

CSV export of expenses

Web interface (Servlets + JSP) and optional Swing desktop UI

MySQL database integration with prepared statements

🏗️ Project Structure
text
ExpenseTracker/
├── src/
│   ├── model/
│   │   ├── User.java
│   │   ├── Expense.java
│   │   ├── Budget.java
│   │   ├── Currency.java
│   │   └── ExpenseCategory.java
│   ├── dao/
│   │   ├── DatabaseConnection.java
│   │   ├── UserDAO.java
│   │   ├── ExpenseDAO.java
│   │   ├── BudgetDAO.java
│   │   └── CurrencyDAO.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── ExpenseService.java
│   │   ├── BudgetService.java
│   │   ├── CurrencyConversionService.java
│   │   ├── ReportService.java
│   │   └── ExportService.java
│   ├── servlet/
│   │   ├── LoginServlet.java
│   │   ├── DashboardServlet.java
│   │   ├── ExpenseServlet.java
│   │   ├── ReportServlet.java
│   │   └── CurrencyServlet.java
│   ├── filter/
│   │   ├── AuthenticationFilter.java
│   │   └── CharacterEncodingFilter.java
│   ├── listener/
│   │   └── SessionListener.java
│   ├── util/
│   │   ├── PasswordUtil.java
│   │   ├── ValidationUtil.java
│   │   ├── ChartGenerator.java
│   │   └── CurrencyAPIClient.java
│   ├── ui/              # Optional Swing desktop UI
│   │   ├── DashboardFrame.java
│   │   └── ReportChartPanel.java
│   └── Main.java
├── webapp/
│   ├── WEB-INF/
│   │   ├── web.xml
│   │   └── jsp/
│   │       ├── login.jsp
│   │       ├── dashboard.jsp
│   │       ├── addExpense.jsp
│   │       ├── reports.jsp
│   │       └── currencyConverter.jsp
│   └── css/
│       └── style.css
├── lib/
│   ├── mysql-connector-java-8.0.33.jar
│   ├── jfreechart-1.5.3.jar
│   ├── jcommon-1.0.24.jar
│   ├── httpclient-4.5.14.jar
│   └── json-20231013.jar
├── resources/
│   └── config.properties
├── schema.sql
└── README.md
🧰 Tech Stack
Language: Java 8+

Web: Servlets, JSP, JSTL

Desktop UI (optional): Java Swing

Database: MySQL

ORM/Access: Plain JDBC (DAO pattern)

Charts: JFreeChart

HTTP & JSON: Apache HttpClient, org.json

Server: Apache Tomcat (8+ recommended)

⚙️ Setup & Installation
1. Clone the repository
bash
git clone https://github.com/<your-username>/expense-tracker-java.git
cd expense-tracker-java
2. Configure the database
Start MySQL and create the database using schema.sql:

bash
mysql -u root -p < schema.sql
Update resources/config.properties with your DB credentials:

text
db.url=jdbc:mysql://localhost:3306/expense_tracker_db
db.username=root
db.password=yourpassword
3. Add required libraries
Place the JARs in the lib/ folder and add them to your project classpath:

mysql-connector-java-8.0.33.jar

jfreechart-1.5.3.jar

jcommon-1.0.24.jar

httpclient-4.5.14.jar

json-20231013.jar

If you use Maven/Gradle, you can replace these with dependencies instead of manual JARs.

4. Configure the web app (Tomcat)
Deploy the project as a WAR or as an exploded directory.

Ensure webapp/ becomes the web root (in IDEs like IntelliJ/Eclipse, mark it as Web Resource Directory).

web.xml is already configured under webapp/WEB-INF/web.xml.

Basic URL after deployment (default context):

text
http://localhost:8080/ExpenseTracker/login.jsp
🌐 How to Use – Web Application
1. Register & Login
Open: http://localhost:8080/ExpenseTracker/login.jsp

Click Register (if you have a register JSP) or use provided route.

Fill in:

Name

Email

Username

Password

Log in with your username and password.

2. Add an Expense
Go to Add Expense (menu or button).

Fill:

Date

Category (Food, Transport, etc.)

Description

Amount

Currency (INR, USD, EUR, GBP, etc.)

Submit to save. The expense is stored in the expenses table.

3. View Dashboard
Navigate to Dashboard.

You will see:

Category-wise expense chart (Pie chart)

Monthly trend chart (Bar chart)

Key statistics (total, average, max expense)

4. Reports Page
Open Reports.

Choose report type:

Category Breakdown

Monthly Trend

Budget vs Actual

The app generates JFreeChart charts dynamically and returns them as PNG images.

5. Currency Converter
Open Currency Converter.

Enter:

Amount

From currency

To currency

Click Convert to get:

Converted amount

Current exchange rate (fetched via HTTP API, then cached)

6. Budgets (if used)
Go to Budgets.

Set:

Category

Monthly budget amount

Dashboard/reports can show how much you have spent against your budget.

🖥️ How to Use – Desktop App (Optional)
If you want to showcase the Swing desktop interface as well:

1. Compile and Run
bash
javac -cp ".:lib/*" -d bin src/**/*.java
java -cp "bin:lib/*" com.expensetracker.Main
2. Desktop Features
Login & Register using Swing frames

Add and view expenses in tables

View basic charts in DashboardFrame via ReportChartPanel (if wired to JFreeChart)

Export expenses to CSV using ExportService

The desktop and web layers share the same model, DAO, and service classes, so both operate on the same MySQL database.

🔐 Security & Best Practices
Passwords are hashed using SHA-256 via PasswordUtil.

All DB access uses PreparedStatement to prevent SQL injection.

Authentication is enforced on web routes via AuthenticationFilter.

Currency API responses are cached in memory to reduce external calls.

🧪 Testing Checklist
 Register a new user and login

 Add multiple expenses in different categories

 Verify expenses appear in the dashboard and reports

 Test budget creation and check budget vs actual

 Use the currency converter with different currency pairs

 Export expenses to CSV (if enabled) and open the file

 Restart the server/app and confirm data persists

📚 Learning Objectives
This project is designed to demonstrate:

Core Java (OOP, collections, exceptions)

JDBC with DAO pattern

Web development with Servlets & JSP

Swing UI fundamentals

Data visualization with JFreeChart

Consuming third-party APIs (HTTP + JSON)

Basic security and layering (Controller–Service–DAO–DB)

