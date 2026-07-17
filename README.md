# Market Share Analyzer

A Java command-line application for reading market-share data from CSV, filtering it by country and quarter, calculating vendor shares, sorting results, and rendering an HTML table.

## Highlights

- standards-based Maven project layout
- CSV mapping with OpenCSV
- immutable result type using a Java record
- filtering and case-insensitive vendor lookup
- sorting by vendor or units
- safe HTML table generation
- JUnit 5 test coverage
- GitHub Actions CI and Dependabot

## Technology stack

- Java 17
- Maven
- OpenCSV 5.12
- JUnit 5

## Run locally

Requirements:

- JDK 17 or newer
- Maven 3.9 or newer

Build and test:

```bash
mvn clean verify
```

Run with the bundled sample data:

```bash
mvn exec:java
```

Run with a custom CSV file:

```bash
mvn exec:java -Dexec.args="/path/to/data.csv"
```

The CSV file must contain these headers:

```text
Country,Timescale,Vendor,Units
```

## Project structure

```text
src/main/java       application source code
src/main/resources  bundled sample CSV data
src/test/java       automated tests
```

## Example capabilities

- select records for `Czech Republic` and `2010 Q4`
- calculate Dell's units and percentage share
- sort records alphabetically or by unit count
- output a compact HTML table

## Security and maintenance

The former manually committed OpenCSV and Commons Lang JAR files have been replaced by Maven-managed dependencies. Dependabot checks Maven and GitHub Actions dependencies weekly, and CI runs a clean build and all tests for each pull request.
