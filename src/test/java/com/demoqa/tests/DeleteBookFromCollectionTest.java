package com.demoqa.tests;

import com.demoqa.api.AuthorizationApiSteps;
import com.demoqa.api.BooksApiSteps;
import com.demoqa.helpers.WithLogin;
import com.demoqa.models.LoginResponseModel;
import com.demoqa.models.UserBooksResponseModel;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.demoqa.api.AuthorizationApiSteps.config;
import static com.demoqa.spec.MainSpec.mainRequestSpec;
import static com.demoqa.spec.MainSpec.mainResponseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBookFromCollectionTest extends TestBase{

    BooksApiSteps booksApi = new BooksApiSteps();

    @Test
    @WithLogin
    void deleteBookFromCollectionTest() {

        LoginResponseModel authResponse = new AuthorizationApiSteps().login();

        booksApi.deleteAllBooksFromCollection(authResponse);

        String isbn = "9781449365035";
        booksApi.addBook(authResponse.getToken(), isbn, authResponse.getUserId());

        step("Открыть список книг пользователя", ()-> {
            open("/profile");
            $("#userName-value").shouldHave(text(config.login()));
        });

        step("Удалить книгу из библиотеки", ()-> {
            $("#delete-record-undefined").click();
            $("#example-modal-sizes-title-sm").shouldHave(text("Delete Book"));
            $("#closeSmallModal-ok").click();
            sleep(1000);
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
