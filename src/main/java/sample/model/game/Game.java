package sample.model.game;

import sample.model.User;

public class Game {
    private final Map map;
    private int numberOfHearts;
    private int score;

    public Game(Map map, int numberOfHearts) {
        this.map = map;
        this.numberOfHearts = numberOfHearts;
    }

    public Map getMap() {
        return map;
    }

    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    public void increaseHeart(int amount) {
        this.numberOfHearts += amount;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }
}
