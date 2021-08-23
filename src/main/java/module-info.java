module Pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    exports sample.view;
    opens sample.view to javafx.fxml;
    opens sample.model to javafx.fxml, com.google.gson, javafx.base;
    exports sample.view.animations;
    opens sample.view.animations to javafx.fxml;
    exports sample.model.game;
}