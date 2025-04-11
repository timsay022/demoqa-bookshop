package com.demoqa.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.restassured.RestAssured.baseURI;

public class TestBase {

    @BeforeAll
    static void setUp() {
        baseURI = "https://demoqa.com";
    }

    @AfterEach
    void shutDown() {
        closeWebDriver();
    }
}
