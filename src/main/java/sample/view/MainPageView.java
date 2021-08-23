package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import sample.controller.MainController;

import java.io.IOException;

public class MainPageView {
    @FXML
    private Button muteButton;

    public void muteCommand(ActionEvent actionEvent) {
        if (muteButton.getText().equals("Mute")) {
            muteButton.setText("Unmute");
            MainView.mediaPlayer.pause();
        } else {
            muteButton.setText("Mute");
            MainView.mediaPlayer.play();
        }
    }

    public void startNewGame(ActionEvent actionEvent) {
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/newGame.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showScoreBoard(ActionEvent actionEvent) {
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/Scoreboard.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent actionEvent) {
        MainController.getInstance().setLoggedInUsername(null);
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/StartPage.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accountSetting(ActionEvent actionEvent) {
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/AccountSettingPage.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
