Feature: Validate the Weather API when you pass the ZIP in US Location 
	Description: The purpose of this feature is to test the Weather API Response
 
 
Scenario: Validate the Response when the Request is by Zip 
	Given Launce the API 
	
	Then The Response code is succesfull 
	
	Then The Response code if negative 
	
	Then The Response is in JSON format 
	
	Then Validate the temperature range 
	
	Then The City name is displayed as expected 
	Then The Key name is availabe in the response 
	Then The Key IsInvisible is not availabe in the response 
	
	Then Validate response time 
	Then Validate standard assertions using ResponseSpec 
	
