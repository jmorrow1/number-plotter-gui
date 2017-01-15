package number_plotter_gui;

import curves.Curve;
import curves.Diamond;
import curves.Point;
import curves.Triangle;
import curves.Typewriter;
import curves.UlamSpiral;
import draw_modes.DrawMode;
import draw_modes.ToChar;
import draw_modes.ToImage;
import draw_modes.ToShape;
import geom.Rect;
import gui.GUI;
import int_properties.IntProperty;
import int_properties.IntSequence;
import int_properties.Multiples;
import int_properties.Integers;
import int_properties.Powers;
import int_properties.IsPrime;
import int_properties.RandomInt;
import processing.core.PApplet;

/**
 * 
 * The entry point for the application. The window that displays the output of
 * the system.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class App extends PApplet implements NumberPlotterControllable {

    public static void main(String[] args) {
        PApplet.main("number_plotter_gui.App");
    }

    // The current config.
    private IntSequence sequence;
    private Curve curve;
    private IntProperty property;
    private DrawMode drawMode;

    // All possible sequences
    private Integers integers;
    private Multiples multiples;
    private Powers powers;

    // All possible curves
    private Diamond diamond;
    private UlamSpiral ulamSpiral;
    private Typewriter typewriter;
    private Triangle triangle;

    // All possible properties
    private Multiples isDivisibleBy;
    private Powers isAnyPower;
    private RandomInt random;
    private IsPrime isPrime;

    // All possible draw modes
    private ToChar toChar;
    private ToImage toImg;
    private ToShape toShape;

    // points
    private Point[] pts = new Point[40000];
    private float cellSize = 3;
    private float numberSize = 4;

    /*****************
     ***** Setup *****
     *****************/

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        surface.setResizable(true);

        // init all possible sequences
        integers = Integers.instance;
        multiples = new Multiples(2);
        powers = new Powers(2);

        // init all possible curves
        diamond = new Diamond(150, 2);
        ulamSpiral = new UlamSpiral();
        typewriter = new Typewriter(200);
        triangle = new Triangle(2);

        // init all possible properties
        isDivisibleBy = new Multiples(2);
        isAnyPower = new Powers(2);
        random = new RandomInt(2);
        isPrime = IsPrime.instance;

        // init all possible draw modes
        toShape = new ToShape(ToShape.CIRCLE, 1, 1, 0xffffffff, 0, 200, 200, false);
        toChar = new ToChar("x.", '?');

        // init current settings
        sequence = integers;
        curve = diamond;
        property = isPrime;
        drawMode = toShape;

        new GUI(this);

        noLoop();
    }

    /**************************
     ***** Event Handling *****
     **************************/

    public void setSequence(String name) {
        switch (name) {
        case "Integers":
            sequence = integers;
            break;
        case "Multiples":
            sequence = multiples;
            break;
        case "Powers":
            sequence = powers;
            break;
        default:
            System.err.println("Don't know any sequence by the name of " + name);
            break;
        }
        redraw();
    }

    public void setCurve(String name) {
        switch (name) {
        case "Diamond":
            curve = diamond;
            break;
        case "Ulam Spiral":
            curve = ulamSpiral;
            break;
        case "Typewriter":
            curve = typewriter;
            break;
        case "Triangle":
            curve = triangle;
            break;
        default:
            System.err.println("Don't know any curve by the name of " + name);
            break;
        }
        redraw();
    }

    public void setProperty(String name) {
        switch (name) {
        case "Divisible by":
            property = isDivisibleBy;
            break;
        case "Any Power":
            property = isAnyPower;
            break;
        case "Random":
            property = random;
            break;
        case "Prime":
            property = isPrime;
            break;
        default:
            System.err.println("Don't know any property by the name of " + name);
        }
        redraw();
    }

    public void setDrawMode(String name) {
        switch (name) {
        case "Shape":
            drawMode = toShape;
            break;
        case "Char":
            drawMode = toChar;
            break;
        default:
            System.err.println("Don't know any draw mode by the name of " + name);
        }
        redraw();
    }

    public void setNumberOfPoints(float value) {

    }

    public void setCellSize(float value) {
        cellSize = value;
        redraw();
    }

    public void setPointSize(float value) {
        numberSize = value;
        redraw();
    }

    /****************
     ***** Draw *****
     ****************/

    @Override
    public void draw() {
        background(255);

        pushMatrix();
        curve.enumerate(pts);

        Rect bounds = bounds(pts);
        bounds.scale(cellSize, cellSize);
        float tx = (width - bounds.getWidth()) / 2 - bounds.getX1();
        float ty = (height - bounds.getHeight()) / 2 - bounds.getY1();
        translate(tx, ty);

        drawPoints();
        popMatrix();

        // System.out.println(frameCount);
    }

    private void drawPoints() {
        for (int n = 0; n < pts.length; n++) {
            if (pts[n] != null) {
                long y = sequence.nth(n + 1);
                drawMode.draw(n, property.evaluate(y), property.numPossibleStates() - 1, cellSize * pts[n].x,
                        cellSize * pts[n].y, numberSize, g);
            }
        }
    }

    @Override
    public void keyPressed() {
        if (key == 's' || key == 'S') {
            save("C://Users//James//Desktop//" + frameCount + ".png");
        }
    }

    /**************************
     ***** Helper Methods *****
     **************************/

    /**
     * Gives the smallest possible Rect that contains all the points in the
     * given array.
     * 
     * @param pts The points
     * @return The smallest possible Rect that contains all the points
     */
    private static Rect bounds(Point[] pts) {
        float min_x = (pts.length == 0) ? 0 : pts[0].x;
        float max_x = (pts.length == 0) ? 0 : pts[0].x;
        float min_y = (pts.length == 0) ? 0 : pts[0].y;
        float max_y = (pts.length == 0) ? 0 : pts[0].y;

        for (int i = 1; i < pts.length; i++) {
            if (pts[i].x < min_x) {
                min_x = pts[i].x;
            } else if (pts[i].x > max_x) {
                max_x = pts[i].x;
            }

            if (pts[i].y < min_y) {
                min_y = pts[i].y;
            } else if (pts[i].y > max_y) {
                max_y = pts[i].y;
            }
        }

        return new Rect(min_x, min_y, max_x, max_y, CORNERS);
    }
}