package com.main;

import com.figures.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MyController implements Initializable {

    private static final String THERE_ARE_NO_FIGURES = "There are no figures added!";
    private static final String TRY_TO_ADD_ANY_FIGURE = "Try to add any figure :)";
    private List<Figure> figures;
    //private Map<String, Shape> figuresMap;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField sizeTextField;
    @FXML
    private TextField minPerimeterTextField;
    @FXML
    private TextField maxAreaTextField;
    @FXML
    private ComboBox<String> figuresComboBox;
    @FXML
    private ComboBox<String> namesComboBox;

    @FXML
    private Pane pane;

    @FXML
    private ColorPicker colorPicker;

    public void onAddFigureButtonClicked() {
        String name = nameTextField.getText();
        String size = sizeTextField.getText();
        try {
            if (figuresComboBox.getValue() == null)
                throw new IllegalArgumentException("Choose figure!!!");
            if(size.isEmpty() || name.isEmpty()) {
                throw new IllegalArgumentException("Fill all text fields!!!");
            }

            switch (figuresComboBox.getValue()) {
                case "Circle" ->  figures.add(new MyCircle(name, Double.parseDouble(size)));
                case "Square" -> figures.add(new MySquare(name, Double.parseDouble(size)));
                case "Triangle" -> {
                    String[] sides = size.split(" ");
                    figures.add(new MyTriangle(name, Double.parseDouble(sides[0]), Double.parseDouble(sides[1]),
                            Double.parseDouble(sides[2])));
                }
            }
            namesComboBox.getItems().add(name);
        } catch (IllegalArgumentException e) {
            showAlert("It looks like something went wrong!", e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            showAlert("Bad input", "Write tree side sizes for triangle!");
        }

        clearTextFields();
    }

    public void onWriteToFileButtonCLicked() throws IOException {
        if(!figures.isEmpty()) {
            Figure.writeToFile(figures, "D:\\test.txt");
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onSortFiguresButtonCLicked() {
        if(!figures.isEmpty()) {
            Figure.sortFiguresByInscribedCircleRadius(figures).forEach(System.out::println);
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onMaxAreaFigureButtonClicked() {
        if(!figures.isEmpty()) {
            maxAreaTextField.setText(Figure.getMaxAreaFigure(figures).getName());
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
            //throw new NoSuchElementException("There are no figures");
    }

    public void onMinPerimeterFigureButtonClicked() {
        if(!figures.isEmpty()) {
            minPerimeterTextField.setText(Figure.getMinPerimeterFigure(figures).getName());
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
            //throw new NoSuchElementException("There are no figures");
    }

    public void onFillButtonClicked() {
        if(namesComboBox.getValue() == null) {
            //figuresMap.get(namesComboBox.getValue()).setFill(colorPicker.getValue());
            Circle circle = new Circle();
            circle.setRadius(50);
            circle.setCenterX(50);
            circle.setCenterY(50);
            circle.setFill(colorPicker.getValue());
            circle.setOnMouseMoved(event -> {
                if(circle.getFill().equals(Color.BLACK)) {
                    circle.setFill(Color.BLUEVIOLET);
                } else
                    circle.setFill(Color.BLACK);
            });
            pane.getChildren().add(circle);
        } else {
            showAlert("Choose figure name!",
                    "If there are no names, " + TRY_TO_ADD_ANY_FIGURE);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        figures = new ArrayList<>();
        //figuresMap = new HashMap<>();
        figuresComboBox.getItems().addAll("Circle", "Square", "Triangle");
    }

    private void clearTextFields() {
        maxAreaTextField.setText("");
        minPerimeterTextField.setText("");
        nameTextField.setText("");
        sizeTextField.setText("");
    }

    private void showAlert(String headerText, String bodyText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ups..");
        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);
        alert.showAndWait();
    }
}