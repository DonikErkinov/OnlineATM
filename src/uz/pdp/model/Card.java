package uz.pdp.model;

import java.util.UUID;


public class Card extends BaseModel {

    protected UUID userId;
    protected double balance;
    private String expiryDate;
    private String cardNumber;
    private double cashBack;
    private String receiverCard;
    private double transferAmount;
    private String transferType;
    private String cardPassword;

    public Card(UUID userId) {
        this.userId = userId;
        this.balance = 0;
        this.cashBack = 0;
    }

    public Card() {
    }

    public Card(String cardNumber, String receiverCard, double transferAmount, String transferType) {
        this.cardNumber = cardNumber;
        this.receiverCard = receiverCard;
        this.transferAmount = transferAmount;
        this.transferType = transferType;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public String getReceiverCard() {
        return receiverCard;
    }

    public void setReceiverCard(String receiverCard) {
        this.receiverCard = receiverCard;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    @Override
    protected boolean checkName() {
        return true;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getCashBack() {
        return cashBack;
    }

    public void setCashBack(double cashBack) {
        this.cashBack = cashBack;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "|| " + name +
                " || " + cardNumber +
                " || " + phoneNumber +
                " || " + expiryDate +
                " || " + cardPassword +
                " || " + balance;
    }
}