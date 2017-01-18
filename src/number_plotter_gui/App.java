package number_plotter_gui;

import java.util.HashMap;

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
import int_properties.Integers;
import int_properties.IsPolygonal;
import int_properties.IsPrime;
import int_properties.IsPower;
import int_properties.Mod;
import int_properties.Multiples;
import int_properties.Powers;
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
    private HashMap<String, IntSequence> sequences = new HashMap<String, IntSequence>();
    private Integers integers;
    private Multiples multiples;
    private Powers powers;

    // All possible curves
    private HashMap<String, Curve> curves = new HashMap<String, Curve>();
    private Diamond diamond;
    private UlamSpiral ulamSpiral;
    private Typewriter typewriter;
    private Triangle triangle;

    // All possible properties
    private HashMap<String, IntProperty> properties = new HashMap<String, IntProperty>();
    private Multiples isDivisibleBy;
    private Powers isAnyPower;
    private RandomInt random;
    private IsPrime isPrime;
    private IsPolygonal isPoly;
    private Mod mod;
    private IsPower isSquare;

    // All possible draw modes
    private HashMap<String, DrawMode> drawModes = new HashMap<String, DrawMode>();
    private ToChar toChar;
    private ToImage toImg;
    private ToShape toShape;

    // points
    private Point[] pts = new Point[80000];
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
        integers = (Integers)put(sequences, Integers.instance);
        multiples = (Multiples)put(sequences, new Multiples(2));
        powers = (Powers)put(sequences, new Powers(2));

        // init all possible curves
        diamond = (Diamond)put(curves, new Diamond(150, 2));
        ulamSpiral = (UlamSpiral)put(curves, new UlamSpiral());
        typewriter = (Typewriter)put(curves, new Typewriter(200));
        triangle = (Triangle)put(curves, new Triangle(2));

        // init all possible properties
        isDivisibleBy = (Multiples)put(properties, new Multiples(2));
        isAnyPower = (Powers)put(properties, new Powers(2));
        random = (RandomInt)put(properties, new RandomInt(2));
        isPrime = (IsPrime)put(properties, IsPrime.instance);
        isPoly = (IsPolygonal)put(properties, new IsPolygonal(5));
        mod = (Mod)put(properties, new Mod(5)); 
        isSquare = (IsPower)put(properties, IsPower.instance);

        // init all possible draw modes
        toShape = (ToShape)put(drawModes, new ToShape(ToShape.CIRCLE, 1, 1, 0xffffffff, 0, 200, 200, false));
        toChar = (ToChar)put(drawModes, new ToChar("x.", '?'));

        // init current settings
        sequence = integers;
        curve = diamond;
        property = isPrime;
        drawMode = toShape;

        new GUI(this);

        noLoop();
    }
    
    private Object put(HashMap map, Object value) {
        map.put(value.getClass().getSimpleName(), value);
        return value;
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
        float tx = 0.5f * (width - bounds.getWidth()) - bounds.getX1();
        float ty = 0.5f * (height - bounds.getHeight()) - bounds.getY1();
        translate(tx, ty);
        drawPoints();
        popMatrix();

        // System.out.println(frameCount);
    }

    private void drawPoints() {
        for (int n = 0; n < pts.length; n++) {
            if (pts[n] != null) {
                long y = sequence.nth(n + 1);
                drawMode.draw(n, property.evaluate(y), property.numPossibleStates() - 1,
                        cellSize * pts[n].x, cellSize * pts[n].y, numberSize, g);
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
     ***** Event Handling *****
     **************************/

    public void setSequence(String name) {
        IntSequence newSequence = sequences.get(name);
        if (newSequence != null) {
            if (newSequence != sequence) {
                sequence = newSequence;
                redraw();
            }        
        }
        else {
            System.err.println("Don't know any sequence by the name of " + name);
        }
    }

    public void setCurve(String name) {
        Curve newCurve = curves.get(name);
        if (newCurve != null) {
            if (newCurve != curve) {
                curve = newCurve;
                redraw();
            }            
        }
        else {
            System.err.println("Don't know any curve by the name of " + name);
        }
        
    }

    public void setProperty(String name) {
        IntProperty newProperty = properties.get(name);
        if (newProperty != null) {
            if (newProperty != property) {
                property = newProperty;
                redraw();
            }           
        }
        else {
            System.err.println("Don't know any property by the name of " + name);
        }
    }
    
    public void setDrawMode(String name) {
        DrawMode newDrawMode = drawModes.get(name);
        if (newDrawMode != null) {
            if (newDrawMode != drawMode) {
                drawMode = newDrawMode;
            }
        }
        else {
            System.err.println("Don't know any draw mode by the name of " + name);
        }
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