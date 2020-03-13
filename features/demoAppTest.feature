Feature: Test Case on TestApp

Background: 
	Given The TestApp launched with simulator
	
@sum
Scenario Outline: Sum of given value
	Then I launch the TestApp and do sum of "<First Number>" and "<Second Number>"

Examples:
	|First Number|Second Number|
	|100|200|
	|50|40|
	
@alert
Scenario: Click on Alert
	Then I invoke the Alert
