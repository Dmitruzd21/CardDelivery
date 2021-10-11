package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.exactText;


public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        //Открытие формы
        open("http://localhost:9999");
    }

    // Успешное заполнение формы
    @Test
    public void shouldOrderCardDelivery() {
        //Город
        $("[data-test-id=city] .input__control").setValue("Москва");
        //Дата встречи(текущая + 4 дня)
        LocalDate today = LocalDate.now();
        LocalDate dayOfMeeting = today.plusDays(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = dayOfMeeting.format(formatter);
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(formattedDate);
        //Фамилия и имя
        $("[data-test-id=name] .input__control").setValue("Иванов Сергей");
        //Телефон
        $("[data-test-id=phone] .input__control").setValue("+79154568735");
        //Чек-бокс
        $("div form fieldset label").click();
        //Забронировать
        $(Selectors.byText("Забронировать")).click();
        // Всплывающее окно успешно
        $(Selectors.withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        // Окно "Успешно" имеет текст "Встреча успешно забронирована на + дата"
        $("div.notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formattedDate));
    }

    // Ввод 2 букв в поле город, после чего выбор нужного города из выпадающего списка
    @Test
    public void shouldOrderCardDeliveryWithCityChoice() {
        //Город
        $("[data-test-id=city] .input__control").setValue("Ка");
        $(".popup_theme_alfa-on-white.input__popup").shouldBe(visible);
        $(".input__popup div div.popup__inner div div div:nth-child(5)").click();
        //Дата встречи(текущая + 4 дня)
        LocalDate today = LocalDate.now();
        LocalDate dayOfMeeting = today.plusDays(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        String formattedDate = dayOfMeeting.format(formatter);
        $("[data-test-id=date] .input__control").setValue(formattedDate);
        //Фамилия и имя
        $("[data-test-id=name] .input__control").setValue("Иванов Сергей");
        //Телефон
        $("[data-test-id=phone] .input__control").setValue("+79154568735");
        //Чек-бокс
        $("div form fieldset label").click();
        //Забронировать
        $(Selectors.byText("Забронировать")).click();
        // Всплывающее окно успешно
        $(Selectors.withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        // Окно "Успешно" имеет текст "Встреча успешно забронирована на + дата"
        $("div.notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formattedDate));
    }

}