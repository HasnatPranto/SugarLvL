package Controllers;

import Data.Patient;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import sample.Main;

import java.io.IOException;

public class SecondPageController {

    @FXML Button basic_button, home_button;

    @FXML
    private AnchorPane secondPane;

    @FXML private RadioButton input_male, input_female;
    @FXML private TextField input_age;

    private ToggleGroup gender;

    @FXML
    private void initialize() {

        gender = new ToggleGroup();
        input_male.setToggleGroup(gender);
        input_female.setToggleGroup(gender);

    }

   @FXML
    public void ReturnHome(ActionEvent event) throws IOException {
        Patient.refresh();
        Parent root = FXMLLoader.load(getClass().getResource("../Views/sample.fxml"));
        Scene scene = home_button.getScene();
        root.translateYProperty().set(scene.getHeight());

        StackPane container = (StackPane) scene.getRoot();
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(),0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(.4),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( e->{
            container.getChildren().remove(secondPane);
        });
        timeline.play();
    }

    public void NextScreen() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/ThirdPage.fxml"));
        Scene scene = basic_button.getScene();
        root.translateXProperty().set(scene.getHeight());

        StackPane container = (StackPane) scene.getRoot();
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( e->{
            container.getChildren().remove(secondPane);
        });
        timeline.play();
    }

    @FXML
    public void Next(ActionEvent event) throws IOException {

        RadioButton selectedRadioButton = (RadioButton) gender.getSelectedToggle();
        Patient.symptoms[1] = selectedRadioButton.getText();
        Patient.symptoms[0] = (Integer.parseInt(input_age.getText()) < 43) ? "(< 43)":"(> 43)";
        NextScreen();
    }
}
