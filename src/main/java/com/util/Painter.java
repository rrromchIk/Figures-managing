package com.util;

import com.figures.*;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Painter {
    private List<Figure> figures;
    private Pane pane;

    public Painter(Pane pane, List<Figure> list) {
        this.pane = pane;
        this.figures = list;
    }

    public void draw() {
        Painter.clearScene(pane);
        double padding = 10d;
        double currentXPos = padding, currentYPos = padding, nextXPos = 0, nextYPos = 0;
        double lowerYBound = 0;
        for(Figure figure : figures) {
            if(figure instanceof MyCircle myCircle) {
                double diameter = myCircle.getDiameter();
                while (true) {
                    if(currentXPos + diameter + padding <= pane.getWidth()) {
                        if(currentYPos + diameter + padding <= pane.getHeight()) {
                            double radius = myCircle.getRadius();
                            drawCircle(currentXPos + radius, currentYPos + radius, myCircle);
                            currentXPos += diameter + padding;
                            lowerYBound = Math.max(currentYPos + diameter, lowerYBound);
                            break;
                        } else {
                            throw new RuntimeException();
                        }
                    } else {
                        currentXPos = padding;
                        currentYPos = lowerYBound + padding;
                    }
                }
            } else if(figure instanceof MySquare mySquare) {
                double side = mySquare.getSide();
                while (true) {
                    if(currentXPos + side + padding <= pane.getWidth()) {
                        if(currentYPos + side + padding <= pane.getHeight()) {
                            drawSquare(currentXPos, currentYPos, mySquare);
                            currentXPos += side + padding;
                            lowerYBound = Math.max(currentYPos + side, lowerYBound);
                            break;
                        } else {
                            throw new RuntimeException();
                        }
                    } else {
                        currentXPos = padding;
                        currentYPos = lowerYBound + padding;
                    }
                }
            } else if(figure instanceof MyTriangle myTriangle) {
                drawTriangle(currentXPos, currentYPos + myTriangle.getC().getY(),
                        currentXPos + myTriangle.getB().getX(), currentYPos + myTriangle.getC().getY(),
                        currentXPos + myTriangle.getC().getX(), currentYPos, myTriangle);
                currentXPos += (Math.max(myTriangle.getB().getX(), myTriangle.getC().getX())) + padding;
                lowerYBound = Math.max(lowerYBound, myTriangle.getB().getY());
            }
        }
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
