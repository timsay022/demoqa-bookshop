package com.demoqa.api;

import com.demoqa.config.AuthConfig;
import com.demoqa.models.LoginRequestModel;
import com.demoqa.models.LoginResponseModel;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;

import static com.demoqa.spec.MainSpec.*;
import static io.restassured.RestAssured.given;

public class AuthorizationApiSteps {

    public static final AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());

    @Step("Авторизация через API")
    public LoginResponseModel login() {
        LoginRequestModel userLogin = new LoginRequestModel(config.login(), config.password());

        return
                given(mainRequestSpec)
                        .body(userLogin)

                        .when()
                        .post("/Account/v1/Login")

                        .then()
                        .spec(getResponseSpec(200))
                        .extract().as(LoginResponseModel.class);
    }
}
