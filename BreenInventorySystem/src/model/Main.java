package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/***
 * Joshua Breen C482 Project
 * Javadoc located in folder: "/Javadoc"
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
        primaryStage.setTitle("Inventory System");
        primaryStage.setScene(new Scene(root, 920, 468));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
