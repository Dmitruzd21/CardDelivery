package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.ExactText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        $ ("").setValue("");
        //Дата (текущая + 3 дня)
        $ ("").setValue("");
        //Фамилия и имя
        $ ("").setValue("");
        //Телефон
        $ ("").setValue("");
        //Чек-бокс
        $ ("").click();
        //Забронировать
        $ ("").click();
        // Всплывающее окно успешно
        $ ("").shouldHave(exactText(""));
    }
}
