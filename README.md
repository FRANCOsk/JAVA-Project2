# Market Share Table

A small legacy Java exercise that reads market-share data from CSV, filters it by country and quarter, calculates vendor shares, sorts results, and renders an HTML table.

## Technology

- Java 8-compatible source code
- Maven reproducible build
- OpenCSV 3.7
- Apache Commons Lang
- GitHub Actions CI on Java 17

## Build

```bash
mvn clean verify
```

The project keeps its original Eclipse-era directory layout. Maven is configured to compile sources from `navratil.table.component/src` and package the CSV data from `navratil.table.component/data`.

## Run

From the repository root:

```bash
mvn package
java -cp "target/market-share-table-1.0.0-SNAPSHOT.jar:$HOME/.m2/repository/com/opencsv/opencsv/3.7/opencsv-3.7.jar:$HOME/.m2/repository/org/apache/commons/commons-lang3/3.14.0/commons-lang3-3.14.0.jar" navratil.table.component.NavratilTableComponent
```

On Windows, replace the classpath separator `:` with `;`.

## Project structure

```text
navratil.table.component/
├── data/        sample CSV input
├── src/         Java sources
├── bin/         legacy compiled output; ignored for future changes
└── lib/         legacy local JAR copies; Maven now resolves dependencies
```

## CI

Every pull request and push to `main` runs `mvn clean verify`. The workflow uses read-only repository permissions and Maven dependency caching.

## Modernization notes

The original source and data remain unchanged. This modernization adds a documented, repeatable build and CI pipeline without rewriting the historical exercise.
