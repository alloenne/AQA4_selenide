
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void shouldDeliverySuccessManually() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=city] input").setValue("Астрахань");
        $("[data-test-id=name] input").setValue("Имярек Имяреков");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    @Test
    void shouldDeliverySuccessFromListV1() {
        String planningDate = generateDate(7, "dd.MM.yyyy");

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $(".icon_name_calendar").click();
        $("[data-test-id=date] input").sendKeys(Keys.DOWN, Keys.RIGHT, Keys.RIGHT, Keys.RIGHT, Keys.RIGHT);
        $("[data-test-id=city] input").sendKeys("а" + "с" + Keys.DOWN + Keys.ENTER);
        $("[data-test-id=name] input").setValue("Имярек Имяреков");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(visible);

    }

    @Test
    void shouldDeliverySuccessFromListV2SameMonth() {
        int addDays = 7;
        LocalDate currentDate = LocalDate.now();
        int currentDays = currentDate.getDayOfMonth();
        int currentMonthLength = currentDate.lengthOfMonth();
        String planningDate = generateDate(addDays, "dd.MM.yyyy");

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $(".icon_name_calendar").click();
        if (currentDays + addDays > currentMonthLength) {
            $("[data-step='1']").click();
            int deliveryDays = currentDays + addDays - currentMonthLength;
            $x("//td[contains(text()," + deliveryDays + ")]").click();
        } else {
            int deliveryDays = currentDays + addDays;
            $x("//td[contains(text()," + deliveryDays + ")]").click();
        }
        $("[data-test-id=city] input").sendKeys("а" + "с" + Keys.DOWN + Keys.ENTER);
        $("[data-test-id=name] input").setValue("Имярек Имяреков");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(visible);

    }

    @Test
    void shouldDeliverySuccessFromListV2NextMonth() {
        int addDays = 7;
        LocalDate currentDate = LocalDate.of(2023, 03, 29);
        int currentDays = currentDate.getDayOfMonth();
        int currentMonthLength = currentDate.lengthOfMonth();
        String planningDate = currentDate.plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $(".icon_name_calendar").click();
        if (currentDays + addDays > currentMonthLength) {
            $("[data-step='1']").click();
            int deliveryDays = currentDays + addDays - currentMonthLength;
            $x("//td[contains(text()," + deliveryDays + ")]").click();
        } else {
            int deliveryDays = currentDays + addDays;
            $x("//td[contains(text()," + deliveryDays + ")]").click();
        }
        $("[data-test-id=city] input").sendKeys("а" + "с" + Keys.DOWN + Keys.ENTER);
        $("[data-test-id=name] input").setValue("Имярек Имяреков");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }
}
