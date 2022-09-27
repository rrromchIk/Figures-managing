import com.figures.*;
import java.io.IOException;
import java.util.*;
import javafx.scene.paint.Color;
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
            System.out.println(e.getMessage());
            return;
        }

        figures.forEach(System.out::println);
        int area = 2000;

        assertEquals("triangle1", Figure.getMaxAreaFigure(figures).getName());
        Figure.getFiguresWithAreaGreaterThenGiven(figures,area).forEach((i) -> assertTrue(i.getArea() > area));
        assertEquals("square2",  Figure.getMinPerimeterFigure(figures).getName());
        System.out.println("after sorting");
        Figure.sortFiguresByInscribedCircleRadius(figures).forEach(System.out::println);
        assertTrue(Figure.writeToFile(figures,"src\\main\\resources\\outputData.txt"));
    }

    @Test
    public void testCircle() {
        MyCircle circle1 = new MyCircle("circle");
        MyCircle circle2 = new MyCircle("circle1", 20d);
        MyCircle circle = new MyCircle();

        assertEquals("circle21", circle.getName());
        assertEquals(Math.PI * Math.pow(Figure.DEFAULT_SIDE_VALUE, 2), circle.getArea(), 0.01);
        assertEquals(2 * Math.PI * Figure.DEFAULT_SIDE_VALUE, circle.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle.getRadius(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE * 2, circle.getDiameter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle.getCircumscribedCircleRadius(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle.getInscribedCircleRadius(), 0.01);

        assertEquals(Color.YELLOW, circle.getColor());
        circle.setColor(Color.BLACK);
        assertEquals(Color.BLACK, circle.getColor());


        assertEquals("circle", circle1.getName());
        assertEquals(Math.PI * Math.pow(Figure.DEFAULT_SIDE_VALUE, 2), circle1.getArea(), 0.01);
        assertEquals(2 * Math.PI * Figure.DEFAULT_SIDE_VALUE, circle1.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle1.getRadius(), 0.01);
        assertEquals(circle1.getRadius() * 2, circle1.getDiameter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle1.getCircumscribedCircleRadius(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, circle1.getInscribedCircleRadius(), 0.01);

        assertEquals(Color.YELLOW, circle1.getColor());
        circle1.setColor(Color.BLACK);
        assertEquals(Color.BLACK, circle1.getColor());


        assertEquals("circle1", circle2.getName());
        assertEquals(Math.PI * Math.pow(20d, 2), circle2.getArea(), 0.01);
        assertEquals(2 * Math.PI * 20d, circle2.getPerimeter(), 0.01);
        assertEquals(20d, circle2.getRadius(), 0.01);
        assertEquals(circle2.getRadius() * 2, circle2.getDiameter(), 0.01);
        assertEquals(20d, circle2.getCircumscribedCircleRadius(), 0.01);
        assertEquals(20d, circle2.getInscribedCircleRadius(), 0.01);
        assertThrows(IllegalArgumentException.class, () -> new MyCircle("cr", -50));

        assertEquals(Color.YELLOW, circle2.getColor());
        circle2.setColor(Color.BLACK);
        assertEquals(Color.BLACK, circle2.getColor());

    }

    @Test
    public void testSquare() {
        MySquare square = new MySquare();
        MySquare square1 = new MySquare("square");
        MySquare square2 = new MySquare("square1", 50);


        assertEquals("square23", square.getName());
        assertEquals(Math.pow(Figure.DEFAULT_SIDE_VALUE, 2), square.getArea(), 0.01);
        assertEquals(4* Figure.DEFAULT_SIDE_VALUE, square.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, square.getSide(), 0.01);
        assertEquals((Figure.DEFAULT_SIDE_VALUE * Math.sqrt(2)) / 2, square.getCircumscribedCircleRadius(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE / 2d, square.getInscribedCircleRadius(), 0.01);

        assertEquals(Color.BLUE, square.getColor());
        square.setColor(Color.BLACK);
        assertEquals(Color.BLACK, square.getColor());


        assertEquals("square", square1.getName());
        assertEquals(Math.pow(Figure.DEFAULT_SIDE_VALUE, 2), square1.getArea(), 0.01);
        assertEquals(4* Figure.DEFAULT_SIDE_VALUE, square1.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, square1.getSide(), 0.01);
        assertEquals((Figure.DEFAULT_SIDE_VALUE * Math.sqrt(2)) / 2, square1.getCircumscribedCircleRadius(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE / 2d, square1.getInscribedCircleRadius(), 0.01);

        assertEquals(Color.BLUE, square1.getColor());
        square1.setColor(Color.BLACK);
        assertEquals(Color.BLACK, square1.getColor());


        assertEquals("square1", square2.getName());
        assertEquals(Math.pow(50d, 2), square2.getArea(), 0.01);
        assertEquals(4 * 50d, square2.getPerimeter(), 0.01);
        assertEquals(50d, square2.getSide(), 0.01);
        assertEquals((50d * Math.sqrt(2)) / 2, square2.getCircumscribedCircleRadius(), 0.01);
        assertEquals(50d / 2d, square2.getInscribedCircleRadius(), 0.01);
        assertThrows(IllegalArgumentException.class, () -> new MySquare("sq", -50));

        assertEquals(Color.BLUE, square2.getColor());
        square2.setColor(Color.BLACK);
        assertEquals(Color.BLACK, square2.getColor());

    }

    @Test
    public void testTriangle() {
        MyTriangle triangle = new MyTriangle();
        MyTriangle triangle1 = new MyTriangle("triangle");
        MyTriangle triangle2 = new MyTriangle("triangle1", 300, 400, 500);

        assertEquals("triangle1", triangle.getName());
        assertEquals(4330.127018922193d, triangle.getArea(), 0.01);
        assertEquals(300d, triangle.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle.getSideA(), 0.001);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle.getSideB(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle.getSideC(), 0.01);
        assertEquals(86.60d, triangle.getMidLine(), 0.01);
        assertEquals(57.73d, triangle.getCircumscribedCircleRadius(), 0.01);
        assertEquals(28.86d, triangle.getInscribedCircleRadius(), 0.01);

        assertEquals(Color.RED, triangle.getColor());
        triangle.setColor(Color.BLACK);
        assertEquals(Color.BLACK, triangle.getColor());


        assertEquals("triangle", triangle1.getName());
        assertEquals(4330.127018922193d, triangle1.getArea(), 0.01);
        assertEquals(300d, triangle1.getPerimeter(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle1.getSideA(), 0.001);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle1.getSideB(), 0.01);
        assertEquals(Figure.DEFAULT_SIDE_VALUE, triangle1.getSideC(), 0.01);
        assertEquals(86.60d, triangle1.getMidLine(), 0.01);
        assertEquals(57.73d, triangle1.getCircumscribedCircleRadius(), 0.01);
        assertEquals(28.86d, triangle1.getInscribedCircleRadius(), 0.01);

        assertEquals(Color.RED, triangle1.getColor());
        triangle1.setColor(Color.BLACK);
        assertEquals(Color.BLACK, triangle1.getColor());


        assertEquals("triangle1", triangle2.getName());
        assertEquals(60000d, triangle2.getArea(), 0.01);
        assertEquals(1200d, triangle2.getPerimeter(), 0.01);
        assertEquals(300d, triangle2.getSideA(), 0.01);
        assertEquals(400d, triangle2.getSideB(), 0.01);
        assertEquals(500d, triangle2.getSideC(), 0.01);
        assertEquals(427.20d, triangle2.getMidLine(), 0.01);
        assertEquals(250d, triangle2.getCircumscribedCircleRadius(), 0.01);
        assertEquals(100d, triangle2.getInscribedCircleRadius(), 0.01);
        assertThrows(IllegalArgumentException.class, () -> new MyTriangle("tr", 100, 50, 200));
        assertThrows(IllegalArgumentException.class, () -> new MyTriangle("tr", -300, 400, 500));

        assertEquals(Color.RED, triangle2.getColor());
        triangle2.setColor(Color.BLACK);
        assertEquals(Color.BLACK, triangle2.getColor());
    }

    @Test
    public void testReadFromFile() {
        assertThrows(NoSuchElementException.class,
                () -> Figure.readFiguresFromFile("src\\main\\resources\\testFile1.txt"));
        assertThrows(IllegalArgumentException.class, () -> Figure.readFiguresFromFile(""));
        assertFalse(Figure.readFiguresFromFile("src\\main\\resources\\testFile2.txt").isEmpty());
        Figure.readFiguresFromFile("src\\main\\resources\\testFile2.txt").forEach(System.out::println);
    }
}


