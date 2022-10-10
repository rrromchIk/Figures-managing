package com.main;

import com.figures.*;
import com.util.Painter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MyController implements Initializable {
    private static final String THERE_ARE_NO_FIGURES = "There are no figures added!";
    private static final String TRY_TO_ADD_ANY_FIGURE = "Try to add any figure :)";
    private static final String ALL_ADDED_FIGURES_DISPLAYED = "↑ Scene: All added figures displayed! :)";
    private final List<Figure> figures = new ArrayList<>();

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField sizeTextField;
    @FXML
    private TextField minPerimeterTextField;
    @FXML
    private TextField maxAreaTextField;
    @FXML
    private TextField givenArea;
    @FXML
    private ComboBox<String> figuresComboBox;
    @FXML
    private ComboBox<String> namesComboBox;
    @FXML
    private Pane pane;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Label sceneStatus;
    @FXML
    private Label writeToFileStatus;

    public void onAddFigureButtonClicked() {
        String name = nameTextField.getText();
        String size = sizeTextField.getText();
        try {
            validateInput(name, size);
            switch (figuresComboBox.getValue()) {
                case "Circle" ->  addToListAndDisplay(new MyCircle(name, Double.parseDouble(size)));
                case "Square" -> addToListAndDisplay(new MySquare(name, Double.parseDouble(size)));
                case "Triangle" -> {
                    String[] sides = size.split(" ");
                    addToListAndDisplay(new MyTriangle(name, Double.parseDouble(sides[0]), Double.parseDouble(sides[1]),
                            Double.parseDouble(sides[2])));
                }
            }
        } catch (IllegalArgumentException e) {
            showAlert("It looks like something went wrong!", e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            showAlert("Bad input", "Write tree side sizes for triangle!");
        }
        clearTextFields();
    }

    private void validateInput(String name, String size) {
        if (figuresComboBox.getValue() == null)
            throw new IllegalArgumentException("Choose figure!!!");
        if(size.isEmpty() || name.isEmpty()) {
            throw new IllegalArgumentException("Fill all text fields!!!");
        }
    }

    public void onReadFromFileButtonClicked() {
        try {
            List<Figure> list = Figure.readFiguresFromFile(MyApplication.PATH_TO_INPUT_FILE);
            list.forEach(this::addToListAndDisplay);
        } catch (RuntimeException e) {
            showAlert("Bad input format!", "Change input data format and try again!");
        }
    }

    private void addToListAndDisplay(Figure figure) {
        figures.add(figure);
        if (displayFigures(figures)) {
            namesComboBox.getItems().add(figure.getName());
            updateSceneStatus(ALL_ADDED_FIGURES_DISPLAYED);
            updateWriteToFileStatus("Write info about figures to the file!");
        } else
            figures.remove(figure);
    }

    public void onWriteToFileButtonCLicked() throws IOException {
        if(!figures.isEmpty()) {
            String path = MyApplication.PATH_TO_OUTPUT_FILE;
            String nameOfFile = path.substring(path.lastIndexOf("\\") + 1);
            Figure.writeToFile(figures, path);
            updateWriteToFileStatus("Info wrote to the file: " + nameOfFile);
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onSortFiguresButtonCLicked() {
        if(!figures.isEmpty()) {
            displayFigures(Figure.sortFiguresByInscribedCircleRadius(figures));
            updateSceneStatus("↑ Scene: Figures sorted by inscribed circle radius! :)");
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onMaxAreaFigureButtonClicked() {
        if(!figures.isEmpty()) {
            Figure figure = Figure.getMaxAreaFigure(figures);
            maxAreaTextField.setText(String.format("%s: %.2f", figure.getName(), figure.getArea()));
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onMinPerimeterFigureButtonClicked() {
        if(!figures.isEmpty()) {
            Figure figure = Figure.getMinPerimeterFigure(figures);
            minPerimeterTextField.setText(String.format("%s: %.2f", figure.getName(), figure.getPerimeter()));
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onFillButtonClicked() {
        if(namesComboBox.getValue() != null) {
            figures.stream()
                            .filter(figure -> figure.getName().equals(namesComboBox.getValue()))
                                    .forEach(figure -> figure.setColor(colorPicker.getValue()));
            displayFigures(figures);
        } else {
            showAlert("Choose figure name!", "If there are no names, " + TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onDisplayAllButtonClicked() {
        if(!figures.isEmpty()) {
            displayFigures(figures);
            updateSceneStatus(ALL_ADDED_FIGURES_DISPLAYED);
        } else {
            showAlert(THERE_ARE_NO_FIGURES, TRY_TO_ADD_ANY_FIGURE);
        }
    }

    public void onClearSceneButtonClicked() {
        figures.clear();
        Painter.clearScene(pane);
        clearTextFields();
        namesComboBox.getItems().clear();
        namesComboBox.setPromptText("Name of figure");
        colorPicker.setValue(Color.WHITE);
        updateSceneStatus("↑ Scene: Cleaned)");
        updateWriteToFileStatus("");
    }

    public void onGreaterThanButtonClicked() {
        String text = givenArea.getText();
        double area;
        try {
            area = Double.parseDouble(text);
        } catch (IllegalArgumentException e) {
            showAlert("It looks like something went wrong!", "Enter numeric value!");
            givenArea.setText("");
            return;
        }
        List<Figure> list = Figure.getFiguresWithAreaGreaterThenGiven(figures, area);
        if(list.isEmpty())
            showAlert("There are no figures with are greater than " + text, "Try again!");
        else {
            displayFigures(list);
            updateSceneStatus("↑ Scene: Figures with are greater than: " +  text + "! :)");
        }
        givenArea.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        figuresComboBox.getItems().addAll("Circle", "Square", "Triangle");
        pane.setBackground(Background.fill(MyApplication.SCENE_BACKGROUND));
    }

    private void clearTextFields() {
        maxAreaTextField.setText("");
        minPerimeterTextField.setText("");
        nameTextField.setText("");
        sizeTextField.setText("");
    }

    private void showAlert(String headerText, String bodyText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(MyApplication.WINDOW_TITLE);
        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);
        ((Stage)alert.getDialogPane()
                .getScene()
                .getWindow())
                .getIcons()
                .add(new Image(MyApplication.ICON_PATH));
        alert.showAndWait();
    }

    private boolean displayFigures(List<Figure> figures) {
        try {
            Painter painter = new Painter(pane, figures);
            painter.draw();
            return true;
        } catch (IllegalStateException e) {
            showAlert("Ups...", e.getMessage());
            return false;
        }
    }

    private void updateSceneStatus(String text) {
        sceneStatus.setText(text);
    }

    private void updateWriteToFileStatus(String text) {
        writeToFileStatus.setText(text);
    }
}