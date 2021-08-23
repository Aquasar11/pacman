package sample.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import sample.controller.MainController;
import sample.controller.game.GameController;
import sample.model.game.Game;
import sample.model.game.Map;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class newGame implements Initializable {
    @FXML
    private ImageView thirdHeart;
    @FXML
    private ImageView fourthHeart;
    @FXML
    private ImageView fifthHeart;
    @FXML
    private GridPane mapGrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thirdHeart.setOpacity(0);
        fourthHeart.setOpacity(0);
        fifthHeart.setOpacity(0);
        map = new Map();
        showMap();
        numberOfHearts = 2;
    }

    private final int mazeRows = Map.getMazeRows();
    private final int mazeColumns = Map.getMazeColumns();

    private void showMap() {
        Image wall = new Image(String.valueOf(getClass().getResource("/assets/images/wall.png")));
        Image bomb = new Image(String.valueOf(getClass().getResource("/assets/images/whitedot.png")));
        Image dot = new Image(String.valueOf(getClass().getResource("/assets/images/smalldot.png")));
        Image pacman = new Image(String.valueOf(getClass().getResource("/assets/images/pacmanRight.gif")));
        Image ghost1 = new Image(String.valueOf(getClass().getResource("/assets/images/lightblueghost.gif")));
        Image ghost2 = new Image(String.valueOf(getClass().getResource("/assets/images/orangeghost.gif")));
        Image ghost3 = new Image(String.valueOf(getClass().getResource("/assets/images/pinkghost.gif")));
        Image ghost4 = new Image(String.valueOf(getClass().getResource("/assets/images/redghost.gif")));
        String[][] maze = map.getMaze();
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
                        fillGrid(i, j, pacman);
                        break;
                    case "4":
                        fillGrid(i, j, ghost1);
                        break;
                    case "5":
                        fillGrid(i, j, ghost2);
                        break;
                    case "6":
                        fillGrid(i, j, ghost3);
                        break;
                    case "7":
                        fillGrid(i, j, ghost4);
                        break;
                }
            }
        }
    }

    private void fillGrid(int i, int j, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(10);
        imageView.setFitWidth(10);
        mapGrid.add(imageView, j, i);
    }

    private Map map;
    private int numberOfHearts;

    public void start(ActionEvent actionEvent) {
        GameController.getInstance().startNewGame(new Game(map, numberOfHearts));
        try {
            BorderPane startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/Game.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decreaseHearts(MouseEvent actionEvent) {
        if (numberOfHearts == 3) {
            numberOfHearts -= 1;
            thirdHeart.setOpacity(0);
        } else if (numberOfHearts == 4) {
            numberOfHearts -= 1;
            fourthHeart.setOpacity(0);
        } else if (numberOfHearts == 5) {
            numberOfHearts -= 1;
            fifthHeart.setOpacity(0);
        }
    }

    public void increaseHearts(MouseEvent actionEvent) {
        if (numberOfHearts == 2) {
            numberOfHearts += 1;
            thirdHeart.setOpacity(1);
        } else if (numberOfHearts == 3) {
            numberOfHearts += 1;
            fourthHeart.setOpacity(1);
        } else if (numberOfHearts == 4) {
            numberOfHearts += 1;
            fifthHeart.setOpacity(1);
        }
    }

    public void newMap(ActionEvent actionEvent) {
        map = new Map();
        for (int i = 0; i < (mazeRows * 2) + 1; i++) {
            for (int j = 0; j < (mazeColumns * 2) + 1; j++) {
                mapGrid.getChildren().remove(getNodeByRowColumnIndex(i, j));
            }
        }
        showMap();
    }

    private Node getNodeByRowColumnIndex(final int row, final int column) {
        ObservableList<Node> children = mapGrid.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
                return node;
        }
        return null;
    }

    public void back(ActionEvent actionEvent) {
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/MainPage.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
