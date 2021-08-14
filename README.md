# Balance viewer application
To find the relative balance of an account at the given time frame.

##Tech stack used
* Java 8
* Gradle 7.1
* Groovy for unit testing
* Jacoco for coverage report
* Spring boot framework
* OpenCSV to read and parse CSV files and create POJO.
* Used Slf4j for the logging library

##Building the application
* gradle clean build

##To run unit tests
* gradle clean build

##Running the application
* Uses transactions.csv in the root folder of the project
* java -jar balanceviewer-0.0.1-SNAPSHOT.jar "ACC334455" "20/10/2018 12:00:00" "20/10/2018 19:00:00" "/Users/haridhanakoti/Documents/github/BalanceViewer/transactions.csv"


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