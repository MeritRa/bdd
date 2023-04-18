package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CardTransferPage {
    private static final SelenideElement inputAmount = $("[data-test-id=amount] input");
    private static final SelenideElement inputFrom = $("[data-test-id=from] input");
    private static final SelenideElement inputTo = $("[data-test-id=to] input");
    private static final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private static final SelenideElement error = $("[data-test-id='error-notification']");

    public CardTransferPage() {
        transferButton.shouldBe(Condition.visible);
    }

    public static DashboardPage makeTransfer(String amount, String transferFrom, String transferTo) {
        inputAmount.setValue(amount);
        inputFrom.setValue(transferFrom);
        inputTo.setValue(transferTo);
        transferButton.click();
        return new DashboardPage();
    }

    public static void transferError(String errorText) {
        error.shouldHave(Condition.exactText(errorText));
    }

    public DashboardPage cancelTransfer() {
        cancelButton.click();
        return new DashboardPage();
    }
}
