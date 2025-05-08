package com.demoqa.api;

import com.demoqa.models.AddBookRequestModel;
import com.demoqa.models.CollectionOfIsbns;
import com.demoqa.models.LoginResponseModel;
import com.demoqa.models.UserBooksResponseModel;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static com.demoqa.spec.MainSpec.*;
import static io.restassured.RestAssured.given;

public class BooksApiSteps {

    @Step("Удалить все книги из коллекции пользователя")
    public void deleteAllBooksFromCollection(LoginResponseModel authResponse) {
        given(mainRequestSpec)
                .header("Authorization", "Bearer " + authResponse.getToken())
                .queryParam("UserId", authResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(noContentResponseSpec);
    }

    @Step("Добавить книгу в коллекцию пользователя")
    public AddBookRequestModel addBook(String token, String isbn, String userId) {

        List<CollectionOfIsbns> books = new ArrayList<>();
        books.add(new CollectionOfIsbns(isbn));

        AddBookRequestModel bookData = new AddBookRequestModel();
        bookData.setUserId(userId);
        bookData.setCollectionOfIsbns(books);

        return
                given(mainRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(createdResponseSpec)
                .extract().as(AddBookRequestModel.class);
    }

    @Step("Открыть коллекцию книг пользователя")
    public UserBooksResponseModel getUserBooks(String token, String userId) {
        return
                given(mainRequestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .spec(mainResponseSpec)
                .extract().as(UserBooksResponseModel.class);
    }
}
