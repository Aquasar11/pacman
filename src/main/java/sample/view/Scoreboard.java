package sample.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Scoreboard implements Initializable {
    @FXML
    private TableView<User> scoreboard;
    @FXML
    private TableColumn<User, Integer> rankColumn;
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, Integer> maxScore;

    private ObservableList<User> updateScoreBoard() {
        ArrayList<User> allUsers = new ArrayList<>(User.getAllUsers().values());
        allUsers.sort((o1, o2) -> {
            if (o1.getMaxScore() == o2.getMaxScore())
                return o1.getUsername().compareTo(o2.getUsername());
            else
                return o2.getMaxScore() - o1.getMaxScore();
        });

        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        int counter = 10;
        int prevRank = 1;
        int prevScore = allUsers.get(0).getMaxScore();
        if (allUsers.size() < counter)
            counter = allUsers.size();
        for (int i = 0; i < counter; i++) {
            if (i == 0)
                allUsers.get(i).setRank(1);
            else if (prevScore == allUsers.get(i).getMaxScore())
                allUsers.get(i).setRank(prevRank);
            else {
                allUsers.get(i).setRank(i + 1);
                prevScore = allUsers.get(i).getMaxScore();
                prevRank = i + 1;
            }
            userObservableList.add(allUsers.get(i));
        }

        return userObservableList;
    }

    public void backCommand(ActionEvent actionEvent) {
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/MainPage.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        maxScore.setCellValueFactory(new PropertyValueFactory<User, Integer>("maxScore"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("rank"));
        scoreboard.setItems(updateScoreBoard());
    }
}