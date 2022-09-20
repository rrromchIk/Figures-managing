package com.figures;

import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*
 *
 *   done
 *
 */

public abstract class Figure {
    private final String name;
    private double area;
    private double perimeter;
    private double inscribedCircleRadius;
    private double circumscribedCircleRadius;
    private Color color;
    public static final double DEFAULT_SIDE_VALUE = 100d;

    protected static int counterOfFigures = 1;

    Figure(String name) {
        this.name = name;
        counterOfFigures++;
    }

    public String getName() {
        return name;
    }

    protected void setArea(double area) {
        this.area = area;
    }

    public double getArea() {
        return area;
    }

    protected void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    public double getPerimeter() {
        return perimeter;
    }

    protected void setInscribedCircleRadius(double radius) {
        this.inscribedCircleRadius = radius;
    }

    public double getInscribedCircleRadius() {
        return inscribedCircleRadius;
    }

    protected void setCircumscribedCircleRadius(double radius) {
        this.circumscribedCircleRadius = radius;
    }

    public double getCircumscribedCircleRadius() {
        return circumscribedCircleRadius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String displayInfo() {
        return String.format("Name: %s%nArea: %.2f%nPerimeter: %.2f%n", name, area, perimeter);
    }

    public static Figure getMaxAreaFigure(List<Figure> figures) {
        List<Figure>  list = new ArrayList<>(figures);
        list.sort((f1, f2) -> (int)(f2.getArea() - f1.getArea()));
        return list.get(0);
    }

    public static Figure getMinPerimeterFigure(List<Figure> figures) {
        List<Figure>  list = new ArrayList<>(figures);
        list.sort((f1, f2) -> (int)(f1.getPerimeter() - f2.getPerimeter()));
        return list.get(0);
    }

    public static List<Figure> getFiguresWithAreaGreaterThenGiven(List<Figure> figures, double area) {
        List<Figure> figuresWithGreaterArea = new ArrayList<>();
        figures.forEach(figure -> {if(figure.area > area) figuresWithGreaterArea.add(figure);});
        return figuresWithGreaterArea;
    }

    public static List<Figure> sortFiguresByInscribedCircleRadius(List<Figure> figures) {
        List<Figure> list = new ArrayList<>(figures);
        list.sort((f1, f2) -> (int)(f1.inscribedCircleRadius - f2.inscribedCircleRadius));
        return list;
    }

    public static boolean writeToFile(List<Figure> figures, String url) throws IOException {
        String start = "----------------------------- New adding -----------------------------\n\n";
        String end =   "--------------------------------- End --------------------------------\n\n";
        File file = new File(url);
        if(!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fWriter = new FileWriter(file, true)) {
            fWriter.write(start);
            figures.forEach(figure -> {
                try {
                    fWriter.write(figure.displayInfo());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fWriter.write(end);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static List<Figure> readFiguresFromFile(String uri) {
        File file = new File(uri);
        List<Figure> list = new ArrayList<>();
        if(file.exists()) {
            try (Scanner reader = new Scanner(new FileReader(file))) {
                addToList(reader, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            throw new IllegalArgumentException();
        return list;
    }

    private static void addToList(Scanner reader, List<Figure> list) {
        while (reader.hasNext()) {
            switch (reader.next()) {
                case "triangle" -> list.add(new MyTriangle(reader.next(), Double.parseDouble(reader.next()),
                        Double.parseDouble(reader.next()), Double.parseDouble(reader.next())));
                case "circle" -> list.add(new MyCircle(reader.next(), Double.parseDouble(reader.next())));
                case "square" -> list.add(new MySquare(reader.next(), Double.parseDouble(reader.next())));
            }
        }
    }

    @Override
    public String toString() {
        return displayInfo();
    }
}

