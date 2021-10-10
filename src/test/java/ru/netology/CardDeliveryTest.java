package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    private void shouldOrderCardDelivery() {
        //Открытие формы
        open("http://localhost:9999");
        //Город
        $("[data-test-id=city]").setValue("Москва");
        //Дата (текущая + 3 дня)
        LocalDate today = LocalDate.now();
        LocalDate todayPlusThreeDays = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        String formattedDate = todayPlusThreeDays.format(formatter);
        $("[data-test-id=date]").setValue(formattedDate);
        //Фамилия и имя
        $("[data-test-id=name]").setValue("Иванов Сергей");
        //Телефон
        $("[data-test-id=phone]").setValue("+791545687354");
        //Чек-бокс
        $(".checkbox__control").click();
        //Забронировать
        $(Selectors.byText("Забронировать")).click();
        // Всплывающее окно успешно
        $(Selectors.withText("Успешно")).shouldBe (visible, Duration.ofSeconds(15));
                //(exactText("Встреча успешно забронирована на" + ""));
    }
}