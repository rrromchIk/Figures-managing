package com.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image("D:\\проекти Java\\javaFXLerning\\src\\main\\resources\\geometric.png");
        stage.getIcons().add(image);
        stage.setTitle("Figures");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

