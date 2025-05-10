package com.demoqa.config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;

public class WebDriverConfig {
    private final WebConfig webConfig;

    public WebDriverConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }

    public void webConfig() {

        Configuration.baseUrl = webConfig.getBaseUrl();
        RestAssured.baseURI = webConfig.getBaseUrl();
        Configuration.browser = webConfig.getBrowserName();
        Configuration.browserVersion = webConfig.getBrowserVersion();
        Configuration.pageLoadStrategy = "eager";
        if (webConfig.getRemote()) {
            Configuration.remote = webConfig.getRemoteUrl();
        }
    }
}
