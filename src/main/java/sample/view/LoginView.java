package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.controller.UserController;

import java.io.IOException;

public class LoginView {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label loginMessageField;

    public void loginCommand(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty())
            loginMessageField.setText("Please fill both fields first!!!");
        else if (!UserController.getInstance().doesUsernameAlreadyExist(username))
            loginMessageField.setText("This username does not exist!");
        else if (!UserController.getInstance().isPasswordCorrect(username, password))
            loginMessageField.setText("Username or password is wrong!");
        else {
            UserController.getInstance().login(username);
            try {
                Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/MainPage.fxml"));
                MainView.getStage().setScene(new Scene(startingPane));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void backCommand(ActionEvent actionEvent) {
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/StartPage.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}
