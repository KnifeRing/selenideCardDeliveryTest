package ru.netology;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    public String setDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void successfulBid() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Екатеринбург");
        String actualDate = setDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(actualDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79437654312");
        $("[data-test-id='agreement']").click();
        $(".button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + actualDate));
    }
}