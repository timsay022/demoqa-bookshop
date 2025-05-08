package com.demoqa.tests;

import com.demoqa.config.ConfigReader;
import com.demoqa.config.WebConfig;
import com.demoqa.config.WebDriverConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.*;

public class TestBase {

    private static final WebConfig webConfig = ConfigReader.Instance.read();


    @BeforeAll
    static void setUp() {
        WebDriverConfig webDriverConfig = new WebDriverConfig(webConfig);
        webDriverConfig.webConfig();
    }

    @AfterEach
    void shutDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        closeWebDriver();
    }
}
