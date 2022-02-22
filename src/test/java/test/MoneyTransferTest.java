package test;

import data.Cards;
import data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import java.sql.Array;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;


public class MoneyTransferTest {

    private int cardIDFromTransfer = 0; //card number starts from 0
    private int cardIDToTransfer = 1; //card number starts from 0
    private ArrayList<DataHelper.CardInfo> cardList;

    @Test
    public void shouldTransferBetweenTwoCards(){
        cardList = DataHelper.getCards();
        int sumForTransfer = 1000;
        open("http://localhost:9999/");
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verify = new LoginPage().validLogin(authInfo);
        DashboardPage dashboard = verify.validLogin();
        dashboard.getCardsBalance(cardList);
        Assertions.assertTrue((cardList.get(cardIDFromTransfer).getBalance() - sumForTransfer) > 0);
        //dashboard.topUpCard(cardIDFromTransfer, cardIDToTransfer, 1000);
    }

    @Test
    public void checkCardBalance(){
        cardList = DataHelper.getCards();
        open("http://localhost:9999/");
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verify = new LoginPage().validLogin(authInfo);
        DashboardPage dashboard = verify.validLogin();
        dashboard.getCardsBalance(cardList);
        int[] expectedCardsBalance = DataHelper.expectedCardsBalance();
        for (int i = 0; i < cardList.size() - 1; i++) {
            Assertions.assertEquals(expectedCardsBalance[i], cardList.get(i).getBalance());
        }
    }
}
