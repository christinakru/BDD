package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public void validVerify(String verCode) {
        codeField.setValue(verCode);
        verifyButton.click();
        new DashboardPage();
    }
}
