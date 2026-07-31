# Haziq's ATM Simulator

A feature-rich **ATM & Bank Account Management Simulator** built entirely in **Java Swing**. This desktop application simulates real-world ATM operations including account registration, deposits, withdrawals, mini statements, PIN changes, and more — all with a modern **Navy Blue & Gold** UI theme.

> **Note:** This is a **simulator** — it runs fully standalone without a real MySQL database. All data operations are mocked internally so you can run and test the full application with zero setup.

---

## Features

- **Secure Login** — Card number + PIN authentication with a Show/Hide PIN toggle
- **3-Step Account Registration** — Personal details, additional info, and account preferences
- **Deposit & Withdraw** — Real-time balance tracking with insufficient funds protection
- **Fast Cash** — Quick preset withdrawal amounts (Rs 100, 500, 1000, 2000, 5000, 10000)
- **Mini Statement** — View recent transactions with running balance
- **PIN Change** — Securely update your ATM PIN
- **Balance Enquiry** — Instant current balance display
- **Logout Confirmation** — Prevents accidental exits
- **Hover Effects** — Responsive button animations throughout

---

## Security & Code Quality

- All database queries use **PreparedStatement** (SQL injection prevention)
- Input validation on all forms (email format, numeric PIN code, required fields)
- `JPasswordField` uses `getPassword()` — no deprecated API calls
- Graceful error handling with user-friendly dialog popups
- No hardcoded credentials in any source file

---

## Tech Stack

| Technology | Purpose |
|---|---|
| **Java 17+** | Core language |
| **Java Swing** | GUI framework |
| **JCalendar 1.4** | Date picker widget in signup form |
| **MySQL Connector 8.0.28** | Included for real DB connection |
| **Java Reflection / Proxy API** | Powers the mock database in simulator mode |

---

## How to Run

### Prerequisites
- Java JDK 17 or higher installed
- `lib/` folder already contains all required JARs (included in this repo)

### Compile
```bash
javac -cp "lib/*" -d bin src/bank/management/system/*.java
```

### Run
```bash
java -cp "bin;lib/*" bank.management.system.Login
```

> **Windows:** Use semicolons `;` as classpath separator  
> **Linux/Mac:** Use colons `:` as classpath separator

---

## Project Structure

```
ATM-Simulator-Java/
├── src/bank/management/system/
│   ├── Login.java           # ATM login screen (entry point)
│   ├── Transactions.java    # Main ATM menu
│   ├── Signup.java          # Registration - Page 1 (Personal Details)
│   ├── Signup2.java         # Registration - Page 2 (Additional Info)
│   ├── Signup3.java         # Registration - Page 3 (Account Preferences)
│   ├── Deposit.java         # Deposit money
│   ├── Withdrawl.java       # Withdraw money
│   ├── FastCash.java        # Quick preset cash withdrawal
│   ├── BalanceEnquiry.java  # Check account balance
│   ├── MiniStatement.java   # View recent transactions
│   ├── Pin.java             # Change ATM PIN
│   └── Conn.java            # Database connection (mock/simulator mode)
├── lib/
│   ├── jcalendar-1.4.jar
│   └── mysql-connector-java-8.0.28.jar
├── .gitignore
└── README.md
```

---

## Connecting a Real Database

Replace the mock in `Conn.java` with a real JDBC connection, then create these MySQL tables:

```sql
CREATE TABLE login (formno VARCHAR(10), cardnumber VARCHAR(20), pin VARCHAR(4));
CREATE TABLE signup (formno VARCHAR(10), name VARCHAR(50), fname VARCHAR(50), dob VARCHAR(20), gender VARCHAR(10), email VARCHAR(50), marital VARCHAR(20), address VARCHAR(100), city VARCHAR(30), pincode VARCHAR(10), state VARCHAR(30));
CREATE TABLE signuptwo (formno VARCHAR(10), religion VARCHAR(20), category VARCHAR(10), income VARCHAR(20), education VARCHAR(30), occupation VARCHAR(20), pan VARCHAR(15), aadhar VARCHAR(15), seniorcitizen VARCHAR(5), existingaccount VARCHAR(5));
CREATE TABLE signupthree (formno VARCHAR(10), account_type VARCHAR(30), cardnumber VARCHAR(20), pin VARCHAR(4), facility VARCHAR(100));
CREATE TABLE bank (pin VARCHAR(4), date VARCHAR(50), type VARCHAR(15), amount VARCHAR(10));
```

---

## Author

**Haziq Zia**  
Built as a Java Swing desktop project — customized, refactored, and improved.

---

## License

This project is open source and available under the MIT License.