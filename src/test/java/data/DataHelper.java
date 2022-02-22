package data;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class DataHelper {

    private final static String CODE = "12345";

    private DataHelper(){}

    @Value
    public static class AuthInfo{
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode{
        private String code;

        private VerificationCode(String code) {
            this.code = code;
        }
    }

    public static class CardInfo {
        @Getter
        @Setter
        private String cardNumber;
        @Getter
        @Setter
        private int balance = -1;

        public CardInfo(String cardNumber){
            this.cardNumber = cardNumber;
        }

        @Override
        public String toString() {
            return "CardInfo{" +
                    "cardNumber='" + cardNumber + '\'' +
                    ", balance=" + balance +
                    '}';
        }
    }

    public static AuthInfo getAuthInfo(){return new AuthInfo(Users.USER1.getLogin(), Users.USER1.getPassword());}

    public static VerificationCode getVerificationCode(AuthInfo authInfo){return new VerificationCode(CODE);}

    public static ArrayList<CardInfo> getCards(){
        ArrayList<DataHelper.CardInfo> cardList = new ArrayList<>();
        Cards[] cards = Cards.values();
        for (Cards card : cards) {
            cardList.add(new DataHelper.CardInfo(card.getNumber()));
        }
        return cardList;
    }

    public static int[] expectedCardsBalance(){
        int[] expectedCardsBalance = new int[Cards.values().length];
        Cards[] cardList = Cards.values();
        for (int i = 0; i < cardList.length; i++) {
            expectedCardsBalance[i] = cardList[i].getBalance();
        }
        return expectedCardsBalance;
    }

    public static boolean validateCardBalance(ArrayList<DataHelper.CardInfo> cardList){
        int[] expectedCardsBalance = DataHelper.expectedCardsBalance();
        for (int i = 0; i < cardList.size() - 1; i++) {
            if (expectedCardsBalance[i] != cardList.get(i).getBalance()) return false;
        }
        return true;
    }

}
