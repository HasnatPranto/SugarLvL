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

public class FourthPageController {

    @FXML
    Button sb_button, home_button;

    @FXML private RadioButton irri_Yes,irri_No,alo_Yes,alo_No, gt_Yes,gt_No;
    @FXML
    private AnchorPane fourthPane;

    private ToggleGroup irri, alo, gt;

    @FXML
    private void initialize() {

        irri = new ToggleGroup();
        alo = new ToggleGroup();
        gt = new ToggleGroup();
        irri_Yes.setToggleGroup(irri);
        irri_No.setToggleGroup(irri);
        alo_Yes.setToggleGroup(alo);
        alo_No.setToggleGroup(alo);
        gt_Yes.setToggleGroup(gt);
        gt_No.setToggleGroup(gt);

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
            container.getChildren().remove(fourthPane);
        });
        timeline.play();
    }

    private String getAnswer(String label){

        String[] tmp = label.split("_");
        return tmp[0];
    }

    @FXML public void processData() throws IOException {

        RadioButton sel_irri = (RadioButton) irri.getSelectedToggle();
        RadioButton sel_alo = (RadioButton) alo.getSelectedToggle();
        RadioButton sel_gt = (RadioButton) gt.getSelectedToggle();

        Patient.symptoms[10] = getAnswer(sel_irri.getText());
        Patient.symptoms[14] = getAnswer(sel_alo.getText());
        Patient.symptoms[7] = getAnswer(sel_gt.getText());

        Decisiontree dt = new Decisiontree();
        String result = dt.getAResult();

        if(result!=null){
            nextScreen("../Views/ResultPage.fxml");
        }
        else{
            nextScreen("../Views/FifthPage.fxml");
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
            container.getChildren().remove(fourthPane);
        });
        timeline.play();
    }
}
