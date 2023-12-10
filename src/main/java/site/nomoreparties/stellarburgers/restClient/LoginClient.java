package site.nomoreparties.stellarburgers.restClient;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.data.ClientData;

import static io.restassured.RestAssured.given;

public class LoginClient extends RestClient{
    private static final String LOGIN_PATH = "/api/auth/login";

    @Step("Send POST request to /api/auth/login")
    public ValidatableResponse loginClient(ClientData client) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(client)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
}