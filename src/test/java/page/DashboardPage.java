package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.Cards;
import data.DataHelper;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private ElementsCollection buttonsForTopUp = $$("[data-test-id='action-deposit']");
    private SelenideElement buttonForRefresh = $("[data-test-id=\"action-reload\"]");
    private ElementsCollection cards = $$(".list__item");
    private SelenideElement checkLoad = $(Selectors.byText("Ваши карты"));
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage(){
        checkLoad.should(Condition.appear);
    }

    /*private void getCardBalance(int[] balance){
        var count = 0;
        for (DataHelper.CardInfo cardInfo : cardList) {
            cardList.get(count).setBalance(balance[count]);
            count++;
        }
    }*/

    public ArrayList<DataHelper.CardInfo> getCardsBalance(ArrayList<DataHelper.CardInfo> cardList){
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

    //TODO
    public void topUpCard(int cardFromTransfer, String cardToTransfer, int sum){
        buttonsForTopUp.get(cardFromTransfer).click();
        TopUpPage moneyTransfer = new TopUpPage();
        moneyTransfer.topUpCard(Cards.values()[cardFromTransfer].getNumber(), cardToTransfer, sum);
        checkLoad.shouldBe(Condition.appear);

    }
}
