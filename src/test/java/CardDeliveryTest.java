import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @Test
    void successfulCardDeliveryOrder() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("кр");
        $(byText("Краснодар")).click();
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(date.format(formatter));
        $("[name='name']").setValue("Прекрасная Селенида");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date.format(formatter)));
    }
}
