package sample.controller.game;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import sample.model.game.Game;
import sample.model.game.Ghost;
import sample.model.game.Map;
import sample.view.GameView;
import sample.view.MainView;
import sample.view.Moves;
import sample.view.animations.GhostAnimation;
import java.util.ArrayList;

public class GameController {
    private static GameController gameController;

    private GameController() {
    }

    public static GameController getInstance() {
        if (gameController == null)
            gameController = new GameController();
        return gameController;
    }

    private Game game;
    private GridPane board;
    private GridPane ghostBoard;
    private GameView gameView;
    private int pacmanX = 10;
    private int pacmanY = 10;

    public void startNewGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setBoard(GridPane board) {
        this.board = board;
    }

    public void move(Moves direction) {
        switch (direction) {
            case UP:
                if (notWall(pacmanX - 1, pacmanY)) {
                    movePacmanInBoard(pacmanX, pacmanY, -1, 0);
                    pacmanX -= 1;
                }
                break;
            case DOWN:
                if (notWall(pacmanX + 1, pacmanY)) {
                    movePacmanInBoard(pacmanX, pacmanY, 1, 0);
                    pacmanX += 1;
                }
                break;
            case RIGHT:
                if (notWall(pacmanX, pacmanY + 1)) {
                    movePacmanInBoard(pacmanX, pacmanY, 0, 1);
                    pacmanY += 1;
                }
                break;
            case LEFT:
                if (notWall(pacmanX, pacmanY - 1)) {
                    movePacmanInBoard(pacmanX, pacmanY, 0, -1);
                    pacmanY -= 1;
                }
                break;
        }
        checkScore(pacmanX, pacmanY);
        checkBomb();
        checkGame();
        if (pacmanWins())
            checkWins();
    }

    public void checkBomb() {
        if (game.getMap().getMaze()[pacmanX][pacmanY].equals("2")) {
            game.getMap().getMaze()[pacmanX][pacmanY] = String.valueOf('&');
            game.increaseScore(10);
        }
    }


    private boolean pacmanWins() {
        String[][] maze = GameController.getInstance().getGame().getMap().getMaze();
        int dotCounter = 0;
        for (int i = 0; i < (Map.getMazeRows() * 2) + 1; i++) {
            for (int j = 0; j < (Map.getMazeColumns() * 2) + 1; j++) {
                if (maze[i][j].equals("0") || maze[i][j].equals("*")) {
                    dotCounter += 1;
                    break;
                }
            }
        }
        return dotCounter == 0;
    }

    private void checkWins() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(MainView.getStage());
        alert.getButtonTypes().set(0, ButtonType.OK);
        alert.getDialogPane().setContentText("YOU WIN :)\nGOOD JOB!");
        alert.getDialogPane().setHeaderText("WINNER");
        alert.showAndWait();
        alert.close();
        gameView.exitGame();
    }

    private void movePacmanInBoard(int x, int y, int dx, int dy) {
        board.getChildren().remove(getNodeByRowColumnIndexFromBoard(x, y));
        board.getChildren().remove(getNodeByRowColumnIndexFromBoard(x + dx, y + dy));
        board.add(GameView.pacmanImageView, y + dy, x + dx);
    }


    private void checkScore(int pacmanX, int pacmanY) {
        if (game.getMap().getMaze()[pacmanX][pacmanY].equals("0") ||
                game.getMap().getMaze()[pacmanX][pacmanY].equals("*")) {
            game.getMap().getMaze()[pacmanX][pacmanY] = String.valueOf('$');
            game.increaseScore(5);
        }
    }

    public boolean notWall(int pacmanX, int pacmanY) {
        return !game.getMap().getMaze()[pacmanX][pacmanY].equals("1");
    }

    private Node getNodeByRowColumnIndexFromBoard(final int row, final int column) {
        ObservableList<Node> children = board.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
                return node;
        }
        return null;
    }

    private Node getNodeByRowColumnIndexFromGhostBoard(final int row, final int column) {
        ObservableList<Node> children = ghostBoard.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
                return node;
        }
        return null;
    }

    private ArrayList<Ghost> allGhost = new ArrayList<>();

    public void addGhost(Ghost ghost) {
        allGhost.add(ghost);
    }

    public ArrayList<Ghost> getAllGhost() {
        return allGhost;
    }

    public void reset() {
        pacmanX = 10;
        pacmanY = 10;
        allGhost = new ArrayList<>();
    }

    public void setGhostBoard(GridPane ghostBoard) {
        this.ghostBoard = ghostBoard;
    }

    public void moveGhost(Ghost ghost, int dx, int dy) {
        int xPosition = ghost.getXPosition();
        int yPosition = ghost.getYPosition();
        int id = ghost.getId();
        ghost.setXPosition(xPosition + dx);
        ghost.setYPosition(yPosition + dy);
        ghostBoard.getChildren().remove(getNodeByRowColumnIndexFromGhostBoard(xPosition, yPosition));
        ghostBoard.getChildren().remove(getNodeByRowColumnIndexFromGhostBoard(xPosition + dx, yPosition + dy));
        switch (id) {
            case 1:
                ghostBoard.add(GameView.ghost1ImageView, yPosition + dy, xPosition + dx);
                break;
            case 2:
                ghostBoard.add(GameView.ghost2ImageView, yPosition + dy, xPosition + dx);
                break;
            case 3:
                ghostBoard.add(GameView.ghost3ImageView, yPosition + dy, xPosition + dx);
                break;
            case 4:
                ghostBoard.add(GameView.ghost4ImageView, yPosition + dy, xPosition + dx);
                break;
        }
        checkGame();
    }

    public void checkGame() {
        if (hasCollision()) {
            game.increaseHeart(-1);
            gameView.showHearts(game.getNumberOfHearts());
            if (game.getNumberOfHearts() <= 0) {
                endGame();
            } else {
                moveGhostsToPosition();
                movePacmanToPosition();
            }
        }
    }

    private void movePacmanToPosition() {
        board.getChildren().remove(getNodeByRowColumnIndexFromBoard(pacmanX, pacmanY));
        board.getChildren().remove(getNodeByRowColumnIndexFromBoard(10, 10));
        board.add(GameView.pacmanImageView, 10, 10);
        pacmanX = 10;
        pacmanY = 10;
    }

    private void moveGhostsToPosition() {
        for (Ghost ghost : allGhost) {
            resetGhost(ghost);
            ghost.setAlive(false);
        }
    }

    private void resetGhost(Ghost ghost) {
        int xPosition = ghost.getXPosition();
        int yPosition = ghost.getYPosition();
        int id = ghost.getId();
        ghostBoard.getChildren().remove(getNodeByRowColumnIndexFromGhostBoard(xPosition, yPosition));
        switch (id) {
            case 1:
                ghostBoard.getChildren().remove(getNodeByRowColumnIndexFromGhostBoard(1, 1));
                ghostBoard.add(GameView.ghost1ImageView, 1, 1);
                ghost.setXPosition(1);
                ghost.setYPosition(1);
                break;
            case 2:
                ghostBoard.getChildren().remove(getNodeByRowColumnIndexFromGhostBoard(1, 19));
                ghostBoard.add(GameView.ghost2ImageView, 1, 19);
                ghost.setXPosition(1);
                ghost.setYPosition(19);
                break;
            case 3:
                ghostBoard.getChildren().remove(getNodeByRowColumnIndexFromGhostBoard(19, 1));
                ghostBoard.add(GameView.ghost3ImageView, 19, 1);
                ghost.setXPosition(19);
                ghost.setYPosition(1);
                break;
            case 4:
                ghostBoard.getChildren().remove(getNodeByRowColumnIndexFromGhostBoard(19, 19));
                ghostBoard.add(GameView.ghost4ImageView, 19, 19);
                ghost.setXPosition(19);
                ghost.setYPosition(19);
                break;
        }
    }


    private boolean hasCollision() {
        for (Ghost ghost : allGhost) {
            if (ghost.getXPosition() == pacmanX && ghost.getYPosition() == pacmanY)
                return true;
        }
        return false;
    }

    private void endGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(MainView.getStage());
        alert.getButtonTypes().set(0, ButtonType.OK);
        alert.getDialogPane().setContentText("You loose :((\nBetter luck next time!");
        alert.getDialogPane().setHeaderText("Game Over");
        alert.showAndWait();
        alert.close();
        gameView.exitGame();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

}
