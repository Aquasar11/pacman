package sample.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import sample.controller.UserController;

import java.io.IOException;
import java.util.Objects;

public class MainView extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/StartPage.fxml"));
        backgroundMusic();
        MainView.stage = stage;
        stage.setScene(new Scene(startingPane));
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/assets/images/Icon.png"))));
        stage.setTitle("PACMAN");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void exitCommand(ActionEvent actionEvent) {
        stage.close();
    }

    public void loginMenu(MouseEvent actionEvent) throws IOException {
        Parent loginPane = FXMLLoader.load(getClass().getResource("/sample/fxml/LoginPage.fxml"));
        stage.setScene(new Scene(loginPane));
    }

    public void registerMenu(MouseEvent mouseEvent) throws IOException {
        Parent registerPane = FXMLLoader.load(getClass().getResource("/sample/fxml/RegisterPage.fxml"));
        stage.setScene(new Scene(registerPane));
    }

    public static MediaPlayer mediaPlayer;

    private void backgroundMusic() {
        mediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/assets/audio/backgroundMusic.mp3")).toString()));
        mediaPlayer.setVolume(0.2);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(-1);
        mediaPlayer.play();
    }

    @FXML
    Button muteButton;

    public void muteCommand(ActionEvent actionEvent) {
        if (muteButton.getText().equals("Mute")) {
            mediaPlayer.pause();
            muteButton.setText("Unmute");
        } else {
            mediaPlayer.play();
            muteButton.setText("Mute");
        }
    }

    public void guestMode(ActionEvent actionEvent) {
        UserController.getInstance().registerUser("Guest", "Guest");
        UserController.getInstance().login("Guest");
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/newGame.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
