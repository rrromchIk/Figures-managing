import com.figures.*;

import java.io.IOException;
import java.util.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {
    @Test
    public void testAllFigures() throws IOException {
        List<Figure> figures = new ArrayList<>();
        try {
            figures.add(new MyCircle("circle1", 50));
            figures.add(new MySquare("square1", 100));
            figures.add(new MyTriangle("triangle1", 300, 400, 500));
            figures.add(new MyTriangle("triangle2", 400, 101, 300));
            figures.add(new MySquare("square2", 75));
            figures.add(new MyCircle("circle2", 80));
        } catch (IllegalArgumentException e) {
            System.out.println("Bad sides!!!");
            return;
        }

        figures.forEach(System.out::println);
        int area = 2000;

        assertEquals("triangle1", Figure.getMaxAreaFigure(figures).getName());
        Figure.getFiguresWithAreaGreaterThenGiven(figures,area).forEach((i) -> assertTrue(i.getArea() > area));
        assertEquals("square2",  Figure.getMinPerimeterFigure(figures).getName());
        System.out.println("after sorting");
        Figure.sortFiguresByInscribedCircleRadius(figures).forEach(System.out::println);
        assertTrue(Figure.writeToFile(figures,"D:\\text.txt"));
    }

    @Test
    public void testCircle() {
        MyCircle circle = new MyCircle("circle");
        MyCircle circle1 = new MyCircle("circle1", 20d);
        MyCircle circle2 = new MyCircle();
        assertEquals("circle", circle.getName());
        assertEquals(Math.PI * Math.pow(Figure.DEFAULT_SIDE_VALUE, 2), circle.getArea(), 0.01);
        assertEquals(2 * Math.PI * Figure.DEFAULT_SIDE_VALUE, circle.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle.getRadius(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle.getCircumscribedCircleRadius(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle.getInscribedCircleRadius(), 0.01);

        assertEquals("circle1", circle1.getName());
        assertEquals(Math.PI * Math.pow(20d, 2), circle1.getArea(), 0.01);
        assertEquals(2 * Math.PI * 20d, circle1.getPerimeter(), 0.01);
        assertEquals(20d, circle1.getRadius(), 0.01);
        assertEquals(20d, circle1.getCircumscribedCircleRadius(), 0.01);
        assertEquals(20d, circle1.getInscribedCircleRadius(), 0.01);
        assertThrows(IllegalArgumentException.class, () -> new MyCircle("cr", -50));
    }

    @Test
    public void testSquare() {
        MySquare square = new MySquare("square");
        MySquare square1 = new MySquare("square1", 50);

        assertEquals("square", square.getName());
        assertEquals(Math.pow(Figure.DEFAULT_SIDE_VALUE, 2), square.getArea(), 0.01);
        assertEquals(4* Figure.DEFAULT_SIDE_VALUE, square.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, square.getSide(), 0.01);
        assertEquals((Figure.DEFAULT_SIDE_VALUE * Math.sqrt(2)) / 2, square.getCircumscribedCircleRadius(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE / 2d, square.getInscribedCircleRadius(), 0.01);

        assertEquals("square1", square1.getName());
        assertEquals(Math.pow(50d, 2), square1.getArea(), 0.01);
        assertEquals(4 * 50d, square1.getPerimeter(), 0.01);
        assertEquals(50d, square1.getSide(), 0.01);
        assertEquals((50d * Math.sqrt(2)) / 2, square1.getCircumscribedCircleRadius(), 0.01);
        assertEquals(50d / 2d, square1.getInscribedCircleRadius(), 0.01);
        assertThrows(IllegalArgumentException.class, () -> new MySquare("sq", -50));
    }

    @Test
    public void testTriangle() {
        MyTriangle triangle = new MyTriangle("triangle");
        MyTriangle triangle1 = new MyTriangle("triangle1", 300, 400, 500);

        assertEquals("triangle", triangle.getName());
        assertEquals(4330.127018922193d, triangle.getArea(), 0.01);
        assertEquals(300d, triangle.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle.getSideA(), 0.001);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle.getSideB(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle.getSideC(), 0.01);
        assertEquals(86.60d, triangle.getMidLine(), 0.01);
        assertEquals(57.73d, triangle.getCircumscribedCircleRadius(), 0.01);
        assertEquals(28.86d, triangle.getInscribedCircleRadius(), 0.01);

        assertEquals("triangle1", triangle1.getName());
        assertEquals(60000d, triangle1.getArea(), 0.01);
        assertEquals(1200d, triangle1.getPerimeter(), 0.01);
        assertEquals(300d, triangle1.getSideA(), 0.01);
        assertEquals(400d, triangle1.getSideB(), 0.01);
        assertEquals(500d, triangle1.getSideC(), 0.01);
        assertEquals(427.20d, triangle1.getMidLine(), 0.01);
        assertEquals(250d, triangle1.getCircumscribedCircleRadius(), 0.01);
        assertEquals(100d, triangle1.getInscribedCircleRadius(), 0.01);
        assertThrows(IllegalArgumentException.class, () -> new MyTriangle("tr", 100, 50, 200));
        assertThrows(IllegalArgumentException.class, () -> new MyTriangle("tr", -300, 400, 500));
    }
}


