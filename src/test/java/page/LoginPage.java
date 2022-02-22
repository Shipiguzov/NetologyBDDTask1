package page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class Login {

    private SelenideElement loginField = $(".input [name='login']");
    private SelenideElement passwordField = $(".input__inner [name='password']");
    private SelenideElement button = $(Selectors.byClassName("button"));

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getPassword());
        passwordField.setValue(authInfo.getPassword());
        button.click();
        return new VerificationPage();
    }
}
