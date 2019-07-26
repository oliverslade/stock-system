# Super Simple Stock Market
A stock system for the Super Simple Stock Market Assignment authored by Oli Slade

## Running the tests
The application is Springboot 2 so to run the tests: 
- in IntelliJ:
	- import the project using the build.gradle file (wrapper)
	- run the build task in the gradle window or right click on the test folder and select `Run 'All Tests'`
- via command line:
	- Windows: cd into the application and `gradlew.bat test`
	- Unix: cd into the application and `./gradlew test`

## Requirements

 - For a given stock,
	 - Given any price as input, calculate the dividend yield
	 - Given any price as input, calculate the P/E Ratio
	 - Record a trade, with timestamp, quantity of shares, buy or sell indicators and traded price
	 - Calculate Volume Weighted Stock Price based on trades in the past 15 minutes
- Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

## Constraints & Notes
- Written in Java with Springboot
- No database or GUI provided
	- All data is held in memory at runtime
- 91% Class and Method test coverage
