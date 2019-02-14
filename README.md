# Test - Daily Trade Reporting Engine
 
# Requirement 
As a trade analyst, I want a daily report to be generated that will show the entities received in the instructions from various clients. The entities should show the total Outgoing & Incoming settled amounts in USD based on settlement date and entities ranked based on Incoming and Outgoing Amount
# Input
Set of instructions received from client
# Expected Output 
A (daily) report printed in console with Outgoing & Incoming Amounts in USD currency and Ranking based on the respective amounts for each date
# Input Details 
Instructions received from various clients to JPMC:
- Instruction: An instruction to Buy or Sell 
- Entity: A financial entity whose shares are to be bought or sold 
- Instruction Date: Date on which the instruction was sent to JP Morgan by various clients 
- Settlement Date: The date on which the client wished for the instruction to be settled with respect to Instruction Date 
- Buy/Sell flag (Trade Action): 
        B – Buy – outgoing 
        S – Sell – incoming 
- Agreed Fx is the foreign exchange rate with respect to USD that was agreed 
- Units: Number of shares to be bought or sold 
- Price Per Unit
- USD Amount: Price per unit * Units * Agreed Fx

# Working Days
Settlement date for an instruction may change based on the currency of the instruction, especially for Arab currencies UAE Dirham (AED) and Saudi Riyal (SAR) due different working days than the rest of the world.

A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where the work week starts Sunday and ends Thursday. (Assumption: No other holidays to be taken into account)

The code has the logic to accurately find the Work Days in the Workingdays abstract class. There are 2 different subclasses of this class:
- Arab Working Days: For the 2 Arab Currencies Work week starts Sunday and ends Thursday.
- Default Working Days: For Rest of the world in which a work week starts Monday and ends Friday.

# InstructionSettlementDateCalculator
Static class contains the logic to accurately calculate the Settlement Date based taking the currency level exceptions into consideration.

# Report Elements
The InstructionSettlementStatsCalculator class is responsible of generating the elements required to be reported as part of the console report based on a set of given instructions.
- The Outgoing Daily Amount is calculated by grouping the instruction with BUY action by settlement date and then sum up all the USD trade amount for each date.
- The Incoming Daily Amount is calculated by grouping the instruction with SELL action by settlement date and then sum up all the USD trade amount for each date.
- The Outgoing Daily Ranking is calculated by first grouping the instruction with BUY action by settlement date then sort by the USD trade amount and creates a Rank object for each element.
- The Incoming Daily Ranking is calculated by first grouping the instruction with SELL action by settlement date then sort by the USD trade amount and creates a Rank object for each element.

The Rank class represents the ranking, entity and date of a record.

# Reporting
The ReportGenerator class is responsible of generating reports, using a StringBuilder, for the aforementioned statistics along with a generic Header and Trailer (Footer) for the report

# Input Data for Report
In order to show its usage there is a InputInstructionsGenerator class that generates a set of dummy instructions. Additional test data has been included on top of the given data. The data considered is as below:

S No| Entity | Buy/Sell | AgreedFx  | Currenncy | Instruction Date | Settlement Date | Units | Price per Unit
|:-----| -------|:--------:| ---------:|---------:|---------:|---------:|---------:|---------:|
1| foo | Buy | 0.50 | SGD* | 1-Jan-16 |	2-Jan-16 |	200 |	100.25	
2| bar | Sell| 0.22 | AED |5-Jan-16 |	7-Jan-16 |	450 |	150.5	
3| coo | Buy | 0.014 | INR |1-Jan-16 |	2-Jan-16 |	300 |	175	
4| car | Sell| 0.76 | CAD |5-Jan-16 |	7-Jan-16 |	150 |	200	
5| too | Buy | 0.15 | CNY |15-Jan-16 |	16-Jan-16 |	350 |	220	
6| tar | Sell      |    0.71 | AUD |17-Jan-16 |	19-Jan-16 |	250 |	125	
7| koo | Buy      |   1.13 | EUR |15-Jan-16 |	16-Jan-16 |	220 |	250	
8| kar | Sell      |  1.29 | GBP |17-Jan-16 |	19-Jan-16 |	270 |	300	
9| doo | Buy      |    0.21 | SAR |7-Jan-16 |	9-Jan-16 |	240 |	275	
10| dar | Sell      |   0.53 | NZD |8-Jan-16 |	9-Jan-16 |	120 |	320

*Note: SGP (provided  in the Tech Test Paper) is not a valid ISO currency code, hence SGD (Singapore Dollar) has been considered

# Test Scenarios
1.	Scenario 1:

Given an instruction to Buy

When the Currency is one of the default currencies and Settlement Date falls on a Saturday (Weekend)

Then the report should include the calculated USD amount under Outgoing Amount with settlement date of the next business day (Monday)

2.	Scenario 2:

Given an instruction to Sell

When the Currency is one of the default currencies and Settlement Date falls on a Saturday (Weekend)

Then the report should include the calculated USD amount under Incoming Amount with settlement date of the next business day (Monday)

3.	Scenario 3:

Given an instruction to Buy

When the Currency is one of the Arab currencies and Settlement Date falls on a Saturday (Weekend)

Then the report should include the calculated USD amount under Outgoing Amount with settlement date of the next business day (Sunday for Arab Currencies)

4.	Scenario 4:

Given an instruction to Sell

When the Currency is one of the Arab currencies and Settlement Date falls on a Thursday (Weekday)

Then the report should include the calculated USD amount under Incoming Amount with settlement date of the same day (Weekday)

5.	Scenario 5:

Given an instruction to Buy

When the Currency is one of the default currencies and Settlement Date falls on a Weekday

Then the report should include the calculated USD amount under Outgoing Amount with settlement date of the same day

6.	Scenario 6:

Given an instruction to Sell

When the Currency is one of the default currencies and Settlement Date falls on a Weekday

Then the report should include the calculated USD amount under Incoming Amount with settlement date of the same day

Unit Tests - JUnit tests are included for Date and Element objects
