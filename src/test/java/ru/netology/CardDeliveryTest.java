package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        //Открытие формы
        open("http://localhost:9999");
    }

    // метод генерации даты встречи в формате String (который принимает "+дней", формат даты)
    public String getDateOfMeetingInString(int days, String formatePattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(formatePattern));
    }

    // метод генерации даты встречи с типом LocalDate
    public LocalDate getDateOfMeetingInLocalDate(int days) {
        return LocalDate.now().plusDays(days);
    }

    // получение месяца встречи на русском языке
    public String getMonthOfMeetingInRussian(String monthStrInEnglish) {
        String[] engMonths = {
                "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String[] ruMonths = {
                "Январь ", "Февраль ", "Март ", "Апрель ", "Май ", "Июнь ",
                "Июль ", "Август ", "Сентябрь ", "Октябрь ", "Ноябрь ", "Декабрь "};
        for (
                int t = 0;
                t < engMonths.length; t++) {
            if (monthStrInEnglish.contains(engMonths[t])) {
                monthStrInEnglish = monthStrInEnglish.replace(engMonths[t], ruMonths[t]);
                break;
            }
        }
        String monthOfMeetingInRussian = monthStrInEnglish;
        return monthOfMeetingInRussian;
    }

    // Успешное заполнение формы
    @Test
    public void shouldOrderCardDelivery() {
        // Город (полный ввод)
        $("[data-test-id=city] .input__control").setValue("Москва");
        // Дата встречи(текущая + 4 дня)
        String formattedDateOfMeeting = getDateOfMeetingInString(4, "dd.MM.yyyy");
        // Очистить строку с датой
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        // Ввести дату (через клавиатуру)
        $("[data-test-id=date] .input__control").setValue(formattedDateOfMeeting);
        // Фамилия и имя
        $("[data-test-id=name] .input__control").setValue("Иванов Сергей");
        // Телефон
        $("[data-test-id=phone] .input__control").setValue("+79154568735");
        // Чек-бокс
        $("div form fieldset label").click();
        // Забронировать
        $(Selectors.byText("Забронировать")).click();
        // Всплывающее окно успешно
        $(Selectors.withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        // Окно "Успешно" имеет текст "Встреча успешно забронирована на + дата"
        $("div.notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formattedDateOfMeeting));
    }

    // Ввод 2 букв в поле город, после чего выбор нужного города из выпадающего списка, а также выбор даты через календарь
    @Test
    public void shouldOrderCardDeliveryWithCityChoiceAndManualChoiceOfDay() {
        // Выбор города из списка при введении 2х букв
        $("[data-test-id=city] .input__control").setValue("Ка");
        $(".popup_theme_alfa-on-white.input__popup").shouldBe(visible);
        $(Selectors.byText("Казань")).click();
        // Дата встречи (текущая + 4 дня)
        String formattedDateOfMeeting = getDateOfMeetingInString(4, "dd.MM.yyyy");
        // Получение дня встречи и перевод в String
        Integer dayOfMeeting = getDateOfMeetingInLocalDate(4).getDayOfMonth();
        String dayOfMeetingStr = String.valueOf(dayOfMeeting);
        // Получение месяца встречи и перевод в String
        Month monthOfMeeting = getDateOfMeetingInLocalDate(4).getMonth();
        String monthStr = monthOfMeeting.toString();
        // Перевод месяца встречи на руссккий язык
        String monthStrInRussian = getMonthOfMeetingInRussian(monthStr);
        // Получение года встречи и перевод в String
        Integer year = getDateOfMeetingInLocalDate(4).getYear();
        String yearStr = String.valueOf(year);
        // Нажать на календарь
        $("[data-test-id=date]").click();
        // Убедиться, что календарь виден и предлагает текущий месяц
        $(".calendar__name").shouldBe(visible).shouldHave(exactText(monthStrInRussian + yearStr));
        // Кликнуть конкретный день
        Month currentMonth = LocalDate.now().getMonth();
        if (currentMonth == monthOfMeeting) {
            $$(".calendar__day").findBy(text(dayOfMeetingStr)).click();
        } else {
            $(".calendar__arrow_direction_right [data-step=1]").click();
            $$(".calendar__day").findBy(text(dayOfMeetingStr)).click();
        }
        // Фамилия и имя
        $("[data-test-id=name] .input__control").setValue("Иванов Сергей");
        // Телефон
        $("[data-test-id=phone] .input__control").setValue("+79154568735");
        // Чек-бокс
        $("div form fieldset label").click();
        // Забронировать
        $(Selectors.byText("Забронировать")).click();
        // Всплывающее окно успешно
        $(Selectors.withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        // Окно "Успешно" имеет текст "Встреча успешно забронирована на + дата"
        $("div.notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formattedDateOfMeeting));
    }
}

// Дата встречи (текущая + 4 дня в формате String)
// LocalDate today = LocalDate.now();
// LocalDate dateOfMeeting = today.plusDays(4);
// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
// String formattedDateOfMeeting = dateOfMeeting.format(formatter);