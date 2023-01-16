package uz.pdp;

import uz.pdp.model.Card;
import uz.pdp.model.User;
import uz.pdp.service.CardService;
import uz.pdp.service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerDbl = new Scanner(System.in);

        UserService userService = new UserService();
        CardService cardService = new CardService();

        int stepCode = 1;
        while (stepCode != 0) {
            System.out.println("1. sign in\t2. sign up\t 0. Exit");
            stepCode = scannerInt.nextInt();
            if (stepCode == 0) break;
            System.out.println("Enter phone number(996446502) : ");
            String phoneNumber = scannerStr.nextLine();
            boolean isValidPhoneNumber = phoneNumber.length() == 9;
            if (isValidPhoneNumber) {
                if (stepCode == 1) {
                    if (userService.checkUser(phoneNumber)) {
                        System.out.println("Enter password: ");
                        String password = scannerStr.nextLine();
                        User user = userService.signIn(phoneNumber, password);
                        if (userService.isAdmin(phoneNumber, password)) {
                            adminPanel(cardService, scannerInt, scannerDbl, userService);
                        }
                        if (userService.isManager(phoneNumber, password)) {
                            managerPanel(cardService, userService, scannerInt);
                        }
                        if (user != null) {
                            System.out.println("Success✔");
                            int stepCode2 = 1;
                            while (stepCode2 != 0 && !userService.isAdmin(phoneNumber, password) && !userService.isManager(phoneNumber, password)) {
                                System.out.println("||>>");
                                System.out.println("1. Add card\t2. Get card list\t3. P2P\t4. Fill balance\t5.History\t6.Wallet\t7.Get Cash money\t8.Change card password\t0. Exit");
                                System.out.println("||>>");
                                stepCode2 = scannerInt.nextInt();
                                switch (stepCode2) {
                                    case 1: {
                                        Card card = new Card(user.getId());
                                        cardService.add(addCard(card, cardService, scannerStr, user.getPhoneNumber()));
                                    }
                                    break;
                                    case 2: {
                                        cardService.getListByUserId(user.getId());

                                    }
                                    break;
                                    case 3: {
                                        System.out.println("Commission fee :" + cardService.getCommissionPercentage() * 100 + "%");
                                        System.out.println("from which card do you want to pay: ");
                                        String payCard1 = scannerStr.nextLine();
                                        Card payCard = cardService.checkCardHolder(user.getId(), payCard1);
                                        if (payCard != null) {
                                            System.out.println("To which card do you want to send: ");
                                            String fillCard1 = scannerStr.nextLine();

                                            System.out.println("How much do you want to transfer");
                                            double amount1 = scannerInt.nextDouble();
                                            int isTransferred = cardService.p2p(payCard, fillCard1, amount1);
                                            if (isTransferred == 1) {
                                                System.out.println("Successfully transferred");
                                            } else if (isTransferred == -2)
                                                System.out.println("Your balance is low,Please check it first");
                                            else if (isTransferred == -1)
                                                System.out.println("transferring Card number is wrong");
                                        } else System.out.println("Card number is wrong");
                                    }
                                    break;
                                    case 4: {
                                        System.out.println("Enter card number");
                                        String cardNumber = scannerStr.nextLine();
                                        System.out.println("Enter amount");
                                        double amount = scannerInt.nextDouble();
                                        boolean isBalanceFilled = cardService.fillBalance(cardNumber, amount);
                                        if (isBalanceFilled) {
                                            System.out.println("Your balance filled successfully");
                                        } else System.out.println("Wrong card number");
                                    }
                                    break;
                                    case 5: {
                                        cardService.getHistoryList();
                                    }
                                    break;
                                    case 6: {
                                        System.out.println("Cashback percentage :" + cardService.getCashBackPercentage() * 100 + "%");
                                        System.out.println("Enter card number");
                                        String cardNumber = scannerStr.nextLine();
                                        System.out.print("Your cashback is ");
                                        double check = cardService.getWallet(cardNumber);
                                        System.out.println(check);
                                    }
                                    break;
                                    case 7: {
                                        System.out.println("Commission fee :" + cardService.getCommissionPercentage() * 100 + "%");
                                        System.out.println("from which card do you want to pay: ");
                                        String payCard1 = scannerStr.nextLine();
                                        Card payCard = cardService.checkCardHolder(user.getId(), payCard1);
                                        System.out.println("How much money do you want to get ?");
                                        double amount = scannerDbl.nextDouble();
                                        int isGetMoney = (cardService.getCashMoney(payCard, amount));
                                        if (isGetMoney == 1) {
                                            System.out.println("You can get your in cash money now, wait a second ✔😊");
                                        } else if (isGetMoney == -1) {
                                            System.out.println("Your balance is low,Please check your balance");
                                        }
                                    }
                                    break;
                                    case 8: {
                                        System.out.println("Enter card number :");
                                        String cardNumber = scannerStr.nextLine();
                                        Card checkNumber = cardService.checkCardHolder(user.getId(), cardNumber);
                                        if (checkNumber != null) {
                                            System.out.println("Your old password is :" + checkNumber.getCardPassword());
                                            System.out.println("Enter new password :");
                                            boolean check = cardService.editPassword(cardNumber, scannerStr.nextLine());
                                            if (check)
                                                System.out.println("Successfully changed ✔");
                                            else
                                                System.out.println("Card number not found");
                                        } else {
                                            System.out.println("Card number is wrong ❌");
                                        }
                                    }
                                    break;
                                }
                            }
                        } else System.out.println("wrong password");
                    } else System.out.println("Please, sign up first");
                }
                if (stepCode == 2) {
                    if (userService.checkUser(phoneNumber)) {
                        System.out.println("You have already signed up! Please, sign in");
                    } else {
                        User user = new User();
                        user.setPhoneNumber(phoneNumber);
                        user.setSmsCode();
                        System.out.println("Your sms code: " + user.getSmsCode() + ". \nDon't allow to see anybody else");
                        int smsCode = scannerInt.nextInt();
                        if (smsCode == user.getSmsCode()) {
                            System.out.println("Verified successfully ✔");
                            boolean HadUser = userService.add(signUp(user, scannerStr));
                            if (HadUser) System.out.println("You have signed up");
                            else System.out.println("Something wrong");
                        } else System.out.println("wrong sms code");
                    }
                }
            } else System.out.println("Please enter valid phone number");
        }
    }

    private static void managerPanel(CardService cardService, UserService userService, Scanner scannerInt) {
        int stepcode = 1;
        while (stepcode != 0) {
            System.out.println("||>>");
            System.out.println("1.User List\t2.Wallet\t3.History\t0.Exit");
            System.out.println("||>>");
            stepcode = scannerInt.nextInt();

            switch (stepcode) {
                case 1: {
                    userService.getList();
                }
                break;
                case 2: {
                    double income = cardService.getOwnerIncome();
                    System.out.println("Your income is =" + income);
                }
                break;
                case 3: {
                    cardService.getHistoryList();
                }
                break;
            }
        }

    }


    private static User signUp(User user, Scanner scannerStr) {
        System.out.println("Your name: ");
        user.setName(scannerStr.nextLine());
        System.out.println("Create password: ");
        user.setPassword(scannerStr.nextLine());
        return user;
    }

    private static void adminPanel(CardService cardService, Scanner scannerInt, Scanner scannerDbl, UserService userService) {
        int stepcode3 = 1;
        while (stepcode3 != 0) {
            System.out.println("||>>");
            System.out.println("1.Add commission %\t2.Add cashback %\t3.UserList\t4.Wallet\t5.History\t0.Exit");
            System.out.println("||>>");
            stepcode3 = scannerInt.nextInt();
            switch (stepcode3) {
                case 1: {
                    System.out.println("Enter commission percentage :(From 0 up to 1)");
                    System.out.print("|>");
                    cardService.setCommissionPercentage(scannerDbl.nextDouble());
                    System.out.println("Successfully changed");
                }
                break;
                case 2: {
                    System.out.println("Enter cashback percentage : (From 0 up to 1)");
                    System.out.print("|>");
                    cardService.setCashBackPercentage(scannerDbl.nextDouble());
                    System.out.println("Successfully changed");
                }
                break;
                case 3: {
                    userService.getList();
                }
                break;
                case 4: {
                    double income = cardService.getOwnerIncome();
                    System.out.println("Your income is =" + income);
                }
                break;
                case 5: {
                    cardService.getHistoryList();
                }
                break;
            }
        }
    }

    public static Card addCard(Card card, CardService cardService, Scanner scannerStr, String phoneNumber) {
        System.out.println("Enter card name: ");
        card.setName(scannerStr.nextLine());
        System.out.println("Enter card number: ");
        card.setCardNumber(scannerStr.nextLine());
        if (cardService.checkCardHolder(card.getUserId(), card.getCardNumber()) == null) {

            System.out.println("Enter card expiry date");
            card.setExpiryDate(scannerStr.nextLine());
            System.out.println("Enter card password :");
            card.setCardPassword(scannerStr.nextLine());
            card.setPhoneNumber(phoneNumber);
            System.out.println("Card was successfully added");
            return card;
        } else {
            System.out.println("This card was already registered");
            return null;
        }
    }

    }