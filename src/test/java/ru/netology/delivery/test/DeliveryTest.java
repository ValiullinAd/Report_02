package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.*;
import static ru.netology.delivery.data.DataGenerator.*;
import static ru.netology.delivery.data.DataGenerator.Generate.generateUser;

public class DeliveryTest {

    @BeforeEach
    void setUp() {

        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1600x900";
        Configuration.browser = "Chrome";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll (){
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var user = generateUser();
        var daysToAddForFirstMeeting = 3;
        var firstDate = generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 5;
        var secondDate = generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstDate);
        $(byName("name")).setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $(".checkbox__box").click();
        $(byText("Запланировать")).click();
        //$(".button").click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstDate));
        $("[data-test-id='success-notification'] .icon_name_close").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(SHIFT, HOME, DELETE));
        $("[data-test-id=date] input").setValue(secondDate);
        $(".button").click();
        $("[data-test-id=replan-notification] .button").click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + secondDate));
    }
}