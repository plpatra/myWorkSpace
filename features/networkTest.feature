Feature: Test Case to simulate network download and upload throughput

@search
Scenario: Sum of given value
	Then I serch a text

@networkthroughput
Scenario Outline: Sum of given value
	Then I simulate different "<Download>" and "<Upload>" throughput

Examples:
	|Download|Upload|
	|5000|5000|
	|10000|7000|
	|15000|5000|
	|10000|9000|
	|20000|10000|
	|0|0|
