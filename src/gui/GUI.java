package gui;

import controllers.Controller;
import controllers.ControllerListener;
import controllers.ControllerUpdater;
import controllers.Slider;
import controllers.Toggle;
import geom.Rect;
import gui.Displays.DiamondToggleDisplay;
import gui.Displays.TriangleToggleDisplay;
import gui.Displays.TypewriterToggleDisplay;
import gui.Displays.UlamToggleDisplay;
import number_plotter_gui.App;
import processing.core.PApplet;

/**
 * The window that displays the GUI.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class GUI extends PApplet implements ControllerListener {

    // window that displays the output of the system
    private App app;

    // positioning of controllers
    private float x1, x2, dy;
    private float[] y1s;

    // text
    private String[] categoryNames;

    // controllers
    private ControllerUpdater updater;
    private Toggle[] sequenceToggles, curveToggles, propertyToggles, drawModeToggles;

    // style
    private int controllerDefaultColor, controllerHoveredColor, toggleOnColor;
    
    public static void main(String[] args) {
        PApplet.main("gui.GUI");
    }
    
    public GUI() {}

    public GUI(App app) {
        this.app = app;
        PApplet.runSketch(new String[] { this.getClass().getName() }, this);
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        controllerDefaultColor = color(100);
        controllerHoveredColor = color(50);
        toggleOnColor = color(0);

        updater = new ControllerUpdater(this);
        updater.setDefaultListener(this);

        x1 = 20;
        x2 = 250;
        dy = 80;
        y1s = scanAdd(20, dy, 7);
        categoryNames = new String[] { "Sequence:", "Curve:", "Property:", "Draw mode:", "Number of points:",
                "Spacing between points:", "Point size:" };

        // Sequences
        int sw = 80, sh = 40;
        int sdx = 10;
        Toggle s1 = new Toggle(new Rect(x2, y1s[0], sw, sh, CORNER), updater, 1);
        s1.setGroupName("Sequence");
        s1.setName("Integers");
        s1.setTextAlign(LEFT, TOP);
        Toggle s2 = new Toggle(new Rect(x2 + sw + sdx, y1s[0], sw, sh, CORNER), updater, 1);
        s2.setGroupName("Sequence");
        s2.setName("Multiples");
        s2.setTextAlign(LEFT, TOP);
        Toggle s3 = new Toggle(new Rect(x2 + 2 * (sw + sdx), y1s[0], sw, sh, CORNER), updater, 1);
        s3.setGroupName("Sequence");
        s3.setName("Powers");
        s3.setTextAlign(LEFT, TOP);
        sequenceToggles = new Toggle[] { s1, s2, s3 };

        // Curves
        int lns = 7;
        int csqrt = 50;
        int cdx = 10;
        Toggle c1 = new Toggle(new Rect(x2, y1s[1], csqrt, csqrt, CORNER), updater, 1);
        c1.setDisplay(new DiamondToggleDisplay(lns));
        c1.setGroupName("Curve");
        c1.setName("Diamond");
        Toggle c2 = new Toggle(new Rect(x2 + csqrt + cdx, y1s[1], csqrt, csqrt, CORNER), updater, 1);
        c2.setDisplay(new UlamToggleDisplay(lns));
        c2.setGroupName("Curve");
        c2.setName("Ulam Spiral");
        Toggle c3 = new Toggle(new Rect(x2 + 2 * (csqrt + cdx), y1s[1], csqrt, csqrt, CORNER), updater, 1);
        c3.setDisplay(new TypewriterToggleDisplay(lns));
        c3.setGroupName("Curve");
        c3.setName("Typewriter");
        Toggle c4 = new Toggle(new Rect(x2 + 3 * (csqrt + cdx), y1s[1], csqrt, csqrt, CORNER), updater, 1);
        c4.setDisplay(new TriangleToggleDisplay(lns));
        c4.setGroupName("Curve");
        c4.setName("Triangle");
        curveToggles = new Toggle[] { c1, c2, c3, c4 };

        // TODO Properties
        int pw = 120, ph = 40;
        int pdx = 10;
        Toggle p1 = new Toggle(new Rect(x2, y1s[2], pw, ph, CORNER), updater, 1);
        p1.setGroupName("Property");
        p1.setName("Divisible by");
        Toggle p2 = new Toggle(new Rect(x2 + pw + pdx, y1s[2], pw, ph, CORNER), updater, 1);
        p2.setGroupName("Property");
        p2.setName("Any Power");
        Toggle p3 = new Toggle(new Rect(x2 + 2 * (pw + pdx), y1s[2], pw, ph, CORNER), updater, 1);
        p3.setGroupName("Property");
        p3.setName("Random");
        Toggle p4 = new Toggle(new Rect(x2 + 3 * (pw + pdx), y1s[2], pw, ph, CORNER), updater, 1);
        p4.setGroupName("Property");
        p4.setName("Prime");
        propertyToggles = new Toggle[] { p1, p2, p3, p4 };

        // TODO Draw modes
        int dw = 70, dh = 40;
        int ddx = 10;
        Toggle d1 = new Toggle(new Rect(x2, y1s[3], dw, dh, CORNER), updater, 1);
        d1.setGroupName("Draw Mode");
        d1.setName("Shape");
        Toggle d2 = new Toggle(new Rect(x2 + dw + ddx, y1s[3], dw, dh, CORNER), updater, 1);
        d2.setGroupName("Draw Mode");
        d2.setName("Char");
        drawModeToggles = new Toggle[] { d1, d2 };

        // TODO Number of Sample Points
        Slider numSamplePts = new Slider(1000, 1000000, updater, 1);
        numSamplePts.setRect(new Rect(x2, y1s[4] + 2, 200, 20, CORNER));
        numSamplePts.setName("Number of points");

        // TODO Spacing between Samples (temp)
        Slider spacingBetweenSamples = new Slider(1f, 0.25f, 10f, updater, 1);
        spacingBetweenSamples.setRect(new Rect(x2, y1s[5] + 2, 200, 20, CORNER));
        spacingBetweenSamples.setTick(0.25f);
        spacingBetweenSamples.setName("Cell size");

        // TODO Point Size (temp)
        Slider pointSize = new Slider(1f, 0.25f, 5f, updater, 1);
        pointSize.setRect(new Rect(x2, y1s[6] + 2, 200, 20, CORNER));
        pointSize.setTick(0.25f);
        pointSize.setName("Point size");

        for (int i = 0; i < updater.controllerCount(); i++) {
            Controller c = updater.getController(i);
            c.setDefaultColor(controllerDefaultColor);
            c.setHoveredColor(controllerHoveredColor);
            if (c instanceof Toggle) {
                Toggle t = (Toggle) c;
                t.setOnColor(toggleOnColor);
            }
        }
    }

    private static float[] scanAdd(float start, float dx, int n) {
        float x = start;
        float[] ys = new float[n];
        for (int i = 0; i < n; i++) {
            ys[i] = x;
            x += dx;
        }
        return ys;
    }

    @Override
    public void draw() {
        background(255);
        updater.draw(g);
        drawCategoryNames(18, 20);
    }

    private void drawCategoryNames(float textSize, float x1) {
        int i = 0;
        int j = 0;
        fill(0);
        textSize(textSize);
        textAlign(RIGHT, TOP);
        rectMode(CORNERS);
        while (i < categoryNames.length && j < y1s.length) {
            text(categoryNames[i], x1, y1s[i], x2 - 20, y1s[i] + dy - 20);
            i++;
            j++;
        }
    }

    @Override
    public void mousePressed() {
        updater.mousePressed(this);
    }

    @Override
    public void mouseReleased() {
        updater.mouseReleased(this);
    }

    @Override
    public void mouseMoved() {
        updater.mouseMoved(this);
    }

    @Override
    public void mouseDragged() {
        updater.mouseDragged(this);
    }

    @Override
    public void controllerEvent(Controller c) {
        switch (c.getName()) {
        case "Number of points":
            Slider a = (Slider) c;
            app.setNumberOfPoints(a.getValue());
            break;
        case "Cell size":
            Slider b = (Slider) c;
            app.setCellSize(b.getValue());
            break;
        case "Point size":
            Slider d = (Slider) c;
            app.setCellSize(d.getValue());
            break;
        default:
            Toggle t = (Toggle) c;
            switch (c.getGroupName()) {
            case "Curve":
                app.setCurve(c.getName());
                turnOffToggles(curveToggles, t);
                break;
            case "Sequence":
                app.setSequence(c.getName());
                turnOffToggles(sequenceToggles, t);
                break;
            case "Property":
                app.setProperty(c.getName());
                turnOffToggles(propertyToggles, t);
                break;
            case "Draw Mode":
                app.setDrawMode(c.getName());
                turnOffToggles(drawModeToggles, t);
                break;
            default:
                System.out.println("Don't know a controller with this name: " + c.getName() + " or this group name: "
                        + c.getGroupName());
            }
        }
    }

    /**
     * Turns off all the toggles (sets their states to off) except for one
     * toggle.
     * 
     * @param toggles The toggles
     * @param exemption The toggle not to turn off
     */
    private static void turnOffToggles(Toggle[] toggles, Toggle exemption) {
        for (int i = 0; i < toggles.length; i++) {
            if (toggles[i] != exemption) {
                toggles[i].setStateSilently(0);
            }
        }
    }
}
