package com.figures;

//- трикутник(назва, площа, периметр, довжина сторін, радіус вписаного та описаного кола, довжина середньої лінії)

/*
 *
 *   done
 *
 */

import javafx.scene.paint.Color;

public class MyTriangle extends Figure {
    private double sideA;
    private double sideB;
    private double sideC;
    private double midLine;
    private Point pointA;
    private Point pointB;
    private Point pointC;
    private static final Color DEFAULT_TRIANGLE_COLOR = Color.RED;

    public MyTriangle() {
        this("triangle" + counterOfFigures);
    }

    public MyTriangle(String name) {
        this(name, DEFAULT_SIDE_VALUE, DEFAULT_SIDE_VALUE, DEFAULT_SIDE_VALUE);
    }

    public MyTriangle(String name, double sideA, double sideB, double sideC) {
        super(name);
        setParameters(sideA, sideB, sideC);
        setColor(DEFAULT_TRIANGLE_COLOR);
    }

    public void setParameters(double a, double b, double c) {
        if(a > 0.00001 && b > 0.00001 && c > 0.00001 && checkForExistence(a, b, c)) {
            this.sideA = a;
            this.sideB = b;
            this.sideC = c;
            setMidLine(a, b, c);
            setPoints(a, b, c);
            setPerimeter(a + b + c);
            setArea((1.0 / 2.0) * Math.abs((pointA.getX() - pointC.getX()) * (pointB.getY() - pointA.getY()) -
                    (pointA.getX() - pointB.getX()) * (pointC.getY() - pointA.getY())));
            setCircumscribedCircleRadius(calculateCircumscribedCircleRadius(a, b, c));
            setInscribedCircleRadius(calculateInscribedCircleRadius());
        } else {
            throw new IllegalArgumentException("Bad sides!");
        }
    }

    private void setPoints(double a, double b, double c) {
        double x = (b * b - c * c + a * a) / (2 * a);
        double y = Math.sqrt(Math.pow(b, 2) - x * x);
        this.pointA = new Point(0, 0);
        this.pointB = new Point(a, 0);
        this.pointC = new Point(x, y);
    }

    private void setMidLine(double a, double b, double c) {
        this.midLine = Math.sqrt((2 * b * b + 2 * c * c - a * a) / 4);
    }

    public double getSideA() {
        return sideA;
    }

    public double getSideB() {
        return sideB;
    }

    public double getSideC() {
        return sideC;
    }

    public double getMidLine() {
        return midLine;
    }

    public Point getA() {
        return pointA;
    }

    public Point getB() {
        return pointB;
    }

    public Point getC() {
        return pointC;
    }

    private boolean checkForExistence(double a, double b, double c) {
        return (a < (b + c) && b < (c + a) && c < (a + b));
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + String.format("Side A: %.2f%nSide B: %.2f%nSide C: %.2f%n" +
                        "Inscribed circle radius: %.2f%nCircumscribed circle radius: %.2f%nMedian: %.2f%n%n", sideA,
                sideB, sideC, getInscribedCircleRadius(), getCircumscribedCircleRadius(), midLine);
    }

    public static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public void setX(double x) {
            this.x = x;
        }
        public void setY(double y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this) return true;
            if(obj == null || getClass() != obj.getClass()) return false;
            final Point point = (Point) obj;
            return Double.valueOf(this.x).equals(point.x)
                    && Double.valueOf(this.y).equals(point.y);
        }
    }

    private double calculateCircumscribedCircleRadius(double a, double b, double c) {
        return ((a * b * c) / (4 * getArea()));
    }

    private double calculateInscribedCircleRadius() {
        return ((2 * getArea()) / getPerimeter());
    }
}

