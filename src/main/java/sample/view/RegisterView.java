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

public class RegisterView {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label registerMessage;

    public void registerCommand(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty())
            registerMessage.setText("Please fill both fields first!!!");
        else if (UserController.getInstance().doesUsernameAlreadyExist(username))
            registerMessage.setText("This username is already exist!\nPlease enter another username");
        else {
            UserController.getInstance().registerUser(username, password);
            registerMessage.setText("Registered successfully");
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
