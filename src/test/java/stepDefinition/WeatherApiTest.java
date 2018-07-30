package stepDefinition;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;


import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.BeforeClass;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
public class WeatherApiTest {

	
	@Before
	@Given("^Launce the API$")
	public void launce_the_API() throws Throwable {
		RestAssured.baseURI = "http://api.openweathermap.org";   
	 
	}

	@Then("^The Response code is succesfull$")
	public void the_Response_code_is_succesfull() throws Throwable {
		given().when().

		get("/data/2.5/weather?zip=94040,us&appid=651587654bcd0392c12915b211ad8efa").
		then()
		.statusCode(200);
	 
	}

	@Then("^The Response code if negative$")
	public void the_Response_code_if_negative() throws Throwable {
		given().
		params("zip", "94040,AAA", "appid", "651587654bcd0392c12915b211ad8efa").
		when().
		get("/data/2.5/weather").
		then().
		body("message", equalTo("city not found"), "cod", equalTo("404"));
	 
	}

	@Then("^The Response is in JSON format$")
	public void the_Response_is_in_JSON_format() throws Throwable {
		given().params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa").when().
		get("/data/2.5/weather").
		then().
		assertThat().
		contentType("application/json");
	 
	}

	@Then("^Validate the temperature range$")
	public void validate_the_temperature_range() throws Throwable {
	   
		float minTemp = given().
				params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa").
				when().
				get("/data/2.5/weather").
				then().extract().
		        path("main.temp_min");
				
		float maxTemp = given().
						params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa").
						when().
						get("/data/2.5/weather").
						then().extract().
				        path("main.temp_max");
				
		float temp = given().
						params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa").
						when().
						get("/data/2.5/weather").
						then().extract().
				        path("main.temp");
				
				
				Assert.assertTrue(minTemp <= temp && temp <= maxTemp);
				
	}

	@Then("^The City name is displayed as expected$")
	public void the_City_name_is_displayed_as_expected() throws Throwable {
	    
	 
	}

	@Then("^The Key name is availabe in the response$")
	public void the_Key_name_is_availabe_in_the_response() throws Throwable {
	    
		given().params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa").when().
		get("/data/2.5/weather").
		then().
			assertThat().
			body("$", hasKey("name"));
	}

	@Then("^The Key IsInvisible is not availabe in the response$")
	public void the_Key_IsInvisible_is_not_availabe_in_the_response() throws Throwable {
	    
		given().params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa").when().
		get("/data/2.5/weather").
		then().
			assertThat().
			body("$", not(hasKey("IamInvisible")));
	}

	@Then("^Validate response time$")
	public void validate_response_time() throws Throwable {
	    
		given().
		params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa").
		when().
		get("/data/2.5/weather").
		then().
		assertThat().
		time(lessThan(1000L),TimeUnit.MILLISECONDS);
	}
	//ResponseSpecification respSpec;
	
	public ResponseSpecification createResponseSpecification() {
		ResponseSpecification respSpec;
		respSpec = new ResponseSpecBuilder().
				expectStatusCode(200).
				expectContentType(ContentType.JSON).
				expectBody("name", equalTo("Mountain View")).
				build();
			return respSpec;
	}

	@Then("^Validate standard assertions using ResponseSpec$")
	public void validate_standard_assertions_using_ResponseSpec() throws Throwable {
		given().
		params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa").
		when().
		get("/data/2.5/weather").then().		
		
			spec(createResponseSpecification());
	 
	}}
