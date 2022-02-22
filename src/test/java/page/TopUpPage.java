package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TopUpPage {

    private SelenideElement sum = $("[data-test-id='amount'] .input__control");
    private SelenideElement cardFrom = $("[data-test-id=\"from\"] [type=\"tel\"]");
    private SelenideElement cardTo = $("[data-test-id=\"to\"] [type=\"text\"]");
    private SelenideElement transferButton = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement cancelButton = $("[data-test-id=\"action-cancel\"]");

    public TopUpPage(){
        this.sum.shouldBe(Condition.visible);
    }

    public void topUpCard(DataHelper.CardInfo cardFrom, DataHelper.CardInfo cardTo, int sum){
    }
}
