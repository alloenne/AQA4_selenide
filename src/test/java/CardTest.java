
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    void shouldDeliverySuccessManually() {
        int addDays = 3;
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, addDays);
        String currentDatePlus = dateFormat.format(c.getTime());

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        $("[data-test-id=date] input").setValue(currentDatePlus);
        $("[data-test-id=city] input").setValue("Астрахань");
        $("[data-test-id=name] input").setValue("Имярек Имяреков");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldDeliverySuccessFromListV1() {

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

    }

    @Test
    void shouldDeliverySuccessFromListV2() {
        int addDays = 7;
        LocalDate currentDate = LocalDate.now();
        int currentDays = currentDate.getDayOfMonth();
        int currentMonthLength = currentDate.lengthOfMonth();

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $(".icon_name_calendar").click();
        if (currentDays + addDays > currentMonthLength) {
            $$("[data-step]").get(3).click();
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

    }
}
