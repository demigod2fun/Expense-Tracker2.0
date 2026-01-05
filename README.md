# 💰 Expense Tracker 2.0

A full-stack Java-based expense management system featuring both web and desktop interfaces. This application demonstrates enterprise-level Java development with JDBC, Servlets/JSP, Swing UI, data visualization, and real-time currency conversion.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Servlets](https://img.shields.io/badge/Servlets-007396?style=for-the-badge&logo=java&logoColor=white)

## 🎯 Overview

Expense Tracker 2.0 is a comprehensive financial management application that allows users to track expenses, set budgets, generate visual reports, and convert currencies in real-time. Built with a multi-tier architecture, it showcases modern Java development practices including the DAO pattern, secure authentication, and data visualization.

## ✨ Key Features

### Core Functionality
- **User Authentication**: Secure registration and login system with SHA-256 password hashing
- **Expense Management**: Full CRUD operations for tracking daily expenses
- **Category Organization**: Pre-defined categories (Food, Transport, Shopping, etc.) for better expense tracking
- **Budget Planning**: Create and monitor monthly budgets per category

### Advanced Features
- **Visual Reports**: Dynamic chart generation using JFreeChart (Pie, Bar, Line charts)
- **Currency Conversion**: Real-time exchange rates via external API with intelligent caching
- **Data Export**: CSV export functionality for external analysis
- **Dual Interface**: Access via web browser (JSP) or desktop application (Swing)
- **Time-based Analysis**: Monthly and yearly expense reporting

## 🏗️ Architecture

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  (Servlets/JSP + Swing UI)             │
├─────────────────────────────────────────┤
│         Service Layer                   │
│  (Business Logic & Validation)          │
├─────────────────────────────────────────┤
│         Data Access Layer (DAO)         │
│  (JDBC + PreparedStatements)           │
├─────────────────────────────────────────┤
│         Database (MySQL)                │
└─────────────────────────────────────────┘
```

## 🧰 Technology Stack

| Category | Technologies |
|----------|-------------|
| **Language** | Java 8+ |
| **Web Framework** | Servlets, JSP, JSTL |
| **Desktop UI** | Java Swing |
| **Database** | MySQL with JDBC |
| **Data Visualization** | JFreeChart |
| **HTTP Client** | Apache HttpClient |
| **JSON Processing** | org.json |
| **Server** | Apache Tomcat 8+ |

## 📁 Project Structure

```
ExpenseTracker/
├── src/
│   ├── model/              # POJOs (User, Expense, Budget)
│   ├── dao/                # Database access layer
│   ├── service/            # Business logic layer
│   ├── servlet/            # Web controllers
│   ├── filter/             # Authentication & encoding filters
│   ├── util/               # Helper classes (Password, Charts, API)
│   └── ui/                 # Swing desktop interface
├── webapp/
│   ├── WEB-INF/
│   │   ├── web.xml         # Servlet configuration
│   │   └── jsp/            # JSP pages
│   └── css/                # Stylesheets
├── lib/                    # External JAR files
├── resources/
│   └── config.properties   # Database configuration
├── schema.sql              # Database schema
└── README.md
```

## ⚙️ Installation & Setup

### Prerequisites
- Java JDK 8 or higher
- MySQL Server 5.7+
- Apache Tomcat 8.5+
- MySQL Connector/J JAR

### Step 1: Clone the Repository
```bash
git clone https://github.com/demigod2fun/Expense-Tracker2.0.git
cd Expense-Tracker2.0
```

### Step 2: Database Configuration
```bash
# Create the database
mysql -u root -p < schema.sql
```

Update `resources/config.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/expense_tracker_db
db.username=root
db.password=yourpassword
```

### Step 3: Add Dependencies
Ensure the following JARs are in the `lib/` folder:
- mysql-connector-java-8.0.33.jar
- jfreechart-1.5.3.jar
- jcommon-1.0.24.jar
- httpclient-4.5.14.jar
- json-20231013.jar

### Step 4: Deploy to Tomcat
1. Build the project as a WAR file or exploded directory
2. Deploy to Tomcat webapps directory
3. Start Tomcat server
4. Access at: `http://localhost:8080/ExpenseTracker/login.jsp`

## 🚀 Usage

### Web Application

1. **Register/Login**: Navigate to the login page and create an account
2. **Add Expenses**: Fill in date, category, description, amount, and currency
3. **View Dashboard**: See category-wise breakdown and monthly trends
4. **Generate Reports**: Access visual reports with pie, bar, and line charts
5. **Convert Currency**: Use the built-in converter with real-time rates
6. **Set Budgets**: Create monthly budgets and track spending against limits

### Desktop Application (Optional)
```bash
javac -cp ".:lib/*" -d bin src/**/*.java
java -cp "bin:lib/*" com.expensetracker.Main
```

## 🔐 Security Features

- **Password Hashing**: SHA-256 encryption for all passwords
- **SQL Injection Prevention**: PreparedStatements for all database queries
- **Authentication Filter**: Protected routes with session validation
- **API Response Caching**: Minimizes external API calls for currency data

## 🧪 Testing

Run through this checklist to verify functionality:
- [ ] User registration and login
- [ ] Adding expenses across multiple categories
- [ ] Dashboard displays correct data
- [ ] Budget creation and tracking
- [ ] Currency converter with different pairs
- [ ] CSV export functionality
- [ ] Data persistence after server restart

## 📚 Learning Outcomes

This project demonstrates proficiency in:
- Object-Oriented Programming principles
- Multi-tier application architecture
- JDBC and database interaction patterns
- Web development with Servlets and JSP
- Desktop GUI development with Swing
- Data visualization techniques
- RESTful API consumption
- Security best practices

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👤 Author

**demigod2fun**
- GitHub: [@demigod2fun](https://github.com/demigod2fun)

## 🙏 Acknowledgments

- JFreeChart library for data visualization
- Apache HttpClient for API integration
- MySQL for database management

---

**Note**: This project is developed for educational purposes to demonstrate full-stack Java development skills.
