package com.demoqa.api;

import com.demoqa.models.LoginRequestModel;
import com.demoqa.models.LoginResponseModel;
import io.qameta.allure.Step;

import static com.demoqa.spec.MainSpec.mainRequestSpec;
import static com.demoqa.spec.MainSpec.mainResponseSpec;
import static com.demoqa.tests.TestData.login;
import static com.demoqa.tests.TestData.password;
import static io.restassured.RestAssured.given;

public class AuthorizationApi {

    @Step("Авторизация через API")
    public LoginResponseModel login() {
        LoginRequestModel userLogin = new LoginRequestModel();
        userLogin.setUserName(login);
        userLogin.setPassword(password);

        return
                given(mainRequestSpec)
                        .body(userLogin)

                        .when()
                        .post("/Account/v1/Login")

                        .then()
                        .spec(mainResponseSpec)
                        .extract().as(LoginResponseModel.class);
    }
}
