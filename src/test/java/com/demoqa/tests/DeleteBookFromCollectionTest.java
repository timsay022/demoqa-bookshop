package com.demoqa.tests;

import com.demoqa.models.LoginRequestModel;
import com.demoqa.models.LoginResponseModel;
import com.demoqa.models.UserBooksResponseModel;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.demoqa.spec.MainSpec.*;
import static com.demoqa.tests.TestData.login;
import static com.demoqa.tests.TestData.password;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBookFromCollectionTest extends TestBase{

    @Test
    void deleteBookFromCollectionTest() {
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

        step("Удалить все книги из библиотеки", ()-> given(mainRequestSpec)
                .header("Authorization", "Bearer " + authResponse.getToken())
                .queryParam("UserId", authResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(noContentResponseSpec));

        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.getUserId() , isbn);

        Response addBookResponse = step("Добавить книгу в библиотеку", ()-> given(mainRequestSpec)
                .header("Authorization", "Bearer " + authResponse.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(createdResponseSpec)
                .extract().response());


        step("Заполнить Cookie аунтефикационными данными", ()-> {
        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        });

        step("Открыть список книг пользователя", ()-> {
            open("/profile");
            $("#userName-value").shouldHave(text(login));
        });

        step("Удалить книгу из библиотеки", ()-> {
            $("#delete-record-undefined").click();
            $("#example-modal-sizes-title-sm").shouldHave(text("Delete Book"));
            $("#closeSmallModal-ok").click();
        });

        step("Проверить отсутствия книг в библиотеке пользователя", ()-> {
            UserBooksResponseModel response = given(mainRequestSpec)
                    .header("Authorization", "Bearer " + authResponse.getToken())
                    .when()
                    .get("/Account/v1/User/" + authResponse.getUserId())
                    .then()
                    .spec(mainResponseSpec)
                    .extract().as(UserBooksResponseModel.class);

            assertThat(response.getBooks()).isEmpty();
        });

    }
}
