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
        Generator.AuthInfo info = Generator.getCorrectAuthInfo();
        val verificationPage = loginPage.login(info.getLogin(), info.getPassword());
        verificationPage.validVerify(Generator.getVerificationCode().getCode());
    }

    @Test
    @Order(1)
    void transferFromSndToFstCard() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        Generator.CardInfo fstCard = Generator.getFstCard();
        Generator.CardInfo sndCard = Generator.getSndCard();
        val balanceOfFirstCard = dashboardPage.getCardBalance(fstCard.getId());
        val balanceOfSecondCard = dashboardPage.getCardBalance(sndCard.getId());

        val transferPage = dashboardPage.transferToCard(fstCard.getId());
        val sndCardNumber = sndCard.getNumber();
        transferPage.transfer(sndCardNumber, amount);

        val newBalanceOfFirstCard = dashboardPage.getCardBalance(fstCard.getId());
        val newBalanceOfSecondCard = dashboardPage.getCardBalance(sndCard.getId());

        assertEquals(balanceOfFirstCard + amount, newBalanceOfFirstCard);
        assertEquals(balanceOfSecondCard - amount, newBalanceOfSecondCard);
    }

    @Test
    @Order(2)
    void transferFromFstToSndCard() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        Generator.CardInfo fstCard = Generator.getFstCard();
        Generator.CardInfo sndCard = Generator.getSndCard();
        val balanceOfFirstCard = dashboardPage.getCardBalance(fstCard.getId());
        val balanceOfSecondCard = dashboardPage.getCardBalance(sndCard.getId());

        val transferPage = dashboardPage.transferToCard(sndCard.getId());
        val fstCardNumber = fstCard.getNumber();
        transferPage.transfer(fstCardNumber, amount);

        val newBalanceOfFirstCard = dashboardPage.getCardBalance(fstCard.getId());
        val newBalanceOfSecondCard = dashboardPage.getCardBalance(sndCard.getId());

        assertEquals(balanceOfFirstCard - amount, newBalanceOfFirstCard);
        assertEquals(balanceOfSecondCard + amount, newBalanceOfSecondCard);
    }

    @Test
    @Order(3)
    void transferFromIncorrectToSndCard() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        Generator.CardInfo sndCard = Generator.getSndCard();
        val transferPage = dashboardPage.transferToCard(sndCard.getId());
        val incorrectCardNumber = Generator.getFakeCard().getNumber();
        transferPage.transfer(incorrectCardNumber, amount);
        transferPage.invalidTransfer();
    }

    @Test
    @Order(4)
    void transferFromEmptyToSndCard() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        Generator.CardInfo sndCard = Generator.getSndCard();
        val transferPage = dashboardPage.transferToCard(sndCard.getId());
        val incorrectCardNumber = "";
        transferPage.transfer(incorrectCardNumber, amount);
        transferPage.invalidTransfer();
    }

    @Test
    @Order(5)
    void transferFromFstToSndCardTooMuch() {
        val dashboardPage = new DashboardPage();
        val amount = 20000;
        Generator.CardInfo fstCard = Generator.getFstCard();
        Generator.CardInfo sndCard = Generator.getSndCard();
        val transferPage = dashboardPage.transferToCard(sndCard.getId());
        val fstCardNumber = fstCard.getNumber();
        transferPage.transfer(fstCardNumber, amount);
        transferPage.invalidTransfer();
    }

    @Test
    @Order(6)
    void transferZeroFromFstToSndCard() {
        val dashboardPage = new DashboardPage();
        val amount = 0;
        Generator.CardInfo fstCard = Generator.getFstCard();
        Generator.CardInfo sndCard = Generator.getSndCard();
        val transferPage = dashboardPage.transferToCard(sndCard.getId());
        val fstCardNumber = fstCard.getNumber();
        transferPage.transfer(fstCardNumber, amount);
        transferPage.invalidTransfer();
    }
}
