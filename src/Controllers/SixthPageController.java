package Controllers;

import Algorithm.CrossValidation;
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

public class SixthPageController {

    @FXML
    Button sb_button, home_button;

    @FXML private RadioButton pp_Yes,pp_No,wloss_Yes,wloss_No, dheal_Yes,dheal_No,mstiff_Yes,mstiff_No;
    @FXML
    private AnchorPane sixthPane;

    private ToggleGroup pp,wloss,dheal,mstiff;

    @FXML
    private void initialize() {

        pp = new ToggleGroup();
        wloss = new ToggleGroup();
        dheal = new ToggleGroup();
        mstiff = new ToggleGroup();
        pp_Yes.setToggleGroup(pp);
        pp_No.setToggleGroup(pp);
        wloss_Yes.setToggleGroup(wloss);
        wloss_No.setToggleGroup(wloss);
        dheal_Yes.setToggleGroup(dheal);
        dheal_No.setToggleGroup(dheal);
        mstiff_Yes.setToggleGroup(mstiff);
        mstiff_No.setToggleGroup(mstiff);

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
            container.getChildren().remove(sixthPane);
        });
        timeline.play();
    }

    private String getAnswer(String label){

        String[] tmp = label.split("_");
        return tmp[0];
    }

    @FXML public void processData() throws IOException {

        RadioButton sel_pp = (RadioButton) pp.getSelectedToggle();
        RadioButton sel_wloss = (RadioButton) wloss.getSelectedToggle();
        RadioButton sel_dheal = (RadioButton) dheal.getSelectedToggle();
        RadioButton sel_mstiff = (RadioButton) mstiff.getSelectedToggle();

        Patient.symptoms[12] = getAnswer(sel_pp.getText());
        Patient.symptoms[4] = getAnswer(sel_wloss.getText());
        Patient.symptoms[11] = getAnswer(sel_dheal.getText());
        Patient.symptoms[13] = getAnswer(sel_mstiff.getText());

        Decisiontree dt = new Decisiontree();
        String result = dt.getAResult();
        System.out.println(result);

        nextScreen("../Views/ResultPage.fxml");

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
            container.getChildren().remove(sixthPane);
        });
        timeline.play();
    }
}
