package com.demoqa.tests;

import com.demoqa.api.AuthorizationApi;
import com.demoqa.api.BooksApi;
import com.demoqa.helpers.WithLogin;
import com.demoqa.models.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.demoqa.tests.TestData.login;

public class AddBookToCollectionTests extends TestBase {
    BooksApi booksApi = new BooksApi();

    @Test
    @WithLogin
    void addBookToCollectionTest() {

        LoginResponseModel authResponse = new AuthorizationApi().login();

        booksApi.deleteAllBooksFromCollection(authResponse);

        String isbn = "9781449365035";
        String title = "Speaking JavaScript";
        booksApi.addBook(authResponse.getToken(), isbn, authResponse.getUserId());

        open("/profile");
        $("#userName-value").shouldHave(text(login));
        $("[id='see-book-"+title+"']").shouldBe(visible);

    }


}
