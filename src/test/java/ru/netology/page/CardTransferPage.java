package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class CardTransferPage {
    private final SelenideElement inputAmount = $("[data-test-id=amount] input");
    private final SelenideElement inputFrom = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement error = $("[data-test-id='error-notification']");

    public CardTransferPage() {
        transferButton.shouldBe(Condition.visible);
    }

    public DashboardPage makeTransfer(String amount, DataHelper.CardInfo cardInfo) {
        inputAmount.setValue(amount);
        inputFrom.setValue(cardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void transferError(String errorText) {
        error.shouldHave(Condition.exactText(errorText));
    }
}
