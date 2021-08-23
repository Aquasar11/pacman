package sample.view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import sample.controller.game.GameController;
import sample.model.game.Ghost;

import java.util.ArrayList;
import java.util.Random;

public class GhostAnimation extends Transition {
    private final ArrayList<Ghost> ghosts;

    public GhostAnimation(ArrayList<Ghost> ghosts) {
        this.ghosts = ghosts;
        this.setCycleDuration(Duration.millis(100));
        this.setCycleCount(-1);
        this.setRate(0.0005);
    }

    @Override
    protected void interpolate(double v) {
        Random random = new Random();
        int direction;
        for (Ghost ghost : ghosts) {
            boolean move = false;
            while (!move) {
                direction = random.nextInt(4);
                switch (direction) {
                    case 0:
                        if (GameController.getInstance().notWall(ghost.getXPosition() + 1, ghost.getYPosition()) &&
                                notGhost(ghost.getXPosition() + 1, ghost.getYPosition())) {
                            move = true;
                            GameController.getInstance().moveGhost(ghost, 1, 0);
                        }
                        break;
                    case 1:
                        if (GameController.getInstance().notWall(ghost.getXPosition() - 1, ghost.getYPosition()) &&
                                notGhost(ghost.getXPosition() - 1, ghost.getYPosition())) {
                            move = true;
                            GameController.getInstance().moveGhost(ghost, -1, 0);
                        }
                        break;
                    case 2:
                        if (GameController.getInstance().notWall(ghost.getXPosition(), ghost.getYPosition() + 1) &&
                                notGhost(ghost.getXPosition(), ghost.getYPosition() + 1)) {
                            move = true;
                            GameController.getInstance().moveGhost(ghost, 0, 1);
                        }
                        break;
                    case 3:
                        if (GameController.getInstance().notWall(ghost.getXPosition(), ghost.getYPosition() - 1) &&
                                notGhost(ghost.getXPosition(), ghost.getYPosition() - 1)) {
                            move = true;
                            GameController.getInstance().moveGhost(ghost, 0, -1);
                        }
                        break;
                }
            }
        }
    }

    private boolean notGhost(int x, int y) {
        for (Ghost ghost : ghosts) {
            if (ghost.getXPosition() == x && ghost.getYPosition() == y)
                return false;
        }
        return true;
    }

}
