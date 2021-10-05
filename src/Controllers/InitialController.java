package Controllers;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitialController {

    @FXML
    private Button next;

    @FXML
    private AnchorPane homePane;

    @FXML
    private StackPane container;


    @FXML
    public void GoToNextPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/SecondPage.fxml"));
        Scene scene = next.getScene();
        root.translateXProperty().set(scene.getHeight());
        container.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(.5),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( e->{
            container.getChildren().remove(homePane);
        });
        timeline.play();
    }
}
