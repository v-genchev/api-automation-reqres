# Simple API Automation [![Automation Tests](https://github.com/github/docs/actions/workflows/ci.yml/badge.svg)

Example framework using Java, REST Assured, TestNG, Gradle, Allure Reports

## Requirements

1. Java 17
2. Gradle

## Setup

1. Clone the repo
   ```
    git clone https://github.com/v-genchev/api-automation-reqres.git
    ```
2. Run tests
    ```
     ./gradlew clean test
    ```
3. Serve allure report to see the results
    ```
      ./gradlew allureServe
    ```

Note: You can also run the tests directly from IntelliJ

## Tests are run in GitHub Actions

You can find the report [here](https://v-genchev.github.io/api-automation-reqres/)
    
