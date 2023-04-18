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
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        var LoginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var DashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneySuccessfullyTest() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = DashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = DashboardPage.getCardBalance(secondCardInfo);
        var amount = DataHelper.getValidAmount(firstCardInfo);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        CardTransferPage.makeTransfer(String.valueOf(amount), firstCardInfo.getCardNumber(), secondCardInfo.getCardNumber());
        var actualFirstCardBalance = DashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = DashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldGiveErrorTest() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = DashboardPage.getCardBalance(firstCardInfo);
        var amount = firstCardBalance + DataHelper.getValidAmount(firstCardInfo);
        CardTransferPage.makeTransfer(String.valueOf(amount), firstCardInfo.getCardNumber(), secondCardInfo.getCardNumber());
        CardTransferPage.transferError("Ошибка! Произошла ошибка");
    }
}
