# Finance Analytics Processor 📊💰

A production-ready Java CLI application designed to process, filter, and analyze large volumes of financial transaction data from text files. The engine handles automated tax calculations, foreign currency conversions, and complex data aggregation to generate structured financial reports.

Built with **Java 21** using **Three-Tier (Clean) Architecture** principles without monolithic frameworks, demonstrating high proficiency in core Java features, data structures, and clean coding standards.

---

## 🚀 Key Features

*   **Multi-Source Data Ingestion:** Safely imports and processes account profiles and transaction data from custom flat-file formats (`.txt`).
*   **Dynamic Rule-Based Processing:** Automatically adjusts transaction amounts based on their specific polymorphic sub-types:
    *   *Taxable Transactions:* Calculates and deducts specific tax rates from the net value.
    *   *Foreign Currency:* Converts international amounts into base currency using precision exchange rates.
    *   *Recurrent Transactions:* Handles interval tracking patterns (hourly, daily, weekly, etc.).
*   **Advanced Data Aggregation:** Allows user-driven multi-criteria transaction slicing through an interactive CLI menu:
    *   *Filtering:* Chain filters via logical `AND` operators (Categories, Date Ranges, Value Brackets, Substring Comment matching).
    *   *Grouping:* Group data by Month, Year, Day of Week, Category, Profit/Loss, Account Type, or User ID.
    *   *Metrics:* Execute Sum, Average, or Count functions over the targeted datasets.
*   **Structured JSON Reporting:** Generates and exports precise analytics snapshots containing calculation timestamps, active filters, and dataset telemetry.

---

## 🛠 Tech Stack

*   **Language:** Java 21 (LTS)
*   **Build System:** Gradle
*   **Core Mechanics:** Java Generics, Predicates, Custom Collectors
*   **Boilerplate Reduction:** Lombok
*   **Data Persistence:** JSON Serialization / Deserialization

---

## 🏗 Architectural Design

The codebase strictly respects the boundaries of **Three-Tier Architecture** to isolate infrastructure from core domain invariants:

1.  **Presentation Layer (`com.skillbox.controller`):** CLI-driven console interface managing sequential state transitions and user inputs.
2.  **Business Logic Layer (`com.skillbox.service`):** Core orchestration center evaluating filters, calculating specific transaction mutations, and bundling complex aggregation requests.
3.  **Data Access Layer (`com.skillbox.data`):** Repository interfaces managing direct file I/O streams (`accounts.txt`, `transactions.txt`), decoupling domain entities from raw data layout.

---

## 🏁 Getting Started

### CLI Launch Parameters
The application expects exactly **three arguments** passed on startup:
1. Path to accounts file (`accounts.txt`)
2. Path to transactions file (`transactions.txt`)
3. Path to the destination file for saving analytics report

### Local Setup

1. **Clone the repository:**
   ```bash
   git clone github.com
   cd finance-analytics-processor
   ```

2. **Build the Application via Gradle:**
   ```bash
   ./gradlew build
   ```

3. **Run with Production Arguments:**
   ```bash
   ./gradlew run --args="accounts.txt transactions.txt analytics_report.json"
   ```

---

## 📊 Sample Output Format (JSON Export)

When calculations complete, the application exports structured insights following this schema:

```json
{
  "date" : [ 2026, 5, 13, 14, 00, 00 ],
  "groupOption" : "grouping by category",
  "aggregateOption" : "sum calculation",
  "filter" : "Category: Food, start date — 2024-01-01, min amount — -50",
  "data" : {
    "Salary" : 4090.00,
    "Entertainment" : -10284.15,
    "Food" : -38.00
  }
}
```
