package com.demoqa.tests;

import com.demoqa.models.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.demoqa.spec.MainSpec.*;
import static com.demoqa.tests.TestData.login;
import static com.demoqa.tests.TestData.password;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;

import java.util.List;

public class AddBookToCollectionTests extends TestBase {

    @Test
    void addBookToCollectionTest() {
        LoginRequestModel userLogin = new LoginRequestModel();
        userLogin.setUserName(login);
        userLogin.setPassword(password);

        LoginResponseModel authResponse = step("Авторизоваться в системе", ()-> given(mainRequestSpec)
                .body(userLogin)

                .when()
                .post("/Account/v1/Login")

                .then()
                .spec(mainResponseSpec)
                .extract().as(LoginResponseModel.class));

        step("Удалить все книги из корзины", ()-> given(mainRequestSpec)
                .header("Authorization", "Bearer " + authResponse.getToken())
                .queryParam("UserId", authResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(noContentResponseSpec));

        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.getUserId() , isbn);

        Response addBookResponse = step("Добавить книгу", ()-> given(mainRequestSpec)
                .header("Authorization", "Bearer " + authResponse.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(createdResponseSpec)
                .extract().response());

        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));


        open("/profile");
        $("#userName-value").shouldHave(text(login));

    }


}
