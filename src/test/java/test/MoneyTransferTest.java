package test;

import data.Cards;
import data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void resetSUT(){
        //Check balance of first card and add or transfer money from/to second card
    }

    @Test
    public void shouldTransferBetweenTwoCards(){
        cardList = DataHelper.getCards();
        int sumForTransfer = 1000;
        open("http://localhost:9999/");
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verify = new LoginPage().validLogin(authInfo);
        DashboardPage dashboard = verify.validLogin();
        dashboard.getCardsBalance(cardList);
        Assertions.assertTrue((cardList.get(cardIDFromTransfer).getBalance() - sumForTransfer) > 0,
                "Not enough money for transfer");
        dashboard.topUpCard(cardIDFromTransfer, cardList.get(cardIDToTransfer).getCardNumber(), 1000);
    }

    @Test
    public void checkCardBalance(){
        cardList = DataHelper.getCards();
        open("http://localhost:9999/");
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verify = new LoginPage().validLogin(authInfo);
        DashboardPage dashboard = verify.validLogin();
        dashboard.getCardsBalance(cardList);
        Assertions.assertTrue(DataHelper.validateCardBalance(cardList));
    }

    @Test
    public void notEnoughMoney(){
        cardList = DataHelper.getCards();
        int sumForTransfer = 100000;
        open("http://localhost:9999/");
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verify = new LoginPage().validLogin(authInfo);
        DashboardPage dashboard = verify.validLogin();
        dashboard.getCardsBalance(cardList);
        Assertions.assertTrue((cardList.get(cardIDFromTransfer).getBalance() - sumForTransfer) > 0,
                "Not enough money for transfer\n");
        dashboard.topUpCard(cardIDFromTransfer, cardList.get(cardIDToTransfer).getCardNumber(), 1000);
    }
}
