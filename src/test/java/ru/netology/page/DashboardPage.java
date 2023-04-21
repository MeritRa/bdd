package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private static final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement dashboard = $("[data-test-id=dashboard]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        dashboard.shouldBe(Condition.visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        String text = cards.find(text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public CardTransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.find(text(cardInfo.getCardNumber().substring(15))).$("button").click();
        return new CardTransferPage();
    }
}