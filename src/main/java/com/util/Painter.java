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
                drawCircle(myCircle);
                increaseBounds(myCircle.getDiameter(), myCircle.getDiameter());
            } else if(figure instanceof MySquare mySquare) {
                checkBounds(mySquare.getSide());
                drawSquare(mySquare);
                increaseBounds(mySquare.getSide(), mySquare.getSide());
            } else if(figure instanceof MyTriangle myTriangle) {
                double offset = getTriangleOffset(myTriangle);
                checkBounds(offset);
                drawTriangle(myTriangle);
                increaseBounds(offset, currentYPos + myTriangle.getC().getY());
            }
        }
    }

    private double getTriangleOffset(MyTriangle triangle) {
        double cX = triangle.getC().getX();
        double bX = triangle.getB().getX();
        double offset = cX < 0 ? Math.abs(cX) + bX: bX;
        return Math.max(offset, cX);
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

    private void increaseBounds(double offsetX, double offsetY) {
        currentXPos += offsetX + PADDING;
        lowerYBound = Math.max(currentYPos + offsetY, lowerYBound);
    }

    private void drawCircle(MyCircle myCircle) {
        Circle circle = new Circle(myCircle.getRadius());
        circle.setCenterX(currentXPos + myCircle.getRadius());
        circle.setCenterY(currentYPos + myCircle.getRadius());
        circle.setFill(myCircle.getColor());
        Tooltip.install(circle, new Tooltip(myCircle.displayInfo()));
        pane.getChildren().add(circle);
    }

    private void drawSquare(MySquare mySquare) {
        Rectangle square = new Rectangle();
        square.setWidth(mySquare.getSide());
        square.setHeight(mySquare.getSide());
        square.setX(currentXPos);
        square.setY(currentYPos);
        square.setFill(mySquare.getColor());
        Tooltip.install(square, new Tooltip(mySquare.displayInfo()));
        pane.getChildren().add(square);
    }

    private void drawTriangle(MyTriangle myTriangle) {
        double ax = currentXPos;
        double ay = currentYPos + myTriangle.getC().getY();
        double bx = currentXPos + myTriangle.getB().getX();
        double by = currentYPos + myTriangle.getC().getY();
        double cx = currentXPos + myTriangle.getC().getX();
        double cy = currentYPos;
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
