-- Database Schema for Expense Tracker
-- Drop database if exists and create fresh
DROP DATABASE IF EXISTS expense_tracker_db;
CREATE DATABASE expense_tracker_db;
USE expense_tracker_db;

-- Users Table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Categories Table
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert default categories
INSERT INTO categories (category_name, description) VALUES
('Food', 'Food and dining expenses'),
('Transportation', 'Travel and commute expenses'),
('Shopping', 'Shopping and retail purchases'),
('Entertainment', 'Entertainment and leisure'),
('Healthcare', 'Medical and health expenses'),
('Education', 'Educational expenses'),
('Utilities', 'Bills and utilities'),
('Others', 'Miscellaneous expenses');

-- Expenses Table
CREATE TABLE expenses (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL CHECK (amount > 0),
    expense_date DATE NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    INDEX idx_user_id (user_id),
    INDEX idx_expense_date (expense_date),
    INDEX idx_category_id (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Budgets Table
CREATE TABLE budgets (
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    budget_amount DECIMAL(10, 2) NOT NULL CHECK (budget_amount > 0),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    INDEX idx_user_id (user_id),
    INDEX idx_dates (start_date, end_date),
    CHECK (end_date > start_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create view for expense summary by category
CREATE VIEW expense_summary AS
SELECT 
    u.user_id,
    u.username,
    c.category_name,
    COUNT(e.expense_id) as expense_count,
    COALESCE(SUM(e.amount), 0) as total_amount,
    YEAR(e.expense_date) as year,
    MONTH(e.expense_date) as month
FROM users u
LEFT JOIN expenses e ON u.user_id = e.user_id
LEFT JOIN categories c ON e.category_id = c.category_id
GROUP BY u.user_id, c.category_name, YEAR(e.expense_date), MONTH(e.expense_date);

-- Create view for budget tracking
CREATE VIEW budget_tracking AS
SELECT 
    b.budget_id,
    u.username,
    c.category_name,
    b.budget_amount,
    COALESCE(SUM(e.amount), 0) as spent_amount,
    (b.budget_amount - COALESCE(SUM(e.amount), 0)) as remaining_amount,
    ROUND((COALESCE(SUM(e.amount), 0) / b.budget_amount) * 100, 2) as usage_percentage,
    b.start_date,
    b.end_date
FROM budgets b
JOIN users u ON b.user_id = u.user_id
JOIN categories c ON b.category_id = c.category_id
LEFT JOIN expenses e ON b.user_id = e.user_id 
    AND b.category_id = e.category_id 
    AND e.expense_date BETWEEN b.start_date AND b.end_date
GROUP BY b.budget_id, u.username, c.category_name, b.budget_amount, b.start_date, b.end_date;

-- Insert sample data for testing (optional)
-- Sample User (password: password123 - hashed)
INSERT INTO users (username, email, password) VALUES
('testuser', 'test@example.com', '482c811da5d5b4bc6d497ffa98491e38'); -- MD5 hash for demo

-- Sample Expenses for testuser
INSERT INTO expenses (user_id, category_id, amount, expense_date, description) VALUES
(1, 1, 250.00, '2025-11-20', 'Grocery shopping'),
(1, 2, 150.00, '2025-11-19', 'Uber ride'),
(1, 3, 1200.00, '2025-11-18', 'New shoes'),
(1, 4, 500.00, '2025-11-17', 'Movie tickets');

-- Sample Budget for testuser
INSERT INTO budgets (user_id, category_id, budget_amount, start_date, end_date) VALUES
(1, 1, 5000.00, '2025-11-01', '2025-11-30'),
(1, 2, 3000.00, '2025-11-01', '2025-11-30'),
(1, 3, 8000.00, '2025-11-01', '2025-11-30');

-- Verify installation
SELECT 'Database created successfully!' as Status;
SELECT COUNT(*) as CategoryCount FROM categories;
SELECT COUNT(*) as UserCount FROM users;
-- Currency rates table
CREATE TABLE currency_rates (
    id INT PRIMARY KEY AUTO_INCREMENT,
    currency_code VARCHAR(3) UNIQUE NOT NULL,
    currency_name VARCHAR(50),
    exchange_rate DECIMAL(10, 4) NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Add currency column to expenses
ALTER TABLE expenses ADD COLUMN currency VARCHAR(3) DEFAULT 'INR';

-- Budget table (if not exists)
CREATE TABLE budgets (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    budget_amount DECIMAL(10, 2) NOT NULL,
    month INT,
    year INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE KEY unique_budget (user_id, category, month, year)
);
-- Create Database
CREATE DATABASE IF NOT EXISTS expense_tracker_db;
USE expense_tracker_db;

-- Users Table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Expenses Table
CREATE TABLE expenses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    date DATE NOT NULL,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'INR',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, date),
    INDEX idx_category (category)
);

-- Budgets Table
CREATE TABLE budgets (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    budget_amount DECIMAL(10, 2) NOT NULL,
    month INT NOT NULL,
    year INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_budget (user_id, category, month, year),
    INDEX idx_user_month (user_id, month, year)
);

-- Currency Rates Table
CREATE TABLE currency_rates (
    id INT PRIMARY KEY AUTO_INCREMENT,
    currency_code VARCHAR(3) UNIQUE NOT NULL,
    currency_name VARCHAR(50),
    exchange_rate DECIMAL(10, 4) NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert default currencies
INSERT INTO currency_rates (currency_code, currency_name, exchange_rate) VALUES
('INR', 'Indian Rupee', 1.0),
('USD', 'US Dollar', 83.5),
('EUR', 'Euro', 91.2),
('GBP', 'British Pound', 105.3),
('JPY', 'Japanese Yen', 0.57),
('AUD', 'Australian Dollar', 55.8),
('CAD', 'Canadian Dollar', 62.4),
('CHF', 'Swiss Franc', 94.2),
('CNY', 'Chinese Yuan', 11.5),
('SEK', 'Swedish Krona', 8.1);