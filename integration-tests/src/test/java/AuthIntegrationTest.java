import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004/";
    }

    @Test
    public void shouldReturnOKWithValidationToken(){
        // 1. Arrange
        String loginPayload = """
            {
                "email" : "testuser@test.com",
                "password" : "password123"
            }
        """;

        // 2. Act

        Response response = RestAssured.given().contentType("application/json").body(loginPayload).when().post("/auth/login")
                .then().statusCode(200).body("token", notNullValue()).extract().response();

        System.out.println("Generated Token: " + response.jsonPath().getString("token"));
        // 3. Assert

    }

    @Test
    public void shouldReturnUnAuthorizedOnInvalidLogin(){
        // 1. Arrange
        String loginPayload = """
            {
                "email" : "invaliduser@test.com",
                "password" : "password"
            }
        """;

        // 2. Act

        RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);

//        System.out.println("Generated Token: " + response.jsonPath().getString("token"));
        // 3. Assert

    }
}
