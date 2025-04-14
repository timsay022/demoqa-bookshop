package com.demoqa.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.baseURI;

public class TestBase {

    @BeforeAll
    static void setUp() {
        baseURI = "https://demoqa.com";
        baseUrl = "https://demoqa.com";
        pageLoadStrategy = "eager";
        browserSize = "1920x1080";
    }

    @AfterEach
    void shutDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        closeWebDriver();
    }
}
