package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import sample.controller.MainController;
import sample.controller.UserController;

import java.io.IOException;

public class AccountSettingPage {

    @FXML
    private Label changeMessageResponse;
    @FXML
    private PasswordField newPassField;
    @FXML
    private PasswordField oldPassField;

    public void changePasswordCommand(ActionEvent actionEvent) {
        String oldPass = oldPassField.getText().trim();
        String newPass = newPassField.getText().trim();

        if (newPassField.getText().isEmpty() || oldPassField.getText().isEmpty())
            changeMessageResponse.setText("Please fill both fields first!!!");
        else if (!UserController.getInstance().isPasswordCorrect(MainController.getInstance().getLoggedInUsername(), oldPass))
            changeMessageResponse.setText("Old password is wrong!");
        else if (oldPass.equals(newPass))
            changeMessageResponse.setText("Your new password can't be the old one!!!");
        else {
            UserController.getInstance().changePassword(MainController.getInstance().getLoggedInUsername(), newPass);
            changeMessageResponse.setText("Password changed successfully!");
        }
    }

    public void deleteAccountCommand(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(MainView.getStage());
        alert.getButtonTypes().set(0, ButtonType.YES);
        alert.getButtonTypes().set(1, ButtonType.NO);
        alert.getDialogPane().setContentText("Are you sure?!");
        alert.getDialogPane().setHeaderText("Delete Account");
        if (alert.showAndWait().get().equals(ButtonType.YES)) {
            alert.close();
            UserController.getInstance().deleteAccount(MainController.getInstance().getLoggedInUsername());
            try {
                Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/StartPage.fxml"));
                MainView.getStage().setScene(new Scene(startingPane));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.close();
        }
    }

    public void backCommand(ActionEvent actionEvent) {
        try {
            Parent startingPane = FXMLLoader.load(getClass().getResource("/sample/fxml/MainPage.fxml"));
            MainView.getStage().setScene(new Scene(startingPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
