package sample.controller;

import sample.model.Database;
import sample.model.User;

public class UserController {
    private static UserController userController;

    private UserController() {
    }

    public static UserController getInstance() {
        if (userController == null)
            userController = new UserController();
        return userController;
    }


    public boolean doesUsernameAlreadyExist(String username) {
        return User.getAllUsers().containsKey(username);
    }

    public void registerUser(String username, String password) {
        new User(username, password);
    }

    public void login(String username) {
        MainController.getInstance().setLoggedInUsername(username);
    }

    public boolean isPasswordCorrect(String username, String password) {
        return User.getUserByUsername(username).getPassword().equals(password);
    }

    public void changePassword(String loggedInUsername, String newPass) {
        User.getUserByUsername(loggedInUsername).changePassword(newPass);
    }

    public void deleteAccount(String loggedInUsername) {
        Database.getInstance().removeUser(User.getUserByUsername(loggedInUsername));
        User.getUserByUsername(loggedInUsername).deleteUser(loggedInUsername);
    }
}
