package org.fx.main;

import com.sun.javafx.scene.SceneHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("汉云合快捷鼠标");
        primaryStage.setScene(new Scene(root, 550, 280));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
