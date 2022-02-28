package test;

import data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;


public class MoneyTransferTest {

    private final int CARDIDFROMTRANSFER = 1; //card number starts from 0
    private final int CARDIDTOTRANSFER = 0; //card number starts from 0
    private final String invalidCardNumber = "0000 0000 0000 0000";
    private ArrayList<DataHelper.CardInfo> cardList; //use in tests

    @Test
    public void checkCardBalance() {
        open("http://localhost:9999/");
        cardList = DataHelper.getCards();
        DashboardPage dashboard = login();
        dashboard.getCardsBalance(cardList);
        Assertions.assertTrue(DataHelper.validateCardBalance(cardList));
        cardList.clear();
    }

    @Test
    public void cancelTransaction(){
        open("http://localhost:9999/");
        cardList = DataHelper.getCards();
        DashboardPage dashboard = login();
        //resetCardBalance(dashboard);
        dashboard.getCardsBalance(cardList);
        int sumForTransfer = 1000;
        int expectedFromCardBalance = cardList.get(CARDIDFROMTRANSFER).getBalance();
        int expectedToCardBalance = cardList.get(CARDIDTOTRANSFER).getBalance();
        dashboard.cancelTransaction(CARDIDTOTRANSFER, cardList.get(CARDIDFROMTRANSFER).getCardNumber(), sumForTransfer);
        dashboard.getCardsBalance(cardList);
        int actualFromCardBalance = cardList.get(CARDIDFROMTRANSFER).getBalance();
        int actualToCardBalance = cardList.get(CARDIDTOTRANSFER).getBalance();
        Assertions.assertEquals(expectedFromCardBalance, actualFromCardBalance);
        Assertions.assertEquals(expectedToCardBalance, actualToCardBalance);
        cardList.clear();
    }

    @Test
    public void transferOnSameCard(){
        open("http://localhost:9999/");
        DashboardPage dashboard = login();
        cardList = DataHelper.getCards();
        //resetCardBalance(dashboard);
        dashboard.getCardsBalance(cardList);
        int sumToTransfer = 1000;
        int expectedResult = cardList.get(CARDIDTOTRANSFER).getBalance();
        dashboard.topUpCard(CARDIDTOTRANSFER, cardList.get(CARDIDTOTRANSFER).getCardNumber(), sumToTransfer);
        dashboard.getCardsBalance(cardList);
        Assertions.assertEquals(expectedResult, cardList.get(CARDIDTOTRANSFER).getBalance());
        cardList.clear();
    }

    @Test
    public void testRefreshButton(){
        open("http://localhost:9999/");
        cardList = DataHelper.getCards();
        DashboardPage dashboard = login();
        dashboard.refreshButtonClick();
        dashboard.testPageRefresh();
    }

    @Test
    public void transferToInvalidCard(){
        open("http://localhost:9999/");
        cardList = DataHelper.getCards();
        DashboardPage dashboard = login();
        //resetCardBalance(dashboard);
        int sumToTransfer = 1000;
        dashboard.topUpInvalidCard(CARDIDTOTRANSFER, invalidCardNumber, sumToTransfer);
        cardList.clear();
    }

    @Test
    public void transferNegativeSum(){
        open("http://localhost:9999/");
        cardList = DataHelper.getCards();
        DashboardPage dashboard = login();
        //resetCardBalance(dashboard);
        dashboard.getCardsBalance(cardList);
        int sumForTransfer = -1000;
        int expectedFromCardBalance = cardList.get(CARDIDFROMTRANSFER).getBalance() - Math.abs(sumForTransfer);
        int expectedToCardBalance = cardList.get(CARDIDTOTRANSFER).getBalance() + Math.abs(sumForTransfer);
        dashboard.topUpCard(CARDIDTOTRANSFER, cardList.get(CARDIDFROMTRANSFER).getCardNumber(), sumForTransfer);
        dashboard.getCardsBalance(cardList);
        int actualFromCardBalance = cardList.get(CARDIDFROMTRANSFER).getBalance();
        int actualToCardBalance = cardList.get(CARDIDTOTRANSFER).getBalance();
        Assertions.assertEquals(expectedFromCardBalance, actualFromCardBalance);
        Assertions.assertEquals(expectedToCardBalance, actualToCardBalance);
        cardList.clear();
    }

    @Test
    public void shouldTransferBetweenTwoCards() {
        open("http://localhost:9999/");
        DashboardPage dashboard = login();
        cardList = DataHelper.getCards();
        //resetCardBalance(dashboard);
        dashboard.getCardsBalance(cardList);
        int sumForTransfer = 1000;
        int expectedFromCardBalance = cardList.get(CARDIDFROMTRANSFER).getBalance() - sumForTransfer;
        int expectedToCardBalance = cardList.get(CARDIDTOTRANSFER).getBalance() + sumForTransfer;
        dashboard.topUpCard(CARDIDTOTRANSFER, cardList.get(CARDIDFROMTRANSFER).getCardNumber(), sumForTransfer);
        dashboard.getCardsBalance(cardList);
        int actualFromCardBalance = cardList.get(CARDIDFROMTRANSFER).getBalance();
        int actualToCardBalance = cardList.get(CARDIDTOTRANSFER).getBalance();
        Assertions.assertEquals(expectedFromCardBalance, actualFromCardBalance);
        Assertions.assertEquals(expectedToCardBalance, actualToCardBalance);
        cardList.clear();
    }

    @Test
    public void notEnoughMoney() {
        open("http://localhost:9999/");
        cardList = DataHelper.getCards();
        DashboardPage dashboard = login();
        //resetCardBalance(dashboard);
        cardList = DataHelper.getCards();
        int sumForTransfer = 100000;
        dashboard.topUpCard(CARDIDFROMTRANSFER, cardList.get(CARDIDTOTRANSFER).getCardNumber(), sumForTransfer);
        dashboard.getCardsBalance(cardList);
        for (DataHelper.CardInfo cardInfo : cardList) {
            Assertions.assertTrue(cardInfo.getBalance() >0);
        }
        cardList.clear();
    }

    private DashboardPage login(){
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verify = new LoginPage().validLogin(authInfo);
        return verify.validLogin();
    }

    private void resetCardBalance(DashboardPage dashboard) {
        cardList = DataHelper.getCards();
        dashboard.getCardsBalance(cardList);
        if (cardList.get(0).getBalance() == 10_000) return;
        if (cardList.get(0).getBalance() > 10_000) {
            dashboard.topUpCard(1, cardList.get(0).getCardNumber(), cardList.get(0).getBalance() - 10_000);
            return;
        }
        if (cardList.get(1).getBalance() > 10_000) {
            dashboard.topUpCard(0, cardList.get(1).getCardNumber(), cardList.get(1).getBalance() - 10_000);
            return;
        }
    }
}
