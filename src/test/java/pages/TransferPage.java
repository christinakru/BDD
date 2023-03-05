package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement amountInput = $("[data-test-id = amount] input");
    private final SelenideElement fromInput = $("[data-test-id = from] input");
    private final SelenideElement transferButton = $("[data-test-id = action-transfer]");
    private final SelenideElement error = $("[data-test-id = error-notification]");

    public void transfer(String cardNum, int amount) {
        amountInput.setValue(String.valueOf(amount));
        fromInput.setValue(cardNum);
        transferButton.click();
        new DashboardPage();
    }

    public void invalidTransfer() {
        error.shouldBe(visible);
    }
}
