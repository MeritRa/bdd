package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardTransferPage;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardInfo;
import static ru.netology.data.DataHelper.getSecondCardInfo;

public class CardTransferTest {
    DashboardPage dashboardPage;
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneySuccessfullyTest() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = DataHelper.getValidAmount(10000);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeTransfer(String.valueOf(amount), firstCardInfo);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldGiveErrorTest() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var amount = firstCardBalance + DataHelper.getValidAmount(10000);
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), firstCardInfo);
        transferPage.transferError("На балансе карты недостаточно средств");
    }
}
