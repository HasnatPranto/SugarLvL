package Controllers;

import Algorithm.Decisiontree;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class FifthPageController {
    @FXML
    Button sb_button, home_button;

    @FXML private RadioButton blur_Yes,blur_No,itch_Yes,itch_No, obs_Yes,obs_No,wk_Yes,wk_No;
    @FXML
    private AnchorPane fifthPane;

    private ToggleGroup blur,itch,obs,wk;

    @FXML
    private void initialize() {

        blur = new ToggleGroup();
        itch = new ToggleGroup();
        obs = new ToggleGroup();
        wk = new ToggleGroup();
        blur_Yes.setToggleGroup(blur);
        blur_No.setToggleGroup(blur);
        itch_Yes.setToggleGroup(itch);
        itch_No.setToggleGroup(itch);
        obs_Yes.setToggleGroup(obs);
        obs_No.setToggleGroup(obs);
        wk_Yes.setToggleGroup(wk);
        wk_No.setToggleGroup(wk);

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
            container.getChildren().remove(fifthPane);
        });
        timeline.play();
    }

    private String getAnswer(String label){

        String[] tmp = label.split("_");
        return tmp[0];
    }

    @FXML public void processData() throws IOException {

        RadioButton sel_blur = (RadioButton) blur.getSelectedToggle();
        RadioButton sel_itch = (RadioButton) itch.getSelectedToggle();
        RadioButton sel_obs = (RadioButton) obs.getSelectedToggle();
        RadioButton sel_wk = (RadioButton) wk.getSelectedToggle();

        Patient.symptoms[8] = getAnswer(sel_blur.getText());
        Patient.symptoms[9] = getAnswer(sel_itch.getText());
        Patient.symptoms[15] = getAnswer(sel_obs.getText());
        Patient.symptoms[5] = getAnswer(sel_wk.getText());

        Decisiontree dt = new Decisiontree();
        String result = dt.getAResult();
        System.out.println(result);
        if(result!=null){
            nextScreen("../Views/ResultPage.fxml");
        }
        else{
            nextScreen("../Views/SixthPage.fxml");
        }

    }
    public void nextScreen(String path) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = sb_button.getScene();
        root.translateXProperty().set(scene.getHeight());

        StackPane container = (StackPane) scene.getRoot();
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( e->{
            container.getChildren().remove(fifthPane);
        });
        timeline.play();
    }
}
