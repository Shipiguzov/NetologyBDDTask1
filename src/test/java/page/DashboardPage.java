package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private ElementsCollection buttonsForTopUp = $$("[data-test-id='action-deposit']");
    private SelenideElement buttonForRefresh = $("[data-test-id=\"action-reload\"]");
    private ElementsCollection cards = $$(".list__item");
    private SelenideElement loadCheck = $(Selectors.byText("Ваши карты"));
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        testPageRefresh();
    }

    public ArrayList<DataHelper.CardInfo> getCardsBalance(ArrayList<DataHelper.CardInfo> cardList) {
        int count = 0;
        for (SelenideElement element : cards) {
            var balanceStart = element.getText().indexOf(this.balanceStart);
            var balanceFinish = element.getText().indexOf(this.balanceFinish);
            cardList.get(count).setBalance(Integer.parseInt(cards
                    .get(count)
                    .getText()
                    .substring(balanceStart + this.balanceStart.length(), balanceFinish)));
            count++;
        }
        return cardList;
    }

    public void topUpCard(int cardToTransfer, String cardFromTransfer, int sum) {
        buttonsForTopUp.get(cardToTransfer).click();
        TopUpPage moneyTransfer = new TopUpPage();
        moneyTransfer.topUpCard(cardFromTransfer, sum);
        loadCheck.shouldBe(Condition.appear, Duration.ofSeconds(15));
    }

    public void cancelTransaction(int cardToTransfer, String cardFromTransfer, int sum) {
        buttonsForTopUp.get(cardToTransfer).click();
        TopUpPage moneyTransfer = new TopUpPage();
        moneyTransfer.fillCardInfoAndCancel(cardFromTransfer, sum);
        loadCheck.shouldBe(Condition.appear, Duration.ofSeconds(15));
    }

    public void topUpInvalidCard(int cardToTransfer, String cardFromTransfer, int sum) {
        buttonsForTopUp.get(cardToTransfer).click();
        TopUpPage moneyTransfer = new TopUpPage();
        moneyTransfer.topUpInvalidCard(cardFromTransfer, sum);
    }

    public void refreshButtonClick(){
        buttonForRefresh.click();
    }

    public void testPageRefresh(){
        loadCheck.should(Condition.appear);
    }
}
