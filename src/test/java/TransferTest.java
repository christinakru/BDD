import lombok.val;
import org.junit.jupiter.api.*;
import pages.DashboardPage;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransferTest {
    @BeforeEach
    void login() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val verificationPage = loginPage.login(Generator.getCorrectLogin(), Generator.getCorrectPassword());
        verificationPage.validVerify(Generator.getCorrectVerCode());
    }

    @Test
    @Order(1)
    void transferFromSndToFstCard() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val balanceOfFirstCard = dashboardPage.getCardBalance(Generator.getFstCardId());
        val balanceOfSecondCard = dashboardPage.getCardBalance(Generator.getSndCardId());

        val transferPage = dashboardPage.transferToCard(Generator.getFstCardId());
        val sndCardNumber = Generator.getSndCardNumber();
        transferPage.transfer(sndCardNumber, amount);

        val newBalanceOfFirstCard = dashboardPage.getCardBalance(Generator.getFstCardId());
        val newBalanceOfSecondCard = dashboardPage.getCardBalance(Generator.getSndCardId());

        assertEquals(balanceOfFirstCard + amount, newBalanceOfFirstCard);
        assertEquals(balanceOfSecondCard - amount, newBalanceOfSecondCard);
    }

    @Test
    @Order(2)
    void transferFromFstToSndCard() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val balanceOfFirstCard = dashboardPage.getCardBalance(Generator.getFstCardId());
        val balanceOfSecondCard = dashboardPage.getCardBalance(Generator.getSndCardId());

        val transferPage = dashboardPage.transferToCard(Generator.getSndCardId());
        val fstCardNumber = Generator.getFstCardNumber();
        transferPage.transfer(fstCardNumber, amount);

        val newBalanceOfFirstCard = dashboardPage.getCardBalance(Generator.getFstCardId());
        val newBalanceOfSecondCard = dashboardPage.getCardBalance(Generator.getSndCardId());

        assertEquals(balanceOfFirstCard - amount, newBalanceOfFirstCard);
        assertEquals(balanceOfSecondCard + amount, newBalanceOfSecondCard);
    }

    @Test
    @Order(3)
    void transferFromIncorrectToSndCard() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;

        val transferPage = dashboardPage.transferToCard(Generator.getSndCardId());
        val incorrectCardNumber = Generator.getIncorrectCardNumber();
        transferPage.transfer(incorrectCardNumber, amount);
        transferPage.invalidTransfer();
    }

    @Test
    @Order(4)
    void transferFromEmptyToSndCard() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;

        val transferPage = dashboardPage.transferToCard(Generator.getSndCardId());
        val incorrectCardNumber = "";
        transferPage.transfer(incorrectCardNumber, amount);
        transferPage.invalidTransfer();
    }

    @Test
    @Order(5)
    void transferFromFstToSndCardTooMuch() {
        val dashboardPage = new DashboardPage();
        val amount = 20000;

        val transferPage = dashboardPage.transferToCard(Generator.getSndCardId());
        val fstCardNumber = Generator.getFstCardNumber();
        transferPage.transfer(fstCardNumber, amount);
        transferPage.invalidTransfer();
    }

    @Test
    @Order(6)
    void transferZeroFromFstToSndCard() {
        val dashboardPage = new DashboardPage();
        val amount = 0;

        val transferPage = dashboardPage.transferToCard(Generator.getSndCardId());
        val fstCardNumber = Generator.getFstCardNumber();
        transferPage.transfer(fstCardNumber, amount);
        transferPage.invalidTransfer();
    }
}
