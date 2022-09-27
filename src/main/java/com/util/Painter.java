package com.util;

import com.figures.*;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Painter {
    private final List<Figure> figures;
    private final Pane pane;
    private static final int PADDING = 10;
    private static final String EXCEPTION_MESSAGE = "No more space!\nTry to increase your window size " +
                                                    "or delete current figures!";
    private double currentXPos = PADDING;
    private double currentYPos = PADDING;
    private double lowerYBound = 0;

    public Painter(Pane pane, List<Figure> list) {
        this.pane = pane;
        this.figures = list;
    }

    public void draw() {
        Painter.clearScene(pane);

        for(Figure figure : figures) {
            if(figure instanceof MyCircle myCircle) {
                checkBounds(myCircle.getDiameter());
                drawCircle(currentXPos + myCircle.getRadius(), currentYPos + myCircle.getRadius(), myCircle);
                increaseBounds(myCircle.getDiameter());
            } else if(figure instanceof MySquare mySquare) {
                checkBounds(mySquare.getSide());
                drawSquare(currentXPos, currentYPos, mySquare);
                increaseBounds(mySquare.getSide());
            } else if(figure instanceof MyTriangle myTriangle) {
                drawTriangle(currentXPos, currentYPos + myTriangle.getC().getY(),
                        currentXPos + myTriangle.getB().getX(), currentYPos + myTriangle.getC().getY(),
                        currentXPos + myTriangle.getC().getX(), currentYPos, myTriangle);
                currentXPos += (Math.max(myTriangle.getB().getX(), myTriangle.getC().getX())) + PADDING;
                lowerYBound = Math.max(lowerYBound, myTriangle.getB().getY());
            }
        }
    }

    private void checkBounds(double offset) {
        if(offset > pane.getWidth())
            throw new IllegalStateException(EXCEPTION_MESSAGE);

        while (true) {
            if(currentXPos + offset + PADDING <= pane.getWidth()) {
                if(currentYPos + offset + PADDING <= pane.getHeight()) {
                    break;
                } else {
                    throw new IllegalStateException(EXCEPTION_MESSAGE);
                }
            } else {
                currentXPos = PADDING;
                currentYPos = lowerYBound + PADDING;
            }
        }
    }

    private void increaseBounds(double offset) {
        currentXPos += offset + PADDING;
        lowerYBound = Math.max(currentYPos + offset, lowerYBound);
    }

    private void drawCircle(double x, double y, MyCircle myCircle) {
        Circle circle = new Circle(myCircle.getRadius());
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setFill(myCircle.getColor());
        Tooltip.install(circle, new Tooltip(myCircle.displayInfo()));
        pane.getChildren().add(circle);
    }

    private void drawSquare(double x, double y, MySquare mySquare) {
        Rectangle square = new Rectangle();
        square.setWidth(mySquare.getSide());
        square.setHeight(mySquare.getSide());
        square.setX(x);
        square.setY(y);
        square.setFill(mySquare.getColor());
        Tooltip.install(square, new Tooltip(mySquare.displayInfo()));
        pane.getChildren().add(square);
    }

    private void drawTriangle(double ax, double ay, double bx, double by, double cx, double cy,
                              MyTriangle myTriangle) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(ax, ay, bx, by, cx, cy);
        triangle.setFill(myTriangle.getColor());
        Tooltip.install(triangle, new Tooltip(myTriangle.displayInfo()));
        pane.getChildren().add(triangle);
    }

    public static void clearScene(Pane pane) {
        pane.getChildren().forEach(node -> node.setVisible(false));
    }
}
