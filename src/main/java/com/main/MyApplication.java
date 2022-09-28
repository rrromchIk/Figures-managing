package com.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class MyApplication extends Application {
    public static final String ICON_PATH = "geometric.png";
    public static final String WINDOW_TITLE = "Figures";
    public static final String PATH_TO_OUTPUT_FILE = "src\\main\\resources\\outputData.txt";
    public static final String PATH_TO_INPUT_FILE = "src\\main\\resources\\inputData.txt";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image(ICON_PATH);
        stage.getIcons().add(image);
        stage.setTitle(WINDOW_TITLE);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

