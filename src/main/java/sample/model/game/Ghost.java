package sample.model.game;

import sample.controller.game.GameController;

public class Ghost {
    private final int id;
    private int xPosition;
    private int yPosition;
    private boolean isAlive;

    public Ghost(int id, int xPosition, int yPosition) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isAlive = true;
        GameController.getInstance().addGhost(this);
    }

    public int getId() {
        return id;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
