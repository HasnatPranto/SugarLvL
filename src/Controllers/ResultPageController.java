package Controllers;

import Algorithm.CrossValidation;
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
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

public class ResultPageController {

    @FXML
    Label display_result;
    @FXML
    Button home_button_res;
    @FXML
    AnchorPane resultPane;

    @FXML BarChart barChart;
    @FXML
    NumberAxis yAxis;
    @FXML
    CategoryAxis xAxis;
    @FXML
    public void initialize(){

        CrossValidation x = new CrossValidation();

        if(CrossValidation.result.equals("")){
            display_result.setTextFill(Color.BLUE);
            display_result.setText("50/50!");
        }
        else {
            if (CrossValidation.result.equals("Negative"))
                display_result.setTextFill(Color.GREEN);
            else
                display_result.setTextFill(Color.RED);

            display_result.setText(CrossValidation.result.toString() + "!");
        }

        xAxis.setLabel("metrics");

        yAxis.setLabel("value");

        XYChart.Series<String, Number> series = new XYChart.Series();
        XYChart.Series<String, Number> series1 = new XYChart.Series();
        XYChart.Series<String, Number> series2 = new XYChart.Series();
        XYChart.Series<String, Number> series3 = new XYChart.Series();
        series.setName("Accuracy");series1.setName("Precision");series2.setName("Recall");series3.setName("F1-score");
        series.getData().add(new XYChart.Data("Accuracy",CrossValidation.accuracy));
        series1.getData().add(new XYChart.Data("Precision",CrossValidation.precision));
        series2.getData().add(new XYChart.Data("Recall",CrossValidation.recall));
        series3.getData().add(new XYChart.Data("F1-score",CrossValidation.f1));

        barChart.getData().addAll(series,series1,series2,series3);
    }

    @FXML
    public void ReturnHome(ActionEvent event) throws IOException {
        Patient.refresh();
        Parent root = FXMLLoader.load(getClass().getResource("../Views/sample.fxml"));
        Scene scene = home_button_res.getScene();
        root.translateYProperty().set(scene.getHeight());

        StackPane container = (StackPane) scene.getRoot();
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(),0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(.4),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( e->{
            container.getChildren().remove(resultPane);
        });
        timeline.play();
    }
}
