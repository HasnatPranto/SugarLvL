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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class ThirdPageController {

    @FXML
    Button sb_button_one, home_button;

    @FXML private RadioButton puria_Yes,puria_No,pdipsia_Yes,pdipsia_No, pphagia_Yes,pphagia_No;
    @FXML
    private AnchorPane thirdPane;

    private ToggleGroup puria, pdipsia, pphagia;
    private CrossValidation xval;
    private ResultPageController rs;

    @FXML
    private void initialize() {

        xval = new CrossValidation();
        puria = new ToggleGroup();
        pdipsia = new ToggleGroup();
        pphagia = new ToggleGroup();
        puria_Yes.setToggleGroup(puria);
        puria_No.setToggleGroup(puria);
        pdipsia_Yes.setToggleGroup(pdipsia);
        pdipsia_No.setToggleGroup(pdipsia);
        pphagia_Yes.setToggleGroup(pphagia);
        pphagia_No.setToggleGroup(pphagia);

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
            container.getChildren().remove(thirdPane);
        });
        timeline.play();
    }

    private String getAnswer(String label){

        String[] tmp = label.split("_");
        return tmp[0];
    }

    @FXML public void processData() throws IOException {

        RadioButton sel_puria = (RadioButton) puria.getSelectedToggle();
        RadioButton sel_pdipsia = (RadioButton) pdipsia.getSelectedToggle();
        RadioButton sel_pphagia = (RadioButton) pphagia.getSelectedToggle();

        Patient.symptoms[2] = getAnswer(sel_puria.getText());
        Patient.symptoms[3] = getAnswer(sel_pdipsia.getText());
        Patient.symptoms[6] = getAnswer(sel_pphagia.getText());

        Decisiontree dt = new Decisiontree();
        String result = dt.getAResult();

        System.out.println(result);

        if(result!=null){
            nextScreen("../Views/ResultPage.fxml");
        }
        else{
            nextScreen("../Views/FourthPage.fxml");
        }

    }

    public void nextScreen(String path) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = sb_button_one.getScene();
        root.translateXProperty().set(scene.getHeight());

        StackPane container = (StackPane) scene.getRoot();
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( e->{
            container.getChildren().remove(thirdPane);
        });
        timeline.play();
    }
}
