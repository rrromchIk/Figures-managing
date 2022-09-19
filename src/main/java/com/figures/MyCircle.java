package com.figures;

//- коло(назва, площа, довжина, радіус, діаметр)

/*
 *
 *   done
 *
 */

import javafx.scene.paint.Color;

public class MyCircle extends Figure {
    private double radius;
    private static final Color DEFAULT_CIRCLE_COLOR = Color.YELLOW;

    public MyCircle() {
        this("circle" + counterOfFigures);
    }

    public MyCircle(String name) {
        this(name, DEFAULT_SIDE_VALUE);
    }

    public MyCircle(String name, double radius) {
        super(name);
        setRadius(radius);
        setColor(DEFAULT_CIRCLE_COLOR);
    }

    public void setRadius(double radius) {
        if(radius > 0.00001) {
            this.radius = radius;
            setArea(Math.PI * radius * radius);
            setPerimeter(2 * Math.PI * radius);
            setCircumscribedCircleRadius(radius);
            setInscribedCircleRadius(radius);
        } else
            throw new IllegalArgumentException("Radius can not be less/equal 0!");
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return radius * 2;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + String.format("Radius: %.2f%nDiameter: %.2f%n%n", radius, radius * 2);
    }
}

