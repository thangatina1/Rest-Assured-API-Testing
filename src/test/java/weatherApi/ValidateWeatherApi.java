package weatherApi;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ValidateWeatherApi {

	@BeforeClass
	public void initPath() {

		RestAssured.baseURI = "http://api.openweathermap.org";
	}

	// Verify the Response code
	@Test
	public void validateResponseCode() {
		given().when().

				get("/data/2.5/weather?zip=94040,us&appid=651587654bcd0392c12915b211ad8efa")
				.then()
				.statusCode(200);

	}

	// Passing Parameters to GET Request
	@Test
	public void passQueryParametersToGetRequest() {

		given().
				params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather").then().body("cod", equalTo(200));


	}

	// Passing Parameters to GET Request
	@Test
	public void validateTemperature() {

		float minTemp = given()
				.params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.extract()
				.path("main.temp_min");

		float maxTemp = given()
				.params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.extract()
				.path("main.temp_max");

		float temp = given()
				.params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.extract()
				.path("main.temp");

		assertTrue(minTemp <= temp && temp <= maxTemp);


	}

	// PathParameter to read a JSON file
	@Test
	public void useSinglePathParameter() {

		String responseBody = given()
				.pathParam("suffixName", "monarchs")
				.when()
				.get("http://mysafeinfo.com/api/data?list=english{suffixName}&format=json")
				.getBody().asString();
		// System.out.println("Response Body is: " + responseBody);
	}

	// Negative to read a JSON file
	@Test
	public void negativeTest() {

		given().params("zip", "94040,AAA", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.body("message", equalTo("city not found"), "cod", equalTo("404"));
	}

	@Test
	// Validate if the format is JSON
	public void checkResponseContentTypeJson() {

		given().params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.assertThat()
				.contentType("application/json");
	}

	//A Key is available in the Response
	@Test
	public void isKeyAvailable() {

		given().params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.assertThat()
				.body("$", hasKey("name"))
				.body("$", not(hasKey("IamInvisible")));

	}

	//Data Providers
	// Returning a String Array. Data Providers can return maps too
	@DataProvider(name = "city")
	public Object[][] cityData() {
		return new String[][]{
				{"94040,us", "Mountain View"},
				{"03820,us", "Dover"},
				{"75024,us", "Plano"}
		};
	}

	@Test(dataProvider = "city")
	public void validateWithDataProviders(String zipCode, String cityName) {

		String city = given()
				.params("zip", zipCode, "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.extract()
				.path("name");

		System.out.println(city);

		assertEquals(city, cityName);

	}

	@Test
	public void responseTimeValidation() {

		given()
				.params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.assertThat()
				.time(lessThan(1000L), TimeUnit.MILLISECONDS);
	}

	ResponseSpecification respSpec;

	@BeforeClass
	public void createResponseSpecification() {

		respSpec = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.expectBody("name", equalTo("Mountain View")).
						build();
	}

	@Test
	public void useResponseSpecification() {
		given().
				params("zip", "94040,us", "appid", "651587654bcd0392c12915b211ad8efa")
				.when()
				.get("/data/2.5/weather")
				.then()
				.spec(respSpec)
				.and()
				.body("cod", equalTo(200));
	}

}
	
