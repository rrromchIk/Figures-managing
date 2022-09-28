package com.figures;

// - квадрат(назва, площа, периметр, довжина сторін, радіус вписаного та описаного кола)

/*
 *
 *   done
 *
 */

import javafx.scene.paint.Color;

public class MySquare extends Figure {
    private double side;
    private static final Color DEFAULT_SQUARE_COLOR = Color.BLUE;

    public MySquare() {
        this("square" + counterOfFigures);
    }

    public MySquare(String name) {
        this(name, DEFAULT_SIDE_VALUE);
    }

    public MySquare(String name, double side) {
        super(name);
        setParameters(side);
        setColor(DEFAULT_SQUARE_COLOR);
    }

    public void setParameters(double side) {
        if(side > 0.00001) {
            this.side = side;
            setArea(side * side);
            setPerimeter(4 * side);
            setInscribedCircleRadius(side / 2);
            setCircumscribedCircleRadius((side * Math.sqrt(2)) / 2);
        } else
            throw new IllegalArgumentException("Side can not be less/equal 0!");
    }

    public double getSide() {
        return side;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() +
                String.format("Sides: %.2f%nInscribed circle radius: %.2f%nCircumscribed circle radius: %.2f%n%n",
                        side, getInscribedCircleRadius(), getCircumscribedCircleRadius());
    }
}

