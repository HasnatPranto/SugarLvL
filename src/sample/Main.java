package sample;

import Algorithm.Decisiontree;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       Decisiontree dt = new Decisiontree();
       dt.init("./src/Data/diabetes_data.csv");

        Parent root = FXMLLoader.load(getClass().getResource("../Views/sample.fxml"));
        primaryStage.setTitle("SugarLvL");
        primaryStage.setScene(new Scene(root, 630, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
