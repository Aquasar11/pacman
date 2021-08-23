package sample.model;

import java.util.HashMap;

public class User {
    private final String username;
    private String password;
    private int maxScore;
    private int rank;
    private static HashMap<String, User> allUsers;

    static {
        allUsers = Database.getInstance().updateAllUsers();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.maxScore = 0;
        allUsers.put(username, this);
        Database.getInstance().saveUser(this);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        Database.getInstance().saveUser(this);
    }

    public void deleteUser(String username) {
        allUsers.remove(username);
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int score) {
        if (score > this.maxScore)
            this.maxScore = score;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static User getUserByUsername(String username) {
        return allUsers.get(username);
    }

    public static HashMap<String, User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(HashMap<String, User> allUsers) {
        User.allUsers = allUsers;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
