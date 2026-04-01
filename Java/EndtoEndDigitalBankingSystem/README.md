# End-to-End Digital Banking System

This is a comprehensive Java-based digital banking system project built for a Java Mini Project (Sem 4). The application operates entirely via a Command Line Interface (CLI) and securely manages user accounts, authentications, deposits, withdrawals, and inter-account transfers.

## Completed Features & Implementations

* **Interactive CLI (`Main.java`)**
  * Fully functioning dual-state menu system (Main Menu & User Dashboard).
  * Dynamically reads the Bank Name from the `config.properties` configuration file.
* **Domain Models (`src/main/model/`)**
  * `SavingsAccount` - Implemented with minimum balance protections preventing negative withdrawals.
  * `CurrentAccount` - Implemented with an authorized overdraft limit enabling withdrawals below a $0 balance up to a restricted threshold.
  * `Transaction` - Implemented with `LocalDateTime` timestamping and ENUM-based categorizations (DEPOSIT, WITHDRAWAL, TRANSFER).
* **Data Repositories (`src/main/repository/`)**
  * `UserRepository`, `AccountRepository`, & `TransactionRepository` fully persist data during the lifecycle of the application using in-memory Java Collections (`HashMap` and `ArrayList`) optimized with `Java Streams` querying.
* **Business Logic Services (`src/main/service/`)**
  * `AuthService` - Validates secure user login and guards against duplicate email registration.
  * `AccountService` - Manages Savings vs Current account opening configurations securely. 
  * `TransactionService` - Core transaction controller logic governing money movement handling checked custom errors `InsufficientBalanceException` and `InvalidAmountException` securely. It updates all appropriate accounts and stores transaction histories.
* **System Utilities (`src/main/util/`)**
  * `IDGenerator` - Autogenerates and assigns guaranteed universally unique transaction codes, account identifiers, and user ID tags using standard Java `UUID`s.
  * `InputUtil` - Safe user input `Scanner` wrappers guaranteeing the entire application won't crash when encountering an `InputMismatchException` typo.
* **Custom Exceptions (`src/main/exception/`)**
  * Implemented specific routing for `AuthenticationFailedException`, `InsufficientBalanceException`, `InvalidAmountException`, and `UserNotFoundException`.

## Project Structure

```text
EndtoEndDigitalBankingSystem
│
├── README.md
└── src
    ├── main                 # Source Root Directory
    │   ├── Main.java        # Central Console UI Application
    │   ├── model            # Data models (User, Account, etc.)
    │   ├── service          # Business logic
    │   ├── repository       # Data access
    │   ├── exception        # Custom exceptions
    │   └── util             # Utility classes
    │
    └── resources
        └── config.properties # Global Configuration file
```

## How to Compile & Run

We recommend navigating to the source folder and compiling all files at once. Ensure you have Java 17+ installed.

**1. Navigate to the source folder:**
```bash
cd src/main
```

**2. Compile all Java files at once:**
*If you are using Windows PowerShell:*
```bash
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { "$($_.FullName.Replace('\', '/'))" } | Set-Content -Encoding Ascii sources.txt; javac "@sources.txt"
```
*If you are using Linux/Mac:*
```bash
javac $(find . -name "*.java")
```

**3. Run the compiled application:**
```bash
java Main
```
