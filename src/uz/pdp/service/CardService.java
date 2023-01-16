package uz.pdp.service;

import uz.pdp.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardService implements BaseService<Card> {


    private double cashBackPercentage = 0;
    private double CommissionPercentage = 0;
    private double ownerIncome = 0;

    public double getOwnerIncome() {
        return this.ownerIncome;
    }

    public void setOwnerIncome(double ownerIncome) {
        this.ownerIncome = ownerIncome;
    }

    public double getCashBackPercentage() {
        return cashBackPercentage;
    }

    public void setCashBackPercentage(double cashBackPercentage) {
        this.cashBackPercentage = cashBackPercentage;
    }

    public double getCommissionPercentage() {
        return CommissionPercentage;
    }

    public void setCommissionPercentage(double commissionPercentage) {
        this.CommissionPercentage = commissionPercentage;
    }

    @Override
    public boolean add(Card card) {
        if(card!=null) {
            this.cards.add(card);
        }
        return true;
    }

    @Override
    public void getList() {
        for (Card card : this.cards) {
            System.out.println("Name: "+card.getName()+"\tcard number :" +card.getCardNumber()+"\tcard balance: "+card.getBalance()+"\tPhone number :"+card.getPhoneNumber());

        }
    }

    @Override
    public void getListByUserId(UUID id) {
        System.out.println("|cardName |cardNumber | phoneNumber |expiryDate |cardPassword | balance");
        for (Card card : this.cards) {
            if (card.getUserId() == id) {
                System.out.println(card);
            }
        }
    }

    public boolean editPassword(String cardNumber, String pass) {
        int i = 0;
        for (Card card : this.cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                this.cards.get(i).setCardPassword(pass);
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean fillBalance(String cardNumber, double amount) {
        int i = 0;
        for (Card card : this.cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                this.cards.get(i).setBalance(amount);
                return true;
            }
            i++;
        }
        return false;
    }

    public Card checkCardHolder(UUID userId, String cardNumber) {
        for (Card card : this.cards) {
            if (card.getCardNumber().equals(cardNumber))
                return card;
        }
        return null;
    }

    public boolean checkCardNumber(UUID userId, String cardNumber){
        for (Card card : this.cards) {
            if (card.getCardNumber().equals(cardNumber))
                return true;
        }
        return false;
    }

    public void withdrawMoney(UUID cardId, double amount) {
        int ind = 0;
        for (Card card : this.cards) {
            if (card.getId() == cardId) {
                this.cards.get(ind).setBalance(this.cards.get(ind).getBalance() - amount);
            }
            ind++;
        }
    }

    public int p2p(Card fromCardNumber, String toCardNumber, double amount) {
        double amountWithCommission = amount * (1 +
                (getCommissionPercentage()));
        setOwnerIncome(getOwnerIncome() + amount * (getCommissionPercentage() - getCashBackPercentage()));
        if (fromCardNumber.getBalance() < amountWithCommission) {
            return -2;
        }

        boolean doesExist = false;
        for (int i = 0; i < cards.size(); i++) {

            if (cards.get(i).getCardNumber().equals(fromCardNumber.getCardNumber()))
                cards.get(i).setCashBack(cards.get(i).getCashBack() + (amount * (getCashBackPercentage())));

            if (cards.get(i).getCardNumber().equals(toCardNumber)) {
                this.cards.get(i).setBalance(this.cards.get(i).getBalance() + amount);
                this.withdrawMoney(fromCardNumber.getId(), amountWithCommission);

                transferHistory.add(new Card(fromCardNumber.getCardNumber(), toCardNumber, amount, "Output"));
                doesExist = true;
            }
        }

        if (doesExist) return 1;

        return -1;
    }

    public double getWallet(String cardNumber) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardNumber().equals(cardNumber))
                return this.cards.get(i).getCashBack();
        }

        return 0;
    }

    public int getCashMoney(Card fromCard, double amount) {
        double amountWithCommission = amount * (1 +
                (getCommissionPercentage()));
        setOwnerIncome(getOwnerIncome() + amount * (getCommissionPercentage() - getCashBackPercentage()));
        if (fromCard.getBalance() < amountWithCommission) {
            return -1;
        }

        this.withdrawMoney(fromCard.getId(), amountWithCommission);
        transferHistory.add(new Card(fromCard.getCardNumber(), "cash", amount, "In cash"));
        return 1;
    }

    public void getHistoryList() {
        for (Card card : transferHistory) {
            System.out.print("1-card:" + card.getCardNumber() + "\t  2-card: ");
            System.out.print(card.getReceiverCard() + "\tamount:\t ");
            System.out.print(card.getTransferAmount() + "\ttype:\t ");
            System.out.print(card.getTransferType());
            System.out.println();
        }
    }
}
