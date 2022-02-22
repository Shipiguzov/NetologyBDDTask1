package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private String code = "12345";
    private SelenideElement codeField = $(".Verification_verificationForm__yQSMa [name='code']");
    private SelenideElement button = $(Selectors.byClassName("button"));

    public VerificationPage(){
        codeField.shouldBe(Condition.visible);
    }

    public DashboardPage validLogin(){
        codeField.setValue(code);
        button.click();
        return new DashboardPage();
    }

}
