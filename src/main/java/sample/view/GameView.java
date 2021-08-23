package sample.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import sample.controller.MainController;
import sample.controller.game.GameController;
import sample.model.game.Ghost;
import sample.model.game.Map;
import sample.view.animations.GhostAnimation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.view.Moves.*;

public class GameView implements Initializable {
    @FXML
    public Label scoreField;
    public BorderPane mainPane;
    public GridPane ghostBoard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showHearts(GameController.getInstance().getGame().getNumberOfHearts());
        GameController.getInstance().setBoard(gameBoard);
        GameController.getInstance().setGhostBoard(ghostBoard);
        GameController.getInstance().setGameView(this);
        showMap();
        ghostAnimation = new GhostAnimation(GameController.getInstance().getAllGhost());
        run();
    }

    private GhostAnimation ghostAnimation;

    private void run() {
        mainPane.setOnKeyPressed(keyEvent -> {
            String keyName = keyEvent.getCode().getName();
            ghostAnimation.play();
            pause = false;
            switch (keyName) {
                case "W":
                    pacmanImageView.setImage(pacmanUp);
                    GameController.getInstance().move(UP);
                    scoreField.setText(String.valueOf(GameController.getInstance().getGame().getScore()));
                    break;
                case "S":
                    pacmanImageView.setImage(pacmanDown);
                    GameController.getInstance().move(DOWN);
                    scoreField.setText(String.valueOf(GameController.getInstance().getGame().getScore()));
                    break;
                case "D":
                    pacmanImageView.setImage(pacmanRight);
                    GameController.getInstance().move(RIGHT);
                    scoreField.setText(String.valueOf(GameController.getInstance().getGame().getScore()));
                    break;
                case "A":
                    pacmanImageView.setImage(pacmanLeft);
                    GameController.getInstance().move(LEFT);
                    scoreField.setText(String.valueOf(GameController.getInstance().getGame().getScore()));
                    break;
                case "Esc":
                    break;
            }
        });
    }

    public void showHearts(int numberOfHearts) {
        switch (numberOfHearts) {
            case 0:
                heart1.setOpacity(0);
                heart2.setOpacity(0);
                heart3.setOpacity(0);
                heart4.setOpacity(0);
                heart5.setOpacity(0);
                break;
            case 1:
                heart1.setOpacity(1);
                heart2.setOpacity(0);
                heart3.setOpacity(0);
                heart4.setOpacity(0);
                heart5.setOpacity(0);
                break;
            case 2:
                heart1.setOpacity(1);
                heart2.setOpacity(1);
                heart3.setOpacity(0);
                heart4.setOpacity(0);
                heart5.setOpacity(0);
                break;
            case 3:
                heart1.setOpacity(1);
                heart2.setOpacity(1);
                heart3.setOpacity(1);
                heart4.setOpacity(0);
                heart5.setOpacity(0);
                break;
            case 4:
                heart1.setOpacity(1);
                heart2.setOpacity(1);
                heart3.setOpacity(1);
                heart4.setOpacity(1);
                heart5.setOpacity(0);
                break;
            case 5:
                heart1.setOpacity(1);
                heart2.setOpacity(1);
                heart3.setOpacity(1);
                heart4.setOpacity(1);
                heart5.setOpacity(1);
                break;
        }
    }


    @FXML
    private GridPane gameBoard;
    @FXML
    private ImageView heart1;
    @FXML
    private ImageView heart2;
    @FXML
    private ImageView heart3;
    @FXML
    private ImageView heart4;
    @FXML
    private ImageView heart5;

    private final int mazeRows = Map.getMazeRows();
    private final int mazeColumns = Map.getMazeColumns();

    private static final Image wall;
    private static final Image bomb;
    private static final Image dot;
    private static final Image pacmanUp;
    private static final Image pacmanDown;
    private static final Image pacmanRight;
    private static final Image pacmanLeft;
    private static final Image ghost1;
    private static final Image ghost2;
    private static final Image ghost3;
    private static final Image ghost4;
    public static ImageView pacmanImageView;
    public static ImageView ghost1ImageView;
    public static ImageView ghost2ImageView;
    public static ImageView ghost3ImageView;
    public static ImageView ghost4ImageView;
    public static Image deadGhost;

    static {
        wall = new Image(String.valueOf(GameView.class.getResource("/assets/images/wall.png")));
        bomb = new Image(String.valueOf(GameView.class.getResource("/assets/images/whitedot.png")));
        dot = new Image(String.valueOf(GameView.class.getResource("/assets/images/smalldot.png")));
        pacmanRight = new Image(String.valueOf(GameView.class.getResource("/assets/images/pacmanRight.gif")));
        pacmanUp = new Image(String.valueOf(GameView.class.getResource("/assets/images/pacmanUp.gif")));
        pacmanDown = new Image(String.valueOf(GameView.class.getResource("/assets/images/pacmanDown.gif")));
        pacmanLeft = new Image(String.valueOf(GameView.class.getResource("/assets/images/pacmanLeft.gif")));
        ghost1 = new Image(String.valueOf(GameView.class.getResource("/assets/images/lightblueghost.gif")));
        ghost2 = new Image(String.valueOf(GameView.class.getResource("/assets/images/orangeghost.gif")));
        ghost3 = new Image(String.valueOf(GameView.class.getResource("/assets/images/pinkghost.gif")));
        ghost4 = new Image(String.valueOf(GameView.class.getResource("/assets/images/redghost.gif")));
        deadGhost = new Image(String.valueOf(GameView.class.getResource("/assets/images/blueghost.gif")));
    }

    private void showMap() {
        String[][] maze = GameController.getInstance().getGame().getMap().getMaze();
        for (int i = 0; i < (mazeRows * 2) + 1; i++) {
            for (int j = 0; j < (mazeColumns * 2) + 1; j++) {
                switch (maze[i][j]) {
                    case "1":
                        fillGrid(i, j, wall);
                        break;
                    case "0":
                    case "*":
                        fillGrid(i, j, dot);
                        break;
                    case "2":
                        fillGrid(i, j, bomb);
                        break;
                    case "3":
                        fillGrid(i, j, pacmanRight);
                        break;
                    case "4":
                        maze[i][j] = String.valueOf('0');
                        fillGrid(i, j, dot);
                        putGhosts(i, j, ghost1);
                        break;
                    case "5":
                        maze[i][j] = String.valueOf('0');
                        fillGrid(i, j, dot);
                        putGhosts(i, j, ghost2);
                        break;
                    case "6":
                        maze[i][j] = String.valueOf('0');
                        fillGrid(i, j, dot);
                        putGhosts(i, j, ghost3);
                        break;
                    case "7":
                        maze[i][j] = String.valueOf('0');
                        fillGrid(i, j, dot);
                        putGhosts(i, j, ghost4);
                        break;
                }
            }
        }
    }

    private void putGhosts(int i, int j, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(14.8);
        imageView.setFitWidth(14.8);
        ghostBoard.add(imageView, j, i);
        if (i == 1 && j == 1) {
            ghost1ImageView = imageView;
            ghost1ImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            new Ghost(1, 1, 1);
        }
        if (i == 1 && j == 19) {
            ghost2ImageView = imageView;
            new Ghost(2, 1, 19);
        }
        if (i == 19 && j == 1) {
            ghost3ImageView = imageView;
            ghost3ImageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            new Ghost(3, 19, 1);
        }
        if (i == 19 && j == 19) {
            ghost4ImageView = imageView;
            new Ghost(4, 19, 19);
        }
    }

    private void fillGrid(int i, int j, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        gameBoard.add(imageView, j, i);
        if (i == 10 && j == 10) {
            pacmanImageView = imageView;
            getPacmanNode().requestFocus();
        }
    }

    private boolean pause = false;

    public void pauseGame() {
        if (!pause) {
            ghostAnimation.pause();
            pause = true;
        } else {
            ghostAnimation.play();
            pause = false;
        }
    }

    public void exitGame() {
        MainController.getInstance().saveScore();
        GameController.getInstance().reset();
        ghostAnimation.pause();
        pause = true;
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/newGame.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node getPacmanNode() {
        ObservableList<Node> children = gameBoard.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == 10 && GridPane.getColumnIndex(node) == 10)
                return node;
        }
        return null;
    }
}
