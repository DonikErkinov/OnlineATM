package uz.pdp.service;

import uz.pdp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService implements BaseService<User> {

    private List<User> users = new ArrayList<>();

    @Override
    public boolean add(User user) {
        this.users.add(user);
        return true;
    }

    @Override
    public void getListByUserId(UUID id) {

    }

    public UserService() {
        User user = new User("superAdmin", "1234", "931534602");
        this.users.add(user);
        user = new User("Manager", "1111", "332020024");
        this.users.add(user);
    }

    @Override
    public void getList() {
        System.out.println("List of users: ");
        if (this.users.size() == 0) {
            System.out.println("No users yet");
        }
        for (int i = 2; i < users.size(); i++) {
            System.out.println(users.get(i).toString());
        }
    }

    public User signIn(String phoneNumber, String password) {
        for (User user : this.users) {
            if (user.getPhoneNumber().equals(phoneNumber) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public boolean isAdmin(String phoneNumber, String password) {
        if (users.get(0).getPhoneNumber().equals(phoneNumber) && users.get(0).getPassword().equals(password))
            return true;
        return false;
    }

    public boolean isManager(String phoneNumber, String password) {
        if (users.get(1).getPhoneNumber().equals(phoneNumber) && users.get(1).getPassword().equals(password))
            return true;
        return false;
    }

    public boolean checkUser(String phoneNumber) {
        for (User user : this.users) {
            if (user.getPhoneNumber().equals(phoneNumber))
                return true;
        }
        return false;
    }
}
