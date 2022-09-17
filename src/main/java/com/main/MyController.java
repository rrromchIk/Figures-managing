package com.main;

import com.figures.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MyController implements Initializable {

    private static final String THERE_ARE_NO_FIGURES = "There are no figures added!";
    private static final String TRY_TO_ADD_ANY_FIGURE = "Try to add any figure :)";
    private final List<Figure> figures = new ArrayList<>();
    private final Map<String, Shape> figuresMap = new HashMap<>();

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
            displayFigures(figures);
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
            displayFigures(Figure.sortFiguresByInscribedCircleRadius(figures));
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
    }

    public void onMinPerimeterFigureButtonClicked() {
        if(!figures.isEmpty()) {
            minPerimeterTextField.setText(Figure.getMinPerimeterFigure(figures).getName());
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onFillButtonClicked() {
        if(namesComboBox.getValue() != null) {
            figuresMap.get(namesComboBox.getValue()).setFill(colorPicker.getValue());
        } else {
            showAlert("Choose figure name!", "If there are no names, " + TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onDisplayAllButtonClicked() {
        if(!figures.isEmpty()) {
            displayFigures(figures);
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    private void displayFigures(List<Figure> figures) {
        pane.getChildren().forEach(node -> node.setVisible(false));
        double padding = 10;
        final double[] rightMostX = {padding};
        final double[] downMostY = {padding};
        figures.forEach(figure -> {
            if(figure instanceof MyCircle myCircle) {
                Circle circle = new Circle(myCircle.getRadius());
                circle.setCenterX(rightMostX[0] + myCircle.getRadius());
                circle.setCenterY(downMostY[0] + myCircle.getRadius());
                circle.setFill(Color.RED);
                Tooltip.install(circle, new Tooltip(myCircle.displayInfo()));
                figuresMap.put(myCircle.getName(), circle);
                pane.getChildren().add(circle);
                rightMostX[0] += myCircle.getDiameter() + padding;
            } else if(figure instanceof MySquare mySquare) {
                Rectangle square = new Rectangle();
                square.setWidth(mySquare.getSide());
                square.setHeight(mySquare.getSide());
                square.setX(rightMostX[0]);
                square.setY(downMostY[0]);
                square.setFill(Color.GREEN);
                Tooltip.install(square, new Tooltip(mySquare.displayInfo()));
                figuresMap.put(mySquare.getName(), square);
                pane.getChildren().add(square);
                rightMostX[0] += mySquare.getSide() + padding;
            } else if(figure instanceof MyTriangle myTriangle) {
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(rightMostX[0], downMostY[0] + myTriangle.getC().getY(),
                        rightMostX[0] + myTriangle.getB().getX(), downMostY[0] + myTriangle.getC().getY(),
                        rightMostX[0] + myTriangle.getC().getX(), downMostY[0]);
                triangle.setFill(Color.BLUE);
                Tooltip.install(triangle, new Tooltip(myTriangle.displayInfo()));
                figuresMap.put(myTriangle.getName(), triangle);
                pane.getChildren().add(triangle);
                rightMostX[0] += (myTriangle.getB().getX() > myTriangle.getC().getX() ? myTriangle.getB().getX() :
                                        myTriangle.getC().getX()) + padding;
            }
        });
    }
}