package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class TopUpPage {

    private SelenideElement sum = $("[data-test-id='amount'] .input__control");
    private SelenideElement cardFrom = $("[data-test-id=\"from\"] [type=\"tel\"]");
    private SelenideElement errorPopUpWindow = $("[data-test-id='error-notification']");
    private SelenideElement transferButton = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement cancelButton = $("[data-test-id=\"action-cancel\"]");

    public TopUpPage(){
        this.sum.shouldBe(Condition.visible);
    }

    public void topUpCard(String cardFromTransfer, int sum){
        clearFields();
        this.sum.setValue(String.valueOf(sum));
        cardFrom.setValue(cardFromTransfer);
        transferButton.click();
    }

    public void fillCardInfoAndCancel(String cardFromTransfer, int sum){
        this.sum.setValue(String.valueOf(sum));
        cardFrom.setValue(cardFromTransfer);
        cancelButton.click();
    }

    public void topUpInvalidCard(String cardFromTransfer, int sum){
        clearFields();
        this.sum.setValue(String.valueOf(sum));
        cardFrom.setValue(cardFromTransfer);
        transferButton.click();
        errorPopUpWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    private void clearFields(){
        this.sum.clear();
        cardFrom.clear();
    }
}
