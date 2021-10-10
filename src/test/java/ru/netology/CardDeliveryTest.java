package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

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
        $("[data-test-id=date]").setValue("13.10.2021");
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