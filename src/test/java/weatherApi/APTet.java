package weatherApi;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APTet {

        private String clientCredentialsLogin(String tokenUri, String clientId, String clientSecret) throws JSONException {
            Response response =
                    given().auth().preemptive().basic(clientId, clientSecret)
                            .formParam("grant_type", "client_credentials")
                            .when()
                            .post(tokenUri);
            JSONObject jsonObject = new JSONObject(response.getBody().asString());
            String accessToken = jsonObject.get("access_token").toString();
            String tokenType = jsonObject.get("token_type").toString();
            System.out.println("AccessToken   ************ " + accessToken);
            return accessToken;
            }


        public void callSecuredEndpoint(String accessToken, String inputJson, String url) {
            Response response = given()
                    .auth()
                    .preemptive()
                    .oauth2(accessToken)
                    .contentType("application/json")
                    .body(inputJson)
                    .when()
                    .post(url);
                    String responseBody = response.getBody().asString();

        }



}
