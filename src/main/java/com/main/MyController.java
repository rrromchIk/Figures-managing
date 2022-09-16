package com.main;

import com.figures.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MyController implements Initializable {
    private List<Figure> figures;
    private Map<String, Shape> figuresMap;
    @FXML
    private TextField nameTextField, sizeTextField, minPerimeterTextField, maxAreaTextField;

    @FXML
    private ComboBox<String> figuresComboBox, namesComboBox;

    @FXML
    private Pane pane;

    @FXML
    private ColorPicker colorPicker;

    public void onAddFigureButtonClicked() {
        String name = nameTextField.getText();
        String size = sizeTextField.getText();
        try {
            if (figuresComboBox.getValue() == null || size.isEmpty() || name.isEmpty())
                throw new IllegalArgumentException("Choose figure!");

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
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Bad input!");
        }

        clearTextFields();
    }

    public void onWriteToFileButtonCLicked() throws IOException {
        if(!figures.isEmpty()) {
            Figure.writeToFile(figures, "D:\\test.txt");
        } else
            throw new IllegalArgumentException("There are no figures!");
    }

    public void onSortFiguresButtonCLicked() {
        if(!figures.isEmpty()) {
            Figure.sortFiguresByInscribedCircleRadius(figures).forEach(System.out::println);
        } else
           throw new IllegalArgumentException("There are no figures!");
    }

    public void onMaxAreaFigureButtonClicked() {
        if(!figures.isEmpty()) {
            maxAreaTextField.setText(Figure.getMaxAreaFigure(figures).getName());
        } else
            throw new NoSuchElementException("There are no figures");
    }

    public void onMinPerimeterFigureButtonClicked() {
        if(!figures.isEmpty()) {
            minPerimeterTextField.setText(Figure.getMinPerimeterFigure(figures).getName());
        } else
            throw new NoSuchElementException("There are no figures");
    }

    public void onFillButtonClicked() {
        if(namesComboBox.getValue() == null) {
            //figuresMap.get(namesComboBox.getValue()).setFill(colorPicker.getValue());
            Circle circle = new Circle();
            circle.setRadius(50);
            circle.setCenterX(50);
            circle.setCenterY(50);
            circle.setFill(colorPicker.getValue());
            pane.getChildren().add(circle);
        } else
            throw new IllegalArgumentException("choose name!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        figures = new ArrayList<>();
        figuresMap = new HashMap<>();
        figuresComboBox.getItems().addAll("Circle", "Square", "Triangle");
    }

    private void clearTextFields() {
        maxAreaTextField.setText("");
        minPerimeterTextField.setText("");
        nameTextField.setText("");
        sizeTextField.setText("");
    }
}