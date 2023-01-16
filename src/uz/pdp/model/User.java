package uz.pdp.model;

public class User extends BaseModel {
    private String password;
    private int smsCode;


    @Override
    protected boolean checkName() {
        return super.name != null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSmsCode() {
        return smsCode;
    }

    public User() {
    }

    public User(String name, String password, String phoneNumber) {
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


    public void setSmsCode() {
        this.smsCode = (int) (Math.random() * 10_000d + 10_000);
    }

    @Override
    public String toString() {
        return "|| " + id +
                " || " + name +
                " || " + phoneNumber +
                " || " + password +
                " || " + smsCode;
    }
}