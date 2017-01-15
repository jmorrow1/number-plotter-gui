package gui;

import java.util.HashMap;

import controllers.Controller;
import controllers.ControllerDisplay;
import controllers.ControllerListener;
import controllers.ControllerUpdater;
import controllers.Slider;
import controllers.Toggle;
import curves.Curve;
import curves.Diamond;
import curves.Triangle;
import curves.Typewriter;
import draw_modes.DrawMode;
import draw_modes.ToChar;
import draw_modes.ToShape;
import geom.Rect;
import gui.Displays.DiamondToggleDisplay;
import gui.Displays.Display;
import gui.Displays.DrawShapeToggleDisplay;
import gui.Displays.StringToggleDisplay;
import gui.Displays.TriangleToggleDisplay;
import gui.Displays.TypewriterToggleDisplay;
import gui.Displays.Ulam;
import gui.Displays.UlamToggleDisplay;
import int_properties.FactorCount;
import int_properties.Fibonacci;
import int_properties.IntProperty;
import int_properties.IntSequence;
import int_properties.Integers;
import int_properties.IsPolygonal;
import int_properties.IsPrime;
import int_properties.Mod;
import int_properties.Multiples;
import int_properties.Powers;
import int_properties.RandomInt;
import number_plotter_gui.NumberPlotterControllable;
import processing.core.PApplet;
import processing.core.PGraphics;

public class GUI extends PApplet implements ControllerListener {
    private ControllerUpdater updater;
    private HashMap<String, Toggle[]> toggles = new HashMap<String, Toggle[]>();
    private Slider spacingSizeSlider, pointSizeSlider; 
    private NumberPlotterControllable controllable;
    
    public GUI(NumberPlotterControllable controllable) {
        this.controllable = controllable;
        PApplet.runSketch(new String[] { this.getClass().getName() }, this);
    }
    
    public void settings() {
//        size(800, 240);
        size(800, 200);
    }
    
    public void setup() {
        updater = new ControllerUpdater(this);
        updater.setDefaultListener(this);
        
//        initSliders();
//        initToggles(40);
        initToggles(5);
    }
    
    private void initSliders() {
        spacingSizeSlider = new Slider(1f, 0.25f, 10f, updater, 1);
        spacingSizeSlider.setRect(new Rect(460, 10, 130, 20, CORNER));
        spacingSizeSlider.setDefaultColor(color(20));
        spacingSizeSlider.setHoveredColor(color(20));

        pointSizeSlider = new Slider(1f, 0.25f, 5f, updater, 1);
        pointSizeSlider.setRect(new Rect(635, 10, 130, 20, CORNER));
        pointSizeSlider.setDefaultColor(color(20));
        pointSizeSlider.setHoveredColor(color(20));
    }
    
    private void initToggles(float startY) {
        Display placeholder = new Display() {
            public void display(PGraphics g, Rect r) {
                g.stroke(0);
                r.draw(g);
            }
        };
        
        float xMargin = 5;
        float yMargin = 10;
        float w1 = 40;
        float w2 = w1+w1+xMargin;
        float x = xMargin;
        float y = startY;
        float h = 40;
        float dy = h + yMargin;
    
        toggles.put(Curve.class.getSimpleName(), new Toggle[] {
            createToggle(1, new DiamondToggleDisplay(5), new Rect(x, y, w1, h, CORNER), Curve.class.getSimpleName(), Diamond.class.getSimpleName()),
            createToggle(1, new TypewriterToggleDisplay(5), new Rect(x += w1+xMargin, y, w1, h, CORNER), Curve.class.getSimpleName(), Typewriter.class.getSimpleName()),
            createToggle(1, new UlamToggleDisplay(5), new Rect(x += w1+xMargin, y, w1, h, CORNER), Curve.class.getSimpleName(), Ulam.class.getSimpleName()),
            createToggle(1, new TriangleToggleDisplay(5), new Rect(x += w1+xMargin, y, w1, h, CORNER), Curve.class.getSimpleName(), Triangle.class.getSimpleName())
        });
        
        x = xMargin;
        y += dy;
        
        toggles.put(IntSequence.class.getSimpleName(), new Toggle[] {
            createToggle(1, new StringToggleDisplay(intSequenceToString(new int[] {1,2,3})), new Rect(x, y, w2, h, CORNER), IntSequence.class.getSimpleName(), Integers.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay(intSequenceToString(new int[] {2,4,6})), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntSequence.class.getSimpleName(), Multiples.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay(intSequenceToString(new int[] {1,2,4})), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntSequence.class.getSimpleName(), Powers.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay(intSequenceToString(new int[] {1,1,2})), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntSequence.class.getSimpleName(), Fibonacci.class.getSimpleName())
        });
        
        x = xMargin;
        y += dy;
        
        toggles.put(IntProperty.class.getSimpleName(), new Toggle[] {
            createToggle(1, new StringToggleDisplay("n^m = 0"), new Rect(x, y, w2, h, CORNER), IntProperty.class.getSimpleName(), Powers.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay("isPrime(n)"), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntProperty.class.getSimpleName(), IsPrime.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay("rand()"), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntProperty.class.getSimpleName(), RandomInt.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay("n/5 = 0"), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntProperty.class.getSimpleName(), Multiples.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay("isFib(n)"), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntProperty.class.getSimpleName(), Fibonacci.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay("isPoly(n)"), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntProperty.class.getSimpleName(), IsPolygonal.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay("factors(n)"), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntProperty.class.getSimpleName(), FactorCount.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay("n%m"), new Rect(x += w2+xMargin, y, w2, h, CORNER), IntProperty.class.getSimpleName(), Mod.class.getSimpleName())
        });
        
        x = xMargin;
        y += dy;
        
        toggles.put(DrawMode.class.getSimpleName(), new Toggle[] {
            createToggle(1, new DrawShapeToggleDisplay(ToShape.SQUARE), new Rect(x, y, w1, h, CORNER), DrawMode.class.getSimpleName(), ToShape.class.getSimpleName()),
            createToggle(1, new StringToggleDisplay("a"), new Rect(x += w1+xMargin, y, w1, h, CORNER), DrawMode.class.getSimpleName(), ToChar.class.getSimpleName())
        });
    }
    
    private String intSequenceToString(int[] ns) {
        String s = "";
        for (int i=0; i<ns.length; i++) {
            s += ns[i] + ", ";
        }
        s += "...";
        return s;
    }
    
    private Toggle createToggle(float priority, ControllerDisplay display, Rect rect, String groupName, String name) {
        Toggle t = new Toggle(updater, priority);
        t.setDisplay(display);
        t.setRect(rect);
        t.setGroupName(groupName);
        return t;
    }

    public void draw() {
        background(255);
        updater.draw(g);
    }

    @Override
    public void controllerEvent(Controller c) {
        if (c instanceof Toggle) {
            toggleEvent((Toggle)c);
        }
    }
    
    public void toggleEvent(Toggle t) {
        Toggle[] ts = toggles.get(t.getGroupName());
        if (ts != null) {
            for (int i=0; i<ts.length; i++) {
                ts[i].setState(ts[i] == t);
                controllable.setVariable(t.getGroupName(), t.getName());
            }
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
}
