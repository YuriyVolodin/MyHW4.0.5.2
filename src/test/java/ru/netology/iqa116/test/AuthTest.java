package ru.netology.iqa116.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import ru.netology.iqa116.data.DataGenerator;

import static ru.netology.iqa116.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.iqa116.data.DataGenerator.Registration.getUser;
import static ru.netology.iqa116.data.DataGenerator.getRandomLogin;
import static ru.netology.iqa116.data.DataGenerator.getRandomPassword;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @Test
    @DisplayName("Should get error if not registered user tries to login")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"),
                        Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }
}
