package sample.controller;

import sample.controller.game.GameController;
import sample.model.Database;
import sample.model.User;

public class MainController {
    private static MainController mainController;

    private MainController() {
    }

    public static MainController getInstance() {
        if (mainController == null)
            mainController = new MainController();
        return mainController;
    }

    private String loggedInUsername;

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setLoggedInUsername(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
    }

    public void saveScore() {
        User.getUserByUsername(loggedInUsername).setMaxScore(GameController.getInstance().getGame().getScore());
        Database.getInstance().saveUser(User.getUserByUsername(loggedInUsername));
    }
}
